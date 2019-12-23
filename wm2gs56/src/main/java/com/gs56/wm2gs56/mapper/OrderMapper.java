package com.gs56.wm2gs56.mapper;

import com.gs56.wm2gs56.dto.CustomersNws;
import com.gs56.wm2gs56.dto.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author gaishi_z
 * @create 2019-11-13 11:59
 */
@Repository
public interface OrderMapper {

    int insertOrderDetail(List<Map<String,Object>> list);

    int insertOrderList(List<Map<String, Object>> order_list);

    int insertCoreOrderDetail(List<Map<String,Object>> list);

    int insertCoreOrderList(List<Map<String, Object>> order_list);


    List<Order> selectDeliveryOrderByOrderNum();

    int updateDeliveryOrder(List<String> orderNumList);

    CustomersNws queryCustomerIdByPhone(@Param("phone")String phone);

    int insertUserCenter(Map<String, Object> orderMap);

    Map<String,Object> selectUnitById(@Param("goodsCode")String goodsCode);
}
