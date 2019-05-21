package com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class base {

    //获取当前时间
    public String getCurrTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    //字符串转long list
    public List<Long> stringToLongList(String str){
        List<Long> list = new ArrayList<Long>();

        String[] split=str.split("\\+");
        for(int i=0,len=split.length;i<len;i++){
            list.add(i,Long.parseLong(split[i].toString()));
        }

        return list;
    }


    public String longListToString(List<Long> list){
        StringBuffer str = new StringBuffer();
        str.append(list.get(0));

        for(int i=1;i<list.size();i++){
            str.append("+"+list.get(i));
        }
        return str.toString();
    }
}