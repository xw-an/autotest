package com.autotest.core.mapper;


import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.BfProductDetail;

@DataSource("fundsystem_")
public interface IBfProductDetail {

    public BfProductDetail select(String productCode);
}
