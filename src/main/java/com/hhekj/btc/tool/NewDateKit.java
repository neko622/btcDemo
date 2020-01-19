package com.hhekj.btc.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author : hux
 * datetime : 2019-05-25 16:28
 * description :
 */
public class NewDateKit {

    /**
     * 获取系统当前时间
     *
     * @return 系统当前时间
     */
    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String day() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String formatday() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }


    /**
     * 时间戳按指定格式转化为日期（String）
     *
     * @param timestamp 时间戳
     */
    public static String timestampToDate(Long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date(timestamp));
    }


    /**
     * 判断一个时间是否在某个时间段里面
     *
     * @param start         开始时间
     * @param end           结束时间
     * @param localDateTime 需要比较的时间
     * @return true | false
     */
    public static boolean isBelong(String start, String end, String localDateTime) {
        //转换的时间格式
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return isBelong(
                LocalDateTime.parse(start, df),
                LocalDateTime.parse(end, df),
                LocalDateTime.parse(localDateTime, df));
    }

    /**
     * 判断一个时间是否在某个时间段里面
     *
     * @param start         开始时间
     * @param end           结束时间
     * @param localDateTime 需要比较的时间
     * @return true | false
     */
    private static boolean isBelong(LocalDateTime start, LocalDateTime end, LocalDateTime localDateTime) {
        // 开始时间之后，结束时间之前
        return start.isBefore(localDateTime) && end.isAfter(localDateTime);
    }

    /**
     * 判断一个时间是否是今天
     * （参数格式：yyyy-MM-dd HH:mm:ss）
     *
     * @param datetime 时间
     */
    public static boolean isToday(String datetime) {
        //将参数时间转成LocalDate
        LocalDate parse = LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        //参数时间和当天对比
        return LocalDate.now().isEqual(parse);
    }

    /**
     * 当前日期加上指定天数
     *
     * @param num 为增加的天数
     */
    public static String nowPlusDay(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        ca.set(Calendar.HOUR_OF_DAY, 24);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        return format.format(ca.getTime());
    }

    /**
     * 获取昨天的日期
     */
    public static String yesterday() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, -1);
        return format.format(ca.getTime());
    }


    public static String today() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        return format.format(ca.getTime());
    }

    public static LocalDateTime toLdt(String str) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, pattern);
    }

    /**
     * 获取上周时间
     */
    public static String lastWeek() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        return format.format(d);
    }

    /**
     * 获取当前时间前num分钟
     */
    public static String beforeMin(Integer num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (num == null || num == 0) return sdf.format(new Date());
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -num);// Num分钟之前的时间
        Date beforeD = beforeTime.getTime();
        return sdf.format(beforeD);
    }

    /**
     * 获取当前时间后num分钟
     */
    public static String afterMin(Integer num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (num == null || num == 0) return sdf.format(new Date());
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, num);// Num分钟之后的时间
        Date beforeD = beforeTime.getTime();
        return sdf.format(beforeD);
    }

    /**
     * 获取当前时间前num小时
     */
    public static String beforeHour(Integer num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        if (num == null || num == 0) return sdf.format(new Date());
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.HOUR, -num);// Num分钟之前的时间
        Date beforeD = beforeTime.getTime();
        return sdf.format(beforeD);
    }

    /**
     * 获取当前时间前num天
     */
    public static String beforeDay(Integer num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (num == null || num == 0) return sdf.format(new Date());
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.DAY_OF_MONTH, -num);
        Date beforeD = beforeTime.getTime();
        return sdf.format(beforeD);
    }

    /**
     * 获取固定间隔时刻集合
     *
     * @param start    开始时间
     * @param end      结束时间
     * @param interval 时间间隔(单位：分钟)
     * @param format   格式化规则 yyyy-MM-dd HH:mm:ss
     */
    public static List<String> getIntervalTimeList(String start, String end, int interval, String format) {
        Date startDate = convertString2Date(format, start);
        Date endDate = convertString2Date(format, end);
        if (startDate == null || endDate == null) return null;
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(startDate);
//        System.out.println("startDate:"+startDate);
//        System.out.println("endDate:"+endDate);
        int num = 0;
        int ys = 0;
        if (format.equals("yyyy-MM-dd HH:mm")) {
            num = rightNow.get(Calendar.MINUTE);
            if (num > interval) ys = num - (num % interval);
            rightNow.set(Calendar.MINUTE, ys);
        } else if (format.equals("yyyy-MM-dd HH")) {
            num = rightNow.get(Calendar.HOUR);
            System.out.println("num:" + num);
            if (num > interval) ys = num - (num % interval);
            rightNow.set(Calendar.HOUR, ys);
        } else if (format.equals("yyyy-MM-dd")) {
            num = rightNow.get(Calendar.DAY_OF_MONTH);
            if (num > interval) ys = num - (num % interval);
            rightNow.set(Calendar.DAY_OF_MONTH, ys);
        }
        startDate = rightNow.getTime();
        List<String> list = new ArrayList<>();
        while (startDate.getTime() <= endDate.getTime()) {
            list.add(convertDate2String(format, startDate));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MINUTE, interval);
            if (calendar.getTime().getTime() > endDate.getTime()) {
                if (!startDate.equals(endDate)) {
                    list.add(convertDate2String(format, endDate));
                }
                startDate = calendar.getTime();
            } else {
                startDate = calendar.getTime();
            }
        }
//        System.out.println("list:" + list.size());
        if (list.size() > 50) {
            list = list.subList(list.size() - 50, list.size());
        }
//        System.out.println("list:" + list.size());
//        System.out.println("list:" + list);
        return list;
    }

    public static Date convertString2Date(String format, String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertDate2String(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 切割時間段
     *
     * @param dateType 交易類型 M/D/H/N -->每月/每天/每小時/每分鐘
     * @param start    yyyy-MM-dd HH:mm:ss
     * @param end      yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static List<String> cutDate(String dateType, String start, String end, int interval, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date dBegin = sdf.parse(start);
            Date dEnd = sdf.parse(end);
            return findDates(dateType, dBegin, dEnd, interval, format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> findDates(String dateType, Date dBegin, Date dEnd, int interval, String format) {
        List<String> listDate = new ArrayList<>();
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (calEnd.after(calBegin)) {
            switch (dateType) {
                case "M":
                    calBegin.add(Calendar.MONTH, interval);
                    break;
                case "D":
                    calBegin.add(Calendar.DAY_OF_YEAR, interval);
                    break;
                case "H":
                    calBegin.add(Calendar.HOUR, interval);
                    break;
                case "N":
                    calBegin.add(Calendar.MINUTE, interval);
                    break;
            }
            if (calEnd.after(calBegin))
                listDate.add(new SimpleDateFormat(format).format(calBegin.getTime()));
            else
                listDate.add(new SimpleDateFormat(format).format(calEnd.getTime()));
        }
//        System.out.println("list:"+listDate.size());
        return listDate;
    }

    public static String formatDate(String dateType,String date, int interval, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date newDate = sdf.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDate);
        switch (dateType) {
            case "M":
                calendar.add(Calendar.MONTH, -calendar.get(Calendar.MONTH) % interval);
                break;
            case "D":
                calendar.add(Calendar.DAY_OF_YEAR, -calendar.get(Calendar.DAY_OF_YEAR) % interval);
                break;
            case "H":
                calendar.add(Calendar.HOUR, -calendar.get(Calendar.HOUR) % interval);
                break;
            case "N":
                calendar.add(Calendar.MINUTE, -calendar.get(Calendar.MINUTE) % interval);
                break;
        }
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

}
