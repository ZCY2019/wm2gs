package com.gs56.wm2gs56.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gaishi_z
 * @create 2019-11-12 16:52
 */
public class Date2TimeStamp {
        /**
         * 时间戳转换成日期格式字符串
         * @param seconds 精确到秒的字符串
         * @return
         */
        public static String timeStamp2Date(String seconds,String format) {
            if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
                return "";
            }
            if(format == null || format.isEmpty()){
                format = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(Long.valueOf(seconds+"000")));
        }
        /**
         * 日期格式字符串转换成时间戳
         * @param format 如：yyyy-MM-dd HH:mm:ss
         * @return
         */
        public static String date2TimeStamp(String date_str,String format){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return String.valueOf(sdf.parse(date_str).getTime()/1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * 取得当前时间戳（精确到秒）
         * @return
         */
        public static String timeStamp(){
            long time = System.currentTimeMillis();
            String t = String.valueOf(time);
            return t;
        }


        /**
         * 昨日23:59:59*/
        public static String yesterDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)-1,23,59,59);
        long tt = calendar.getTime().getTime();
        String t = String.valueOf(tt);
        return t;
    }
    public static String yesterHour(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY)-1,calendar.get(Calendar.MINUTE),calendar.get(Calendar.SECOND));
        long tt = calendar.getTime().getTime();
        String t = String.valueOf(tt);
        return t;
    }


        public static void main(String[] args) {
            System.out.println(yesterHour());
            /*yesterDay();

            String timeStamp = timeStamp();
            System.out.println("timeStamp="+timeStamp); //运行输出:timeStamp=1470278082

            //该方法的作用是返回当前的计算机时间，时间的表达格式为当前计算机时间和GMT时间(格林威治时间)1970年1月1号0时0分0秒所差的毫秒数

            String date = timeStamp2Date(timeStamp, "yyyy-MM-dd HH:mm:ss");
            System.out.println("date="+date);//运行输出:date=2016-08-04 10:34:42

            String timeStamp2 = date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss");
            System.out.println(timeStamp2);  //运行输出:1470278082

            String timeStamp3 = date2TimeStamp("2019-11-11 12:00:00", "yyyy-MM-dd HH:mm:ss");
            System.out.println(timeStamp3);*/
            System.out.println(stampToDate("1573808053185"));

        }

        public static String stampToDate(String s){
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        }

        public static String longToDate(Long lo) throws ParseException {
            /*String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);*/

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
            String sd = sdf.format(new Date(lo));   // 时间戳转换成时间
            System.out.println(sd);//打印出你要的时间

            return sd;
        }
}

