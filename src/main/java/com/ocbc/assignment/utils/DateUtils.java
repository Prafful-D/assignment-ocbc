package com.ocbc.assignment.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author PraffulD
 */
public class DateUtils {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    public static Date currentDate(){
        return new Date();
    }
}
