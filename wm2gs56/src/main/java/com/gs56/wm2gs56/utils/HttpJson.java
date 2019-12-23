package com.gs56.wm2gs56.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * @author gaishi_z
 * @create 2019-11-12 10:14
 */
public class HttpJson {

    public static JSONObject doPost(String method ,JSONObject data) {
        HttpClient client = HttpClients.createDefault();
        // 要调用的接口方法
        String url = "https://dopen.weimob.com/api/1_0/" + method +
                "?accesstoken="+ PropertiesUtils.getKeyValue("access_token");
        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            StringEntity s = new StringEntity(data.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            post.setEntity(s);
            post.addHeader("content-type", "text/xml");
            HttpResponse res = client.execute(post);
            String response1 = EntityUtils.toString(res.getEntity());

            if (res.getStatusLine().getStatusCode() == 200) {
                JSONObject j = new JSONObject(response1);
                return j;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    //判断是否code为合格
    public static String isTrue(JSONObject jsonObject)  {
        String a = null;
        try {
             a = jsonObject.get("errcode").toString();

            return a;
        }catch (Exception e){
            e.printStackTrace();
        }


        return a;
    }

    public static String is_True(JSONObject jsonObject) throws JSONException {

        String err_code = jsonObject.getString("errcode");

        return err_code;
    }
}
