package com.geo.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author yyq
 */
public final class DateUtils {

    /**
     * 日志
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

    public static final int DEFAULT_LIST_SIZE = 10;

    public static final String DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间格式转换
     *
     * @param format
     * @param date
     * @return
     */
    public static String dateFormat(String format, Date date) {
        String dateStr;
        try {
            dateStr = new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            LOGGER.error("格式化输出时间失败", e);
            dateStr = "";
        }

        return dateStr;
    }

    /**
     * 将字符串转成指定格式的时间
     * 默认格式：yyyy-MM-dd HH:mm:ss
     *
     * @param timeStr
     * @param timeFormat
     * @return
     */
    public static Date strToDate(String timeStr, String timeFormat) {
        String format = StringUtils.isEmpty(timeFormat) ? DATE_FORMAT_1 : timeFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(timeStr);
            System.out.println(date);
        } catch (ParseException e) {
            LOGGER.error("转换失败", e);
        }
        return date;
    }
}
