package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


//该工具以周一为一周的开始

/**
 * @author lxbccsu
 *日期比较差值不包括起始日期,包括最后日期
 */
public class CauculateMonthUtil{
	
	private final static Integer MONTH=30;
	
	public static void main(String[] args) {
		
		CauculateMonthUtil dateCalculate = CauculateMonthUtil.calculate("2011/03/17", "2012/02/13");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2011/03/30", "2011/04/13");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2011/03/31", "2011/04/13");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2011/03/17", "2011/04/18");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2011/03/17", "2012/01/13");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2011/03/31", "2012/03/13");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2011/03/31", "2012/05/13");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2011/03/31", "2012/05/13");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2013/12/17", "2014/01/17");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
		dateCalculate = CauculateMonthUtil.calculate("2013/03/01", "2013/05/31");
		System.out.println("月差为: " + dateCalculate.getDifferenceOfMonths());
		System.out.println("天差为: " + dateCalculate.getDifferenceOfDays());
		System.out.println();
		
	}
	
	private long differenceOfMonths;//月份差值
	private long differenceOfDays;//天数差值
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	public static CauculateMonthUtil calculate(String startdate, String endDate){
		try {
			return calculate(dateFormat.parse(startdate),dateFormat.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 计算差值,注意 endDate > startDate
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static CauculateMonthUtil calculate(Date startDate, Date endDate){
		startDate=formatDate(startDate);
		endDate=formatDate(endDate);
		if(startDate.after(endDate)) return calculate(endDate,startDate);
		System.out.println("开始日：" + dateFormat.format(startDate) + ", 结束日: "+ dateFormat.format(endDate));
		CauculateMonthUtil dataCalculate = new CauculateMonthUtil();
		
		Calendar firstDay = Calendar.getInstance();
		Calendar lastDay = Calendar.getInstance();
		firstDay.setTime(startDate);
		lastDay.setTime(endDate);
		
		//算出天数总差值
		long allDays = ((lastDay.getTimeInMillis()) - (firstDay.getTimeInMillis()))/(1000*24*60*60);

		Calendar loopEndDay = calculateLoopEndOfDate(firstDay,lastDay);
		System.out.println("循环终止日期 : " + dateFormat.format(loopEndDay.getTime()));
		
		dataCalculate.setDifferenceOfDays(0);
		dataCalculate.setDifferenceOfMonths(0);
		
		int month = firstDay.get(Calendar.MONTH);
		while(!firstDay.equals(loopEndDay)){
			firstDay.add(Calendar.DAY_OF_MONTH, 1);
			allDays--;
			if(month != firstDay.get(Calendar.MONTH)){
				month = firstDay.get(Calendar.MONTH);
				dataCalculate.setDifferenceOfMonths(dataCalculate.getDifferenceOfMonths()+1);
			}
		}
		dataCalculate.setDifferenceOfDays(allDays);
		return dataCalculate;
		
	}
	
	/**
	 * 将传入的日期参数 的时分秒毫秒 设置为0
	 * @param date
	 * @return
	 */
	private static Date formatDate(Date date){
		Calendar calender=Calendar.getInstance();
		calender.setTime(date);
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);
		return calender.getTime();
	}

	/**
	 * 计算循环终止日期
	 * 例如:开始日：2011/03/17    结束日 2012/02/13 ,循环终止日期 2012/01/17;
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private static Calendar calculateLoopEndOfDate(Calendar startDate, Calendar endDate) {
		int year = endDate.get(Calendar.YEAR);
		int month = endDate.get(Calendar.MONTH);
		int startday = startDate.get(Calendar.DAY_OF_MONTH);
		int endday = endDate.get(Calendar.DAY_OF_MONTH);
		int maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));
		
		if(year > startDate.get(Calendar.YEAR)){
			if(month == Calendar.JANUARY  ){
				if(endday < startday){
					year -= 1;
					month = Calendar.DECEMBER;
				}
			}else{
				if(startday > maxDaysInMonth){
					month -= 1;
					endDate.set(year, month, 1);
					startday = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));
				}else{
					if(startday > endDate.get(Calendar.DAY_OF_MONTH)){
						month -= 1;
						endDate.set(year, month, 1);
						maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));;
						if(startday > maxDaysInMonth){
							startday = maxDaysInMonth;
						}
					}
				}
			}
		}else{
			if(startday > maxDaysInMonth){
				month -= 1;
				endDate.set(year, month, 1);
				startday = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));
			}else{
				if(startday > endDate.get(Calendar.DAY_OF_MONTH)){
					month -= 1;
					endDate.set(year, month, 1);
					maxDaysInMonth = getMaxDaysOfMonth(new GregorianCalendar(year,month,1));
					if(startday > maxDaysInMonth){
						startday = maxDaysInMonth;
					}
				}
			}
		}
		
		return new GregorianCalendar(year, month, startday);
	}

	/**
	 * 获取一月最大天数,考虑年份是否为润年
	 * (对API中的 getMaximum(int field)不了解, date.getMaximum(Calendar.DAY_OF_MONTH)却不是月份的最大天数)
	 * @param date
	 * @return
	 */
	private static int getMaxDaysOfMonth(GregorianCalendar date) {
		int month = date.get(Calendar.MONTH);
		int maxDays  = 0;
		switch(month){
			case Calendar.JANUARY:
			case Calendar.MARCH:
			case Calendar.MAY:
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.OCTOBER:
			case Calendar.DECEMBER:
			maxDays = 31;
			break;
			case Calendar.APRIL:
			case Calendar.JUNE:
			case Calendar.SEPTEMBER:
			case Calendar.NOVEMBER:
			maxDays = 30;
			break;
			case Calendar.FEBRUARY:
			if(date.isLeapYear(date.get(Calendar.YEAR))){
				maxDays = 29;
			}else{
				maxDays = 28;
			}
			break;
		}
		return maxDays;
	}

	public long getDifferenceOfMonths() {
		return differenceOfMonths;
	}

	public void setDifferenceOfMonths(long differenceOfmonths) {
		this.differenceOfMonths = differenceOfmonths;
	}

	public long getDifferenceOfDays() {
		return differenceOfDays;
	}

	public void setDifferenceOfDays(long differenceOfDays) {
		this.differenceOfDays = differenceOfDays;
	}
	
	
	/**
	 * 自然月算法
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Float cauculateDiffDay(Date date1,Date date2){
		Calendar calender1 = Calendar.getInstance();
		Calendar calender2 = Calendar.getInstance();
		calender1.setTime(date1);
		calender2.setTime(date2);
		int year1 = calender1.get(Calendar.YEAR);
		int year2 = calender2.get(Calendar.YEAR);
		int month1 = calender1.get(Calendar.MONTH);
		int month2 = calender2.get(Calendar.MONTH);
		
		//如果是同一个月,直接计算相差天数 左开右闭
		if(year1 == year2 && month1 == month2){
			//取到结束日期所在月的最后一天
			Calendar calendar3 = Calendar.getInstance();
			calendar3.set(Calendar.YEAR, year2);
			calendar3.set(Calendar.MONTH,month2 + 1);
			calendar3.set(Calendar.DAY_OF_MONTH,0);
			calendar3.set(Calendar.HOUR_OF_DAY,0);
			calendar3.set(Calendar.MINUTE,0);
			calendar3.set(Calendar.SECOND,0);
			calendar3.set(Calendar.MILLISECOND,0);
			Date lastDay = calendar3.getTime();
			//如果是同一个月的第一天和最后一天，返回1
			if( calender1.get(Calendar.DAY_OF_MONTH) == 1 && date2.equals(lastDay)){
				return new Float(1);
			}else{
				calender1.set(Calendar.DAY_OF_MONTH, calender1.get(Calendar.DAY_OF_MONTH) - 1);
				date1 = calender1.getTime();
				CauculateMonthUtil dateCalculate=CauculateMonthUtil.calculate(date1,date2);
				Long days=dateCalculate.getDifferenceOfDays();
				return days.floatValue()/MONTH;
			}
		}else{//如果不是同一个月
			//两日期相差月数
			Float month = new Float((year2 - year1) * 12 + (month2 - month1)) - 1;
			Float day = new Float(0);
			if(calender1.get(Calendar.DAY_OF_MONTH)== 1){//如果开始日期是本月第一天，则月数+1
				month ++;
			}else{//如果不是第一天，就计算从开始日期到下个月1号之间的天数，左闭右开
				calender1.set(Calendar.MONTH, calender1.get(Calendar.MONTH) + 1);
				calender1.set(Calendar.DAY_OF_MONTH, 1);
				Date standardDate1 = calender1.getTime();
				CauculateMonthUtil dateCalculate=CauculateMonthUtil.calculate(date1,standardDate1);
				day += dateCalculate.getDifferenceOfDays();
			}
			//取到结束日期所在月的最后一天
			Calendar calendar3 = Calendar.getInstance();
			calendar3.set(Calendar.YEAR, year2);
			calendar3.set(Calendar.MONTH,month2 + 1);
			calendar3.set(Calendar.DAY_OF_MONTH,0);
			calendar3.set(Calendar.HOUR_OF_DAY,0);
			calendar3.set(Calendar.MINUTE,0);
			calendar3.set(Calendar.SECOND,0);
			calendar3.set(Calendar.MILLISECOND,0);
			Date lastDay = calendar3.getTime();
			if(date2.equals(lastDay)){//如果结束日期是当月的最后一天，则月数+1
				month ++;
			}else{//否则计算前一个月的最后一天的天数，左开右闭
				calendar3.set(Calendar.MONTH,month2 );
				calendar3.set(Calendar.DAY_OF_MONTH,1);
				lastDay = calendar3.getTime();
				Calendar calendar4 = Calendar.getInstance();
				calendar4.setTime(date2);
				calendar4.set(Calendar.DAY_OF_MONTH,calendar4.get(Calendar.DAY_OF_MONTH) + 1);
				Date standardDate2 = calendar4.getTime();
				CauculateMonthUtil dateCalculate=CauculateMonthUtil.calculate(lastDay,standardDate2);
				month += dateCalculate.getDifferenceOfMonths();
				day += dateCalculate.getDifferenceOfDays();
			}
			return month+day/MONTH;
		}
	}
	
}