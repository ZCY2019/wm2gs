package com.gs56.wm2gs56.controller;

import com.gs56.wm2gs56.utils.HttpJson;
import com.gs56.wm2gs56.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



/**
 * @author gaishi_z
 * @create 2019-10-24 15:39
 */

@Slf4j
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
    public class WmService {

    @Scheduled(cron = "0 5 * * * ?")
    public void  scheduled(){
        order();
    }
    @Scheduled(cron = "0 50 6 * * ?")
    public void corn(){
        deliveryOrder();
    }

    @Scheduled(cron = "0 1 * * * ?")
    public void cornToken(){
        rtPostObject();
    }

    /*@Scheduled(fixedRate = 15000)
    public void scheduled1() {

        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }*/
    /*@Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    }*/


    @Test
    public void rtPostObject(){
        RestTemplate restTemplate = new RestTemplate();
        //拼接参数
        String url = "https://dopen.weimob.com/fuwu/b/oauth2/token?grant_type=refresh_token&" +
                "&client_id="+ PropertiesUtils.getKeyValue("client_id")+//04331BCC83936F87E2D32C3723E4C81A&" +
                "&client_secret="+PropertiesUtils.getKeyValue("client_secret")+//223FDA5F0DDC6FBDC20B682BE0F0DDD5&" +
                "&refresh_token="+PropertiesUtils.getKeyValue("refresh_token");//919c4f9c-e86f-4b94-aa99-4518c9e5672f";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        //请求
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        //截取转化json
        String str = response.toString().substring(response.toString().indexOf("{"),response.toString().indexOf("}")+1);
        String access_token = "";
        try {
            JSONObject jsonObject = new JSONObject(str);
            access_token= jsonObject.getString("access_token");
            System.out.println(access_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //将json中的token 写入配置文件
        PropertiesUtils.writeProperties("access_token",access_token);

    }

    public static void order(){

        String url = "http://localhost:2019/test/order";
        httpRes(url);
    }
    public static void deliveryOrder(){

        String url = "http://localhost:2019/test/deliveryOrder";
        httpRes(url);
    }
    public static void httpRes(String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);// new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println("response=="+response);
    }

}