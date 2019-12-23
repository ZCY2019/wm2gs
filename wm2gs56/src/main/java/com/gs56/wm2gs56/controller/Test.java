package com.gs56.wm2gs56.controller;

import com.gs56.wm2gs56.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gaishi_z
 * @create 2019-10-30 17:27
 */
@Slf4j
@Component
public class Test {
    public static void main(String[] args) throws IOException, JSONException {
        /*Test t = new Test();
        t.temp("");*/
        jj();
    }

    public static void jj (){
        String skus = "按斤称重|15斤|包";
        String[] sku = skus.split("\\|");
        /*for (int i = 0; i<b.length;i++){
            System.out.println(b[i]);
        }*/
        String quantity = sku[1];
        String a = quantity.substring(0,sku[1].length()-1);
        int sas = quantity.indexOf("5");
int as = sku[1].length();
        String unit = sku[1].substring(sku[1].length()-1);
        //System.out.println(b[0]);
    }

    public  void temp(String isbn) throws IOException, JSONException {
        // 我们需要进行请求的地址：
        String temp = "https://dopen.weimob.com/api/1_0/ec/order/queryOrderList" +
        "?accesstoken="+ PropertiesUtils.getKeyValue("access_token")+
                "";
        try {

            // 1.URL类封装了大量复杂的实现细节，这里将一个字符串构造成一个URL对象
            URL url = new URL(temp);
            // 2.获取HttpURRLConnection对象
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            // 3.调用connect方法连接远程资源
            connection.connect();
            // 4.访问资源数据，使用getInputStream方法获取一个输入流用以读取信息
            BufferedReader bReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

            // 对数据进行访问
            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // 关闭流
            bReader.close();
            // 关闭链接
            connection.disconnect();
            // 打印获取的结果

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            //JSONObject jsonObjects = jsonObject.getJSONObject(stringBuilder.toString());
            System.out.println(jsonObject.getJSONObject("data"));
            System.out.println(jsonObject.getJSONObject("code"));
           // System.out.println(a);
            /*Integer errcode = isTrue(jsonObject.getString("code"));
            if(!errcode.equals(0)){
                System.out.println("asdf");
            }else{
               //String data = jsonObject.getString("data");
                JSONObject data = new JSONObject(jsonObject.getString("data"));
                System.out.println(data);
                //System.out.println(data.getString("pageList"));

                JSONArray pages  = data.getJSONArray("pageList");
                System.out.println(pages);
            }*/

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    //判断是否code为合格
    public static int isTrue(String jsonObject) throws JSONException {
        JSONObject code = new JSONObject(jsonObject);
        Integer errcode = Integer.valueOf(code.getString("errcode"));
        return errcode;
    }


}
