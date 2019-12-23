package com.gs56.wm2gs56.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author gaishi_z
 * @create 2019-11-20 10:54
 */
@Data
public class Order {

    private Integer id;

    private String orderNum;

    private Integer userId;

    private Integer addressId;

    private Date createTime;

    private Integer status;

    private Double amount;

    private Double realAmount;

    private Date endTime;

    private String remark;

    private Integer payType;

    private Integer isPay;

    private Integer type;

    private Integer errorNum;

    private Integer isSuccess;

    private Double deductibleAmount;

    private String setWarehouse;

}
