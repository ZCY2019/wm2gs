package com.gs56;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gaishi_z
 * @create 2019-12-09 16:45
 */
public class Test {
    public static void main(String[] args) {
        Map<String,Object> itemMap = new HashMap<>();
        String skuName = "按斤称重|3斤|包(1";
        if(!skuName.isEmpty()){//是否为多规格
            if (skuName.length()>9){
                if (skuName.contains("(")){
                    skuName = skuName.substring(0,skuName.indexOf("("));
                }else{
                    skuName = skuName.substring(0,skuName.indexOf("（"));
                }

            }
            System.out.println(skuName);
            String[] sku = skuName.split("\\|");
            if (sku.length>1){
                String quantity = sku[1].substring(0,sku[1].length()-1);
                //quantity = String.valueOf((Integer.parseInt(quantity)*Integer.parseInt(mapTypes.get("skuNum").toString())));
                itemMap.put("skuNum",quantity);
            }else{
                itemMap.put("skuNum",1);
            }

        }
    }
}
