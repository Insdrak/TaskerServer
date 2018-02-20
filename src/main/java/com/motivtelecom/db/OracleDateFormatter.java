package com.motivtelecom.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Aoi on 27.01.2017.
 */
public class OracleDateFormatter {

    private static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    @SuppressWarnings("unused")
    public static String formatWithDf(Date date) {
        return df.format(date);
    }
    @SuppressWarnings("unused")
    public static Date parseWithDf(String inStringDate) {
        try {
            return df.parse(inStringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
