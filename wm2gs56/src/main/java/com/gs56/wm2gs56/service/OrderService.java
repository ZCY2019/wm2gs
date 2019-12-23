package com.gs56.wm2gs56.service;

import com.alibaba.fastjson.JSON;
import com.gs56.wm2gs56.dto.CustomersNws;
import com.gs56.wm2gs56.dto.Order;
import com.gs56.wm2gs56.mapper.OrderMapper;
import com.gs56.wm2gs56.utils.Date2TimeStamp;
import com.gs56.wm2gs56.utils.HttpJson;
import com.gs56.wm2gs56.utils.List_Map_Json;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author gaishi_z
 * @create 2019-11-13 14:26
 */
@Slf4j
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;


    public void getData(int pageNum) {
        //int pageNum = 1;
        int pageSize = 2;
        int status = 1;
        String oneOfHour = Date2TimeStamp.yesterHour();//"1571364720000";//Date2TimeStamp.yesterDay();//昨日23:59:59
        String nowTime = Date2TimeStamp.timeStamp();//此时时间戳
        //orderL    istService(pageNum,"100",oneOfHour,nowTime,"1");
        String jsonText = "{\"pageNum\": \""+pageNum+"\",\n" +
                "  \"pageSize\": \""+pageSize+"\",\n" +
                "  \"queryParameter\": {\n" +
                "    \"createStartTime\": "+ oneOfHour+",\n" +
                "    \"createEndTime\":"+nowTime+",\n" +
                "    \"orderStatuses\":[\""+status+"\"]\n" +
                "  }" +
                "}";

        JSONObject paramJsonObject = new JSONObject(jsonText);
        JSONObject resJSONObject = HttpJson.doPost("ec/order/queryOrderList",paramJsonObject);
        try {
            String err_code= HttpJson.isTrue(resJSONObject.getJSONObject("code"));
            if(!err_code.equals("0")){
                log.error("【订单列表】访问接口返回code != 0,err_code={},nowTime={}",err_code,nowTime);
            }else{
                JSONObject data = resJSONObject.getJSONObject("data");
                JSONArray pages  = data.getJSONArray("pageList");
                if (pages.isEmpty()){
                    log.error("【订单列表】数据为空,pages={},data={}",pages,data);
                }else{
                    List<Map<String,Object>> order_detail = new ArrayList<Map<String,Object>>();

                    List list = List_Map_Json.jsonToList(pages.toString());
                    //解析第一层
                    for (int i=0; i<list.size();i++) {
                        List<Map<String,Object>> order_list = new ArrayList<Map<String,Object>>();
                        JSONObject jsonObject = new JSONObject(JSON.toJSONString(list.get(i)));
                        Map<String,Object> map = getOrderList(jsonObject);
                        order_list.add(map);
                        orderMapper.insertOrderList(order_list);
                        orderMapper.insertCoreOrderList(order_list);
                        Long orderNo = jsonObject.getLong("orderNo");
                        JSONArray itemJson = jsonObject.getJSONArray("itemList");//将item转为array
                        List itemList = List_Map_Json.jsonToList(itemJson.toString());
                        //解析itemList
                        for (int j = 0;j<itemList.size();j++){
                            Map<String,Object> itemMap = new HashMap<>();
                            try {
                                Map mapTypes = JSON.parseObject(itemList.get(j).toString());
                                for (Object obj : mapTypes.keySet()){
                                    itemMap.put((String) obj,mapTypes.get(obj).toString() == null ? "":mapTypes.get(obj).toString());
                                }
                                Object ces = mapTypes.get("goodsCode");
                                String skuName = mapTypes.get("skuName") == null ? "" : mapTypes.get("skuName").toString();
                                System.out.println(skuName);
                                String goodsCode = mapTypes.get("goodsCode").toString() ;
                                System.out.println(goodsCode);

                                Map<String,Object> goodsMap = null;
                                try {
                                    if (!ces.toString().isEmpty()){
                                        goodsMap = orderMapper.selectUnitById(goodsCode);
                                    }else{
                                        goodsMap = orderMapper.selectUnitById("1323");
                                    }


                                    if(goodsMap.size()>0){//获取的goods 是否为空
                                        String kind = goodsMap.get("kind").toString();
                                        String unit = goodsMap.get("unit").toString();
                                        itemMap.put("unit",unit);
                                        itemMap.put("kind",kind);
                                    }
                                }catch (Exception e){
                                    log.error("【解析itemList】,goodsMap={}",goodsMap);
                                    e.printStackTrace();
                                }
                                try {
                                    if(!skuName.isEmpty()){//是否为多规格
                                        if (skuName.length()>9){
                                            if (skuName.contains("(")){
                                                skuName = skuName.substring(0,skuName.indexOf("("));
                                            }else{
                                                skuName = skuName.substring(0,skuName.indexOf("（"));
                                            }
                                        }
                                        String[] sku = skuName.split("\\|");
                                        if (sku.length>1){
                                            String quantity = sku[1].substring(0,sku[1].length()-1);
                                            quantity = String.valueOf((Integer.parseInt(quantity)*Integer.parseInt(mapTypes.get("skuNum").toString())));
                                            itemMap.put("skuNum",quantity);
                                        }else{
                                            itemMap.put("skuNum",1);
                                        }

                                    }
                                }catch (Exception e){
                                    log.error("【解析itemList，skuName錯誤】skuName={}",skuName);
                                    e.printStackTrace();
                                }

                                itemMap.put("orderNum",orderNo);
                                itemMap.put("goodsCode",goodsCode);
                                order_detail.add(itemMap);
                            } catch (Exception e) {
                                log.error("【解析itemList】itemList={}",itemList);
                                e.printStackTrace();
                            }
                        }
                    }
                    orderMapper.insertOrderDetail(order_detail);
                    orderMapper.insertCoreOrderDetail(order_detail);
                    System.out.println("order_detail--"+order_detail);
                }
                int count = data.getInt("totalCount");
                if((pageSize*pageNum)<count){
                    getData(pageNum+1);
                }
                    System.out.println(count);
                }
        }catch (Exception e){
            log.error("【访问接口，code为空】resJSONObject={}",resJSONObject);
            e.printStackTrace();
        }
    }

    private Map<String,Object> getOrderList(JSONObject jsonObject) throws ParseException {

        Map<String,Object> orderMap = new  HashMap<>();
        String receiverAddress = jsonObject.getString("receiverAddress");
        String receiverName = jsonObject.getString("receiverName");
        String receiverMobile  = jsonObject.getString("receiverMobile");
        String receiverArea  = jsonObject.getString("receiverArea");
        BigDecimal amount = jsonObject.getBigDecimal("paymentAmount");
        BigDecimal goodsAmount = jsonObject.getBigDecimal("goodsAmount");
        Long createTime = jsonObject.getLong("createTime");
        System.out.println(createTime);
        Long orderNo = jsonObject.getLong("orderNo");
        //Long wid = jsonObject.getLong("wid");
        orderMap.put("order_num",orderNo);
        orderMap.put("customer_name",receiverName);
        orderMap.put("customer_address",receiverAddress);
        orderMap.put("customer_phone",receiverMobile);
        orderMap.put("amount",amount);
        orderMap.put("goodsAmount",goodsAmount);
        orderMap.put("createTime", Date2TimeStamp.longToDate(createTime));
        orderMap.put("receiverArea", receiverArea);
        CustomersNws customersNws = orderMapper.queryCustomerIdByPhone(receiverMobile);
        if(customersNws!=null){
            if (!customersNws.getId().toString().isEmpty()) {
                orderMap.put("customer_id",customersNws.getId().toString());
                orderMap.put("sale_name",customersNws.getSalesPerson());
                orderMap.put("sale_phone",customersNws.getSalesPhone());
                orderMap.put("customer_path",customersNws.getBusinessPath());
                orderMap.put("customer_region",customersNws.getRegion());
                orderMap.put("deliver",customersNws.getDeliver());
                orderMap.put("deliver_phone",customersNws.getDeliverPhone());
                orderMap.put("deliver_time",customersNws.getDeliverTime());
            }else{
                orderMapper.insertUserCenter(orderMap);
            }
        }else{
            log.error("【暂无该手机号对应的用户】phone={}",receiverMobile);

            //return null;
        }


        System.out.println(amount+"/n"+receiverMobile+"/n"+receiverName+"/n"+receiverAddress);

        return orderMap;
    }

    public void deliveryOrder() {
        List<Order> orderList = orderMapper.selectDeliveryOrderByOrderNum();
        List<String> orderNumList = new ArrayList<String>(16);
        for (Order order :orderList){
            String orderNum = order.getOrderNum();
            String jsonText = "{\n" +
                    "  \"selfDelivery\": true,\n" +
                    "  \"orderNo\": "+orderNum+",\n" +
                    "  \"remark\": \"  \"\n" +
                    "}";
            JSONObject paramJsonObject = new JSONObject(jsonText);
            JSONObject resJSONObject = HttpJson.doPost("ec/order/deliveryCityOrder",paramJsonObject);
            String err_code = HttpJson.is_True(resJSONObject.getJSONObject("code"));
            System.out.println(resJSONObject);
            if(!err_code.equals("0")){
                log.error("【同城发货】访问失败 orderNum={}",orderNum);
            }else{
                orderNumList.add(orderNum);
            }
        }
        if (!orderNumList.isEmpty()){
            orderMapper.updateDeliveryOrder(orderNumList);
        }

        System.out.println(orderList);

    }
}
