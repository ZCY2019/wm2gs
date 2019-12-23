package com.gs56.wm2gs56.controller;


import com.gs56.wm2gs56.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * @author gaishi_z
 * @create 2019-11-12 16:44
 */
@Slf4j
@RestController
@RequestMapping("test")
public class OrderController {

    @Resource
    private OrderService orderService;


    @RequestMapping("order")
    @Transactional
    public String orderList()  {
        try {
            orderService.getData(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "访问成功";
    }

    @RequestMapping("deliveryOrder")
    public void deliveryOrder(){

        orderService.deliveryOrder();
    }

    /*public void orderListService(String pageNum,String pageSize,String yesterDay,String nowTime,String status) throws ParseException {
        //此处将要发送的数据转换为json格式字符串
        String jsonText = "{\"pageNum\": \""+pageNum+"\",\n" +
                "  \"pageSize\": \""+pageSize+"\",\n" +
                "  \"queryParameter\": {\n" +
                "    \"createStartTime\": "+ yesterDay+",\n" +
                "    \"createEndTime\":"+nowTime+",\n" +
                "    \"orderStatuses\":[\""+status+"\"]\n" +
                "  }" +
                "}";

        JSONObject paramJsonObject = new JSONObject(jsonText);
        JSONObject resJSONObject = HttpJson.doPost("/order/queryOrderList",paramJsonObject);
       // orderService.getData(resJSONObject);
    }*/




}
