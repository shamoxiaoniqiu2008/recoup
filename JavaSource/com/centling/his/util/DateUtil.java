package com.centling.his.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <p>
 * Description:关于时间的工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * Company:Centling
 * </p>
 * 
 * @author: Ade Wang
 * @version: 1.0
 * @Date: 2011-9-30 下午03:31:13
 * @see: com.centling.his.util
 */
public class DateUtil {

	private DateUtil() {

	}

	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * . date 加减天数
	 * 
	 * @param d
	 *            当前日期
	 * @param offset
	 *            偏移天数
	 * @return 偏移后结果
	 */
	public static Date changeDay(Date d, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.set(Calendar.DAY_OF_YEAR,
				(calendar.get(Calendar.DAY_OF_YEAR) + offset));
		return calendar.getTime();
	}

	/**
	 * date 加减天数
	 * 
	 * @param d
	 * @param days
	 * @return
	 */
	public static Date changeDay(Date d, BigDecimal days) {
		Long millPerDay = 24 * 60 * 60 * 1000L;
		Long milSenconds = days.multiply(new BigDecimal(millPerDay))
				.longValue();
		Date temp = (Date) d.clone();
		temp.setTime(d.getTime() + milSenconds);
		return temp;
	}

	/**
	 * 偏移指定小时数
	 */
	public static Date offsetHours(Date d, BigDecimal hours) {
		Long millPerHourLong = 60 * 60 * 1000L;
		Long milSencondsLong = hours.multiply(new BigDecimal(millPerHourLong))
				.setScale(0, RoundingMode.HALF_UP).longValue();
		Date temp = (Date) d.clone();
		temp.setTime(d.getTime() + milSencondsLong);
		return temp;
	}

	/**
	 * . 取一天的零点时间。
	 * 
	 * @param d
	 * @return
	 */
	public static Date integerDay(Date d) {

		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String stringtemp = myFormatter.format(d);
		try {
			d = myFormatter.parse(stringtemp);
		} catch (ParseException e) {
			// 这里不会出错~~~
			e.printStackTrace();
		}
		return d;
	}

