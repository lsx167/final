package com.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class base {

    public String getCurrTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
}
