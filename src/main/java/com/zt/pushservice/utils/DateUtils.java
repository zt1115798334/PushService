package com.zt.pushservice.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Title: DateUtils</p>
 * <p>Description: 日期工具类</p>
 *
 * @author wjc
 * @date 2017年4月6日
 */
public class DateUtils {

    public static final String DATE_FORMAT_SIMPLE = "MM-dd HH:mm";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_SECOND_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_SECOND_FORMAT_2 = "yyyyMMddHHmmss";
    public static final String DATE_SECOND_FORMAT_3 = "yyyy/MM/dd  HH:mm:ss";
    public static final String DATE_SECOND_FORMAT_4 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String CN_DATE_FORMAT = "yyyy年MM月dd日";
    public static final String GREENWICH_DATE_FORMAT ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private DateUtils() {
    }
    /**
     * <p>
     * Description: 获取当前时间的日期对象
     * </p>
     *
     * @return
     * @author wjc
     * @date 2017年1月10日
     */
    public static Date currentDate() {
        DateTime now = new DateTime();
        return now.toDate();
    }

    /**
     * <p>
     * Description: 解析yyyy-MM-dd HH:mm:ss格式的日期字符串，返回日期对象
     * </p>
     *
     * @param date
     * @return
     * @author wjc
     * @date 2016年12月30日
     */
    public static Date parseDate(String date) {
        return parseDate(date, DATE_SECOND_FORMAT);
    }

    /**
     * <p>
     * Description: 使用指定的日期格式解析日期字符串，返回日期对象
     * </p>
     *
     * @param date
     * @param dateFormat
     * @return
     * @author wjc
     * @date 2016年12月30日
     */
    public static Date parseDate(String date, String dateFormat) {
        Date result = null;
        if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(dateFormat)) {
            DateTimeFormatter format = DateTimeFormat.forPattern(dateFormat);
            DateTime dateTime = DateTime.parse(date, format);
            result = dateTime.toDate();
        }

        return result;
    }

    /**
     * <p>
     * Description: 使用指定的格式格式化日期对象，返回格式化后的日期字符串
     * </p>
     *
     * @param date
     * @param dateFormat
     * @return
     * @author wjc
     * @date 2016年12月30日
     */
    public static String formatDate(Date date, String dateFormat) {
        String result = null;
        if (date != null && StringUtils.isNotEmpty(dateFormat)) {
            DateTimeFormatter format = DateTimeFormat.forPattern(dateFormat);
            DateTime dateTime = new DateTime(date.getTime());
            result = dateTime.toString(format);
        }
        return result;
    }

    /**
     * <p>Description: 将日期对象格式化为yyyy-MM-dd格式的字符串</p>
     *
     * @param date
     * @return
     * @author wjc
     * @date 2017年4月6日
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return formatDate(date, DATE_FORMAT);
    }

    /**
     * <p>
     * Description: 将不包含时间信息的日期对象转换为这一天的开始时间的日期对象
     * 例如：将2017-01-01的日期对象转换为表示2017-01-01 00:00:00的日期对象
     * </p>
     *
     * @param date
     * @return
     * @author wjc
     * @date 2017年1月5日
     */
    public static Date getStartDateTimeOfDay(Date date) {
        Date result = null;
        if (date != null) {
            DateTime dateTime = new DateTime(date);
            result = dateTime.withTimeAtStartOfDay().toDate();
        }

        return result;
    }

    /**
     * <p>
     * Description: 将不包含时间信息的日期对象转换为这一天的结束时间的日期对象
     * 例如：将2017-01-01的日期对象转换为表示2017-01-01 23:59:59的日期对象
     * </p>
     *
     * @param date
     * @return
     * @author wjc
     * @date 2017年1月5日
     */
    public static Date getEndDateTimeOfDay(Date date) {
        Date result = null;
        if (date != null) {
            DateTime dateTime = new DateTime(date);
            String dateStr = dateTime.millisOfDay().withMaximumValue().toString(DATE_SECOND_FORMAT);
            result = parseDate(dateStr);
        }

        return result;
    }

    /**
     * <p>Description: 获取当前时期前后分钟的日期</p>
     *
     * @param minute
     * @return
     * @author zhangtong
     * @date 2017年6月21日
     */
    public static Date currentDateAddMinute(Integer minute) {
        DateTime dateTime = new DateTime();
        return dateTime.plusMillis(minute).toDate();
    }

    /**
     * <p>Description: 获取当前时期前后分钟的日期</p>
     *
     * @param minute
     * @param date
     * @return
     * @author zhangtong
     * @date 2017年6月21日
     */
    public static Date currentDateAddMinute(Integer minute, Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minute).toDate();
    }
}
