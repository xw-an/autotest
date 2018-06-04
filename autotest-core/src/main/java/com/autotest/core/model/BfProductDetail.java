package com.autotest.core.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BfProductDetail {
    private int id;
    private String productCode;
    private String productName;
    private double interestRate;
    private double monthRate;
    private double falseInterestRate;
    private short term;
    private String refundWay;
    private String channel;
    private short state;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String interestWay;
    private String productBusinessName;
    private short advancedDays;
    private short advancedRules;
    private double delayRate;
    private int delay_no_interest_days;
    private double advance_cost;
    private double advance_cost60;
    private double manage_cost;
}
