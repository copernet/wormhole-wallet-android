package whc.com.whc_wallet.util;

import android.content.Context;
import android.databinding.InverseMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

import whc.com.whc_wallet.R;

/**
 * Created by chuanbei.qiao on 2018/10/25.
 */

public class Converters {

    @InverseMethod("convertIntToString")
    public static int convertStringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String convertIntToString(int value) {
        return String.valueOf(value);
    }

    @InverseMethod("convertLongToString")
    public static long convertStringToLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String convertLongToString(long value) {
        return String.valueOf(value);
    }

    @InverseMethod("convertFloatToString")
    public static float convertStringToFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String convertFloatToString(float value) {
        return String.valueOf(value);
    }

    public static String convertDateToString(long milSecond) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        if (milSecond < 10000) {
            return context.getString(R.string.no_timestamp);
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date(milSecond);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String convertSecondToDate(long milSecond) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        if (milSecond < 10000) {
            return context.getString(R.string.no_timestamp);
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date(milSecond * 1000);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String convertSecondToDate(String milSecond) {
        Context context = com.blankj.utilcode.util.Utils.getApp();
        if ("0".equals(milSecond)) {
            return context.getString(R.string.no_timestamp);
        }
        long milSecondL = Long.valueOf(milSecond);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date(milSecondL * 1000);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

}
