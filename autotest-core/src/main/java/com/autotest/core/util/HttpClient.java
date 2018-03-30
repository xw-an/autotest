package com.autotest.core.util;

import com.autotest.core.model.FailException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    /**
     * 根据json请求报文和地址调用接口，并返回json格式的结果
     * @param maps
     * @return 接口响应结果
     */
    public static String getResponseByJson(Map<String,Object> maps){
        String res=null;
        String url=(String)maps.get("url");//获取url地址
        CloseableHttpClient httpClient=null;
        CloseableHttpResponse httpResponse=null;
        try {
            httpClient= HttpClients.createDefault();
            HttpPost httpPost=new HttpPost(url);
            Map<String,String> headers=(Map)maps.get("reqHeader");
            if(headers==null||headers.equals("")){
                headers=new HashMap<>();
            }
            headers.put("Content-Type", "application/json");//json格式调用
            for(String h:headers.keySet()){
                httpPost.setHeader(h,headers.get(h));
            }
            StringEntity reqEntity=new StringEntity((String)maps.get("reqData"));
            reqEntity.setContentEncoding("UTF-8");
            httpPost.setEntity(reqEntity);
            //处理返回结果
            httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()!=200)
                throw new FailException("调用失败");
            HttpEntity he=null;
            he=httpResponse.getEntity();
            res= EntityUtils.toString(he, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(httpResponse!=null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpClient!=null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }
}