	// edited by Damon 2011-9-28 13:55:23
	// 得到当前周周一的时间（周一为一周第一天）
	/**
	 * @return 得到当前周周一的时间（周一为一周第一天）
	 * @author Damon
	 */
	public static Date getCurrentMonday() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_WEEK);
		if (day == 1) {
			c.add(Calendar.DAY_OF_YEAR, -7);
		}
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date date = c.getTime();
		return date;
	}

	public static Date getNextMonday(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 7);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: calculBod
	 * @Description: 根据岁，月，天计算出生日期
	 * @version: 1.0
	 * @param age
	 * @param nowDate
	 * @return
	 * @author ade wang
	 */
	public static Date calculBod(Integer age, Integer month, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		if (age != null) {
			calendar.set(Calendar.YEAR,
					calendar.get(Calendar.YEAR) - age.intValue());
		}
		if (month != null) {
			calendar.set(Calendar.MONTH,
					calendar.get(Calendar.MONTH) - month.intValue());
		}

		if (day != null) {
			calendar.set(Calendar.DAY_OF_MONTH,
					calendar.get(Calendar.DAY_OF_MONTH) - day.intValue());
		}
		return calendar.getTime();
	}

	/**
	 * 返回两个日期相差的天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static long getDistDates(Date startDate, Date endDate) {
		long totalDate = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		long timestart = calendar.getTimeInMillis();
		calendar.setTime(endDate);
		long timeend = calendar.getTimeInMillis();
		totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
		return totalDate;
	}

	/**
	 * 返回两个日期相差的天数。
	 * 
	 * @Description:
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws:
	 * @author:Patrick.Guo
	 * @Date:2012-4-25上午11:47:53
	 * @version:1.0
	 * @Copyright: Copyright (c) 2012 Company:Centling
	 */
	public static BigDecimal getFloatDistDates(Date startDate, Date endDate) {
		BigDecimal start = new BigDecimal(startDate.getTime());
		BigDecimal end = new BigDecimal(endDate.getTime());

		BigDecimal distBigDecimal = end.subtract(start);

		return distBigDecimal.divide(new BigDecimal(1000 * 60 * 60 * 24), 4,
				RoundingMode.HALF_UP);

	}

	/**
	 * 根据出生日期返回当前年龄
	 * 
	 * @param birthday
	 *            出生日期
	 * @return 当前年龄(周岁)
	 * @author Damon
	 */
	public static int getCurrentAge(Date birthday) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(birthday);
		int birthYear = calendar1.get(Calendar.YEAR);
		Calendar calendar2 = Calendar.getInstance();
		int currentYear = calendar2.get(Calendar.YEAR);
		return currentYear - birthYear;
	}

	/**
	 * 
	 * @Title: getNextDay
	 * @Description: 根据当前时间，得到明天的时间
	 * @version: 1.0
	 * @param date
	 * @return
	 * @author adew
	 */
	public static Date getNextDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, 1);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: getYesterDay
	 * @Description: 根据当前时间，得到昨天的时间
	 * @version: 1.0
	 * @param date
	 * @return
	 */
	public static Date getYesterDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(cal.DATE, -1);
		return cal.getTime();
	}

	/**
	 * 
	 * @Title: converteDate
	 * @Description: 将某一个日期的年、月、日设置为传进来的日期的年月日
	 * @version: 1.0
	 * @param reserveDate
	 * @return
	 * @author adew
	 */
	public static Date setYearMonthDay(Date date) {
		Calendar timeCalendar = Calendar.getInstance();
		Date nowDate = getCurrentDate();
		timeCalendar.setTime(nowDate);

		Calendar dateCalendar = Calendar.getInstance();
		dateCalendar.setTime(date);
		timeCalendar.set(Calendar.YEAR, dateCalendar.get(Calendar.YEAR));
		timeCalendar.set(Calendar.MONTH, dateCalendar.get(Calendar.MONTH));
		timeCalendar.set(Calendar.DAY_OF_MONTH,
				dateCalendar.get(Calendar.DAY_OF_MONTH));
		return timeCalendar.getTime();
	}

	/**
	 * 
	 * @Title: setHourOfDate
	 * @Description: 设置传入时间的小时为传入的值，分，秒为0
	 * @version: 1.0
	 * @return
	 */
	public static Date setHourOfDate(Integer hour, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: setHoMiSecToZero
	 * @Description: 将某个时间的时分秒设置为0
	 * @version: 1.0
	 * @param date
	 * @return
	 * @author ade wang
	 */
	public static Date setHoMiSecToZero(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: setHoMiSecToFull
	 * @Description: 将时分秒设置为24:60:60
	 * @version: 1.0
	 * @param date
	 * @return
	 */
	public static Date setHoMiSecToFull(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getFirstDayOfCurrentMonth
	 * @Description: 得到当前月的第一天
	 * @param: args @return
	 * @return: Date 2012-2-8 15:01:07
	 * @version: 1.0
	 */
	public static Date getFirstDayOfCurrentMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getZeroPoint
	 * @Description: 得到相应日期第二天的零点零分零秒
	 * @param: args @param date
	 * @param: args @return
	 * @return: Date
	 * @version: 1.0
	 */
	public static Date getNextDayZeroPoint(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 
	 * 
	 * @Title: getAgeMonthDay
	 * @Description: 今天生日，查出是几岁零几个月零几天
	 * @return:Map<String,Integer>
	 * @version: 1.0
	 */
	public static Map<String, Integer> getAgeMonthDay(Date birthday) {

		Map<String, Integer> map = new HashMap<String, Integer>();

		Calendar c1 = Calendar.getInstance();
		long nowmillSeconds = c1.getTimeInMillis();

		Calendar c2 = Calendar.getInstance();

		c2.setTime(birthday);

		long birmillSeconds = c2.getTimeInMillis();

		Calendar c3 = Calendar.getInstance();

		long millis = nowmillSeconds - birmillSeconds;

		c3.setTimeInMillis(millis);

		int year = c3.get(Calendar.YEAR);

		int month = c3.get(Calendar.MONTH);

		int day = c3.get(Calendar.DAY_OF_MONTH);

		if (year > 1970) {

			map.put("age", year - 1970);
			if (month > Calendar.JANUARY) {
				map.put("month", month - Calendar.JANUARY);
			} else {
				map.put("month", 0);
			}
			if (day > 1) {
				map.put("day", day - 1);
			} else {
				map.put("day", 0);
			}

		} else if (month > Calendar.JANUARY) {
			map.put("age", 0);
			map.put("month", month - Calendar.JANUARY);
			if (day > 1) {
				map.put("day", day - 1);
			} else {
				map.put("day", 0);
			}

		} else if (day > 1) {
			map.put("age", 0);
			map.put("month", 0);
			map.put("day", day - 1);

		} else {

			map.put("age", 0);
			map.put("month", 0);
			map.put("day", 1);
		}
		return map;
	}

	/**
	 * 判断某个字符串是否是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @Title: findLastMonthFirstAndLastDay
	 * @Description: 根据当前日期得到上一个月的第一天和最后一天
	 * @return:Map<String,Date>
	 * @version: 1.0
	 */
	public static Map<String, Date> findLastMonthFirstAndLastDay(Date someDay) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(someDay);

		cal.add(Calendar.MONTH, -1);// 减去一个月
		cal.set(Calendar.DAY_OF_MONTH, 1);// 将日期设置为当月的第一天
		Date firstDayOfMonth = cal.getTime();// 得到某个月的第一天

		cal.add(Calendar.MONTH, 1);// 加一个月
		cal.add(Calendar.DAY_OF_MONTH, -1);// 再减一天
		Date lastDayOfMonth = cal.getTime();// 得到某个月的最后一天

		Map<String, Date> map = new HashMap<String, Date>();
		map.put("firstDayOfMonth", firstDayOfMonth);
		map.put("lastDayOfMonth", lastDayOfMonth);

		return map;
	}

	/**
	 * 
	 * 
	 * @Title: findLastMonthFirstAndLastDay
	 * @Description: 根据当前日期得到上一个月的第一天和最后一天
	 * @return:Map<String,Date>
	 * @version: 1.0
	 */
	public static Map<String, Date> findNextMonthFirstAndLastDay(Date someDay) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(someDay);

		cal.add(Calendar.MONTH, 1);// 减去一个月
		cal.set(Calendar.DAY_OF_MONTH, 1);// 将日期设置为当月的第一天
		Date firstDayOfMonth = cal.getTime();// 得到某个月的第一天

		cal.add(Calendar.MONTH, 1);// 加一个月
		cal.add(Calendar.DAY_OF_MONTH, -1);// 再减一天
		Date lastDayOfMonth = cal.getTime();// 得到某个月的最后一天

		Map<String, Date> map = new HashMap<String, Date>();
		map.put("firstDayOfMonth", firstDayOfMonth);
		map.put("lastDayOfMonth", lastDayOfMonth);

		return map;
	}

	/**
	 * 
	 * 
	 * @Title: findLastMonthAndLastThreeMonth
	 * @Description: 查询过去三个月的第一天和过去三个月的最后一天
	 * @return:Map<String,Date>
	 * @version: 1.0
	 */
	public static Map<String, Date> findLastMonthAndLastThreeMonth(Date someDay) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(someDay);

		cal.add(Calendar.MONTH, -3);// 减去三个个月
		cal.set(Calendar.DAY_OF_MONTH, 1);// 将日期设置为当月的第一天
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date firstDayOfLastMonth = cal.getTime();// 得到某个月的第一天

		cal.add(Calendar.MONTH, 3);
		cal.add(Calendar.DAY_OF_MONTH, -1);// 再减一天
		Date lastDayOfLastThreeMonth = cal.getTime();// 得到某个月的最后一天

		Map<String, Date> map = new HashMap<String, Date>();
		map.put("firstDayOfLastThreeMonth", firstDayOfLastMonth);// 过去第三个月的第一天
		map.put("lastDayOfLastThreeMonth", lastDayOfLastThreeMonth);// 上个月的最后一天

		return map;
	}

	/**
	 * @Title:findBetweenSeveralMonth
	 * @Description 当前月份加减几个月份
	 * @author vxing
	 * @param someDay
	 * @return Map<String,Date>
	 */
	public static Date findBetweenSeveralMonth(Date someDay, int several) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(someDay);
		cal.add(Calendar.MONTH, several);// 加减月份数
		return cal.getTime();
	}

	/**
	 * 将时间设置为传进来的时、分
	 * 
	 * @param date
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date modifyDateToHourAndMinu(Date date, int hour, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 将时间设置为传进来的时、分
	 * 
	 * @param date
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date modifyDateToHourAndMinuAndSec(Date date, int hour,
			int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 取得当前时间是在一周中的星期几。。。。
	 * 
	 * @Description:
	 * @param date
	 * @return
	 * @throws:
	 * @author:Patrick.Guo
	 * @Date:2012-9-12上午11:00:38
	 * @version:1.0
	 * @Copyright: Copyright (c) 2012 Company:Centling
	 */
	public static int getWeekdayFromDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;

	}

	/**
	 * 获得某个时间的时、分
	 * 
	 * @param d
	 * @return
	 */
	public static Map<String, Integer> getHourAndMinute(Date d) {

		Calendar c = Calendar.getInstance();
		c.setTime(d);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("hour", hour);
		map.put("minute", minute);
		return map;
	}

	/**
	 * 获得当年第一天
	 * 
	 * @param d
	 * @return
	 */
	public static Date getFirstDayOfCurrentYear() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getFirstDayOfLastYearMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getFirstDayOfLastYear() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public static Date getTodayOfLastYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 根据年月获得该月第一天的时间
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getEveryMonth(int year, int month) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONDAY, month);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();

	}

	/**
	 * 求两个日期之间的天数(如果间隔为0算作一天)
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return short
	 * @author centling tim.li
	 * 
	 */
	public static Short getDaysFromTwoDate(Date fromDate, Date toDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(fromDate);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		long l1 = c.getTimeInMillis();

		c.setTime(toDate);
		c.set(Calendar.MILLISECOND, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		long l2 = c.getTimeInMillis();

		Short days = (short) ((l2 - l1) / (1000 * 60 * 60 * 24));
		if (days == 0) {
			return 1;
		} else {
			return days;
		}

	}

	/**
	 * 
	 * @Title: unoinDateAndHour
	 * @Description:
	 * @param: args @param date
	 * @param: args @return
	 * @return: Date
	 * @version: 1.0
	 */
	public static Date unoinDateAndHour(Date date, String hourAndMinute) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(date);
		dateStr = dateStr + " " + hourAndMinute;
		try {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date result = sdf.parse(dateStr);
			return result;
		} catch (ParseException e) {
			return null;
		}
	}

}
