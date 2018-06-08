package com.autotest.service.tools;

import com.autotest.core.mapper.IBfProductDetail;
import com.autotest.core.model.BfProductDetail;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Map;

@Log4j
@Service
public class TestToolService implements ITestToolService {
    @Autowired
    private IBfProductDetail iBfProductDetail;

    @Override
    public String calculateAmountByParams(String productCode,String priceStr,String activityRateStr) {
        StringBuffer result=new StringBuffer();
        BigDecimal price=new BigDecimal(priceStr);//本金
        BigDecimal activityRate=new BigDecimal(activityRateStr);//活动利率

        BfProductDetail prodDetail=iBfProductDetail.select(productCode);
        if(prodDetail==null) throw new RuntimeException("产品code不存在");
        BigDecimal interestRate=prodDetail.getInterestRate();//利息利率
        String refundWay=prodDetail.getRefundWay();
        int term=prodDetail.getTerm();
        result.append("----------产品信息----------\n");
        result.append("产品利率："+interestRate+"\n");
        result.append("产品期数："+term+"\n");

        BigDecimal interestAmount=price.multiply(interestRate).divide(new BigDecimal("12"),4,BigDecimal.ROUND_HALF_UP);//每个月利息金额4位小数
        BigDecimal activityAmount=price.multiply(activityRate).divide(new BigDecimal("12"),2,BigDecimal.ROUND_HALF_UP);//每个月活动金额2位小数
        BigDecimal totalInterestAmount=price.multiply(interestRate).divide(new BigDecimal("12"),20,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(term)).setScale(2,BigDecimal.ROUND_HALF_UP);//总利息收益4位小数
        BigDecimal totalActivityAmount=price.multiply(activityRate).divide(new BigDecimal("12"),20,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(term)).setScale(2,BigDecimal.ROUND_HALF_UP);//总活动收益4位小数;

        if(refundWay.equals("3")){
            //一次性还本付息
            result.append("----------一次性还本付息----------\n");
            result.append("每月本金："+price+"\n");
            int n=term-1;
            result.append("本期收益(前"+n+"期每期)："+interestAmount+"\n");
            BigDecimal lastInterestAmount=lastAmount(totalInterestAmount,interestAmount,term);
            result.append("(最后一期)本期收益："+lastInterestAmount+"\n");
            result.append("本期收益合计:"+totalInterestAmount+"\n");
            result.append("应付本金:"+price+"\n");
            BigDecimal payInterest=roundByScale(totalInterestAmount,2);
            BigDecimal payActivity=roundByScale(totalActivityAmount,2);
            result.append("应付利息:"+payInterest+"\n");
            result.append("活动金额:"+payActivity+"\n");
            BigDecimal totalAmount=payInterest.add(payActivity).add(price);
            result.append("应付合计:"+roundByScale(totalAmount,2)+"\n");
        }else if(refundWay.equals("1")){
            //每月付息
            result.append("----------每月付息----------\n");
            result.append("每月本金："+price+"\n");
            int n=term-1;
            result.append("本期收益(前"+n+"期每期)："+interestAmount+"\n");
            BigDecimal lastInterestAmount=lastAmount(totalInterestAmount,interestAmount,term);
            result.append("(最后一期)本期收益："+lastInterestAmount+"\n");
            result.append("本期收益合计:"+totalInterestAmount+"\n");


            BigDecimal beforePayInterestAmount=roundByScale(interestAmount,2);
            BigDecimal lastPayInterestAmount=lastAmount(roundByScale(totalInterestAmount,2),beforePayInterestAmount,term);
            result.append("(前"+n+"期每期)应付利息："+beforePayInterestAmount+"\n");
            result.append("(最后一期)应付利息："+lastPayInterestAmount+"\n");
            result.append("应付利息合计:"+roundByScale(totalInterestAmount,2)+"\n");

            BigDecimal beforeActivityAmount=roundByScale(activityAmount,2);
            BigDecimal lastActivityAmount=lastAmount(roundByScale(totalActivityAmount,2),beforeActivityAmount,term);
            result.append("(前"+n+"期每期)活动金额："+beforeActivityAmount+"\n");
            result.append("(最后一期)活动金额："+lastActivityAmount+"\n");
            result.append("活动金额合计:"+roundByScale(totalActivityAmount,2)+"\n");

            BigDecimal beforeTotal=beforePayInterestAmount.add(beforeActivityAmount);
            BigDecimal afterTotal=lastPayInterestAmount.add(lastActivityAmount).add(price);

            result.append("(前"+n+"期每期)应付合计："+roundByScale(beforeTotal,2)+"\n");
            result.append("(最后一期)应付合计："+roundByScale(afterTotal,2)+"\n");

        }
        return result.toString();
    }

    /**
     * 最后一期倒减
     * @param total
     * @param amount
     * @param term
     * @return
     */
    private BigDecimal lastAmount(BigDecimal total,BigDecimal amount,int term){
        for(int i=0;i<term-1;i++){
            total=total.subtract(amount);
        }
        return total;
    }

/*    *//**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     *
     * @param num     需要格式化的数字
     * @param scale 小数点后保留几位
     * @return
     *//*
    private BigDecimal roundByScale(double num, int scale) {
        BigDecimal result;
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        else{
            BigDecimal b = new BigDecimal(String.valueOf(num));
            result= b.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }*/

    /**
     * @param b     需要格式化的数字
     * @param scale 小数点后保留几位
     * @return
     */
    private BigDecimal roundByScale(BigDecimal b, int scale) {
        BigDecimal result;
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The   scale   must   be   a   positive   integer   or   zero");
        }
        else{
            result= b.setScale(scale, BigDecimal.ROUND_HALF_UP);
        }
        return result;
    }

}
