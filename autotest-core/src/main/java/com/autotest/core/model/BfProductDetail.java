package com.autotest.core.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class BfProductDetail {
    private int id;
    private String productCode;
    private String productName;
    private BigDecimal interestRate;
    private BigDecimal monthRate;
    private BigDecimal falseInterestRate;
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
    private BigDecimal delayRate;
    private int delay_no_interest_days;
    private BigDecimal advance_cost;
    private BigDecimal advance_cost60;
    private BigDecimal manage_cost;
}
