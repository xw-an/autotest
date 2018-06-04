package com.autotest.web.controller;

import com.autotest.service.tools.ITestToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/TestToolManage")
public class TestToolController {

    @Autowired
    private ITestToolService testToolService;

    @RequestMapping
    public String showTestToolManage() {
        return "TestToolManage";
    }

    @RequestMapping("/timeTool")
    public String showTimeTool() {
        return "timeTool";
    }

    @RequestMapping("/profitsPlanTool")
    public String showProfitsPlanTool() {
        return "profitsPlanTool";
    }

    @RequestMapping("/changeTime")
    @ResponseBody
    public Map<String, String> changeTime(@RequestParam int type, @RequestParam Object time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> msg = new HashMap<>();
        try {
            if (type == 1) {
                //时间戳转成yyyy-MM-dd HH:mm:ss
                long timeStamp = Long.parseLong(time.toString());
                Date date = new Date();
                date.setTime(timeStamp);
                msg.put("result", sdf.format(date));
            } else if (type == 2) {
                //yyyy-MM-dd HH:mm:ss格式时间转成时间戳
                Date date = sdf.parse(time.toString());
                msg.put("result", String.valueOf(date.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
            msg.put("errorMsg", "转换失败" + e.getMessage());
        }
        return msg;
    }

    @RequestMapping("/calculateAmount")
    @ResponseBody
    public Map<String,String> calculateAmount(@RequestParam String productCode, @RequestParam String price,@RequestParam String activityRate) {
        Map<String, String> msg = new HashMap<>();
        try {
            //调用计算收益方法，生成结果
            String result=testToolService.calculateAmountByParams(productCode,price,activityRate);
            msg.put("result",result );
        } catch (Exception e) {
            e.printStackTrace();
            msg.put("errorMsg", "计算失败:" + e.getMessage());
        }
        return msg;
    }
}
