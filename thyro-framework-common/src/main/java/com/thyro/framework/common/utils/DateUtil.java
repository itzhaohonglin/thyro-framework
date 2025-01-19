package com.thyro.framework.common.utils;

import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.regex.Pattern;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

/**
 * 新老日期转换
 */
@UtilityClass
public class DateUtil {

    public static final Pattern REGEX_NORM = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?(.\\d{1,6})?)?");
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_HOURS_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MONTH_PATTERN = "yyyy-MM";
    public static final String SIMPLE_MONTH_PATTERN = "yyyyMM";
    public static final String DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
    public static final String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";
    public static final String PURE_DATE_PATTERN = "yyyyMMdd";
    public static final String PURE_TIME_PATTERN = "HHmmss";
    public static final String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";
    public static final String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String UTC_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String UTC_SIMPLE_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String UTC_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String UTC_MS_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 时间字符串格式：yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static LocalDateTime toLocalDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.parse(dateTime, ISO_DATE_TIME);
    }

    /**
     * 时间戳转localDateTime
     *
     * @param timestamp
     * @return
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    public static Boolean isBetweenDateTime(LocalDateTime localDateTime, LocalDateTime startTime, LocalDateTime endTime) {
        if (localDateTime != null && startTime != null && endTime != null) {
            return !(localDateTime.isBefore(startTime) || localDateTime.isAfter(endTime));
        }
        return false;
    }

    public static Boolean isBetweenTime(LocalTime localTime, LocalTime startTime, LocalTime endTime) {
        if (localTime != null && startTime != null && endTime != null) {
            return !(localTime.isBefore(startTime) || localTime.isAfter(endTime));
        }
        return false;
    }

    public static Long betweenHours(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours();
    }

    public static Long betweenMinutes(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toMinutes();
    }

    public static Long toMill(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public LocalDateTime todayStart() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    }

    public LocalDateTime todayEnd() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    public LocalDate getWeekOfMonday() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    public LocalDate getLastDay() {
        return LocalDate.now().minusDays(1);
    }

    public LocalDate getLastWeekMonday() {
        TemporalAdjuster lastMonday = TemporalAdjusters.ofDateAdjuster(
                temporal -> {
                    DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
                    int value = -(dow.getValue() + 6);
                    return temporal.plus(value, ChronoUnit.DAYS);
                });
        return LocalDate.now().with(lastMonday);
    }

    public LocalDate getLastWeekSunday() {
        return LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
    }

    public LocalDate getLastMonthFirstDay() {
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate getLastMonthLastDay() {
        return LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    public LocalDate getMonthFirstDay() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate getMonthLastDay() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

}
