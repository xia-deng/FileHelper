package filehelper.helper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * long转为String
	 * 
	 * @param dateLong
	 * @param format
	 * @return
	 */
	public static String longToDateString(long dateLong, String format) {

		Date date = new Date(dateLong);

		return dateToString(date, format);
	}

	/**
	 * long转为Date
	 */
	public static Date longToDate(long dateLong, String format) {

		return stringToDate(longToDateString(dateLong, format), format);
	}

	/**
	 * String转为Date
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String strDate, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date(strDate);
		}
	}

	/**
	 * Date转为String
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * String转为SQL的Timestamp
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static Timestamp stringToSqlTimestamp(String time, String format) {

		Date date = stringToDate(time, format);

		return new Timestamp(date.getTime());
	}

	/**
	 * ��java.util.Date转化为SQL的Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp dateToSqlTimestamp(Date date) {

		return new Timestamp(date.getTime());
	}


	
	/**
	 * 获取当前时间和N个月之前的时间之间间隔的天数（或者N个月之后）
	 * @param last  为正则是N月之前，为负则为N月之后
	 * @return
	 */
	public static int returnDaysOfLastNMonth(int last) {

		Calendar calendar = Calendar.getInstance();

		int nowMonth = calendar.get(Calendar.MONTH);
		int month = nowMonth - last;

		calendar.set(Calendar.MONTH, month);

		long diffs = Math.abs( new Date().getTime() - calendar.getTime().getTime());
		return (int) (diffs / (1000 * 60 * 60 * 24));
	}

	
	
	/**
	 * 获取距离当前时间N个小时/分钟/天数/秒之前的时间（或者N个小时/分钟/天数/秒之后）
	 * @param last  为正则是N小时/分钟/天数/秒之前，为负则为N小时/分钟/天数/秒之后
	 * @return
	 */
	public static Date returnLastNTime(int last,int calendarType){
		
		Calendar calendar = Calendar.getInstance();
		int hour=calendar.get(calendarType);
		calendar.set(calendarType, hour-last);
		return calendar.getTime();
	}
	
	
	/**
	 * 获取两个时间之间的间隔：N个小时/分钟/天数/秒/月/年
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param calendarType 间隔类型：小时/分钟/天数/秒月/年
	 * @return
	 */
	public static int returnTimesBetweenTwoDates(Date startDate,Date endDate,int calendarType){
	
		long diffs = Math.abs( endDate.getTime() - startDate.getTime());
		double result=0;
		
		switch(calendarType){
		case Calendar.SECOND:
			result=diffs / (1000);
			break;
		case Calendar.MINUTE:
			result=diffs / (1000 * 60);
			break;
		case Calendar.HOUR_OF_DAY:
			result=diffs / (1000 * 60 * 60);
			break;
		case Calendar.DAY_OF_MONTH:
			result=diffs / (1000 * 60 * 60 * 24);
			break;
		case Calendar.MONTH:
			double temp=diffs / (1000 * 60 * 60 * 24);
			int intdayTemp=(int) Math.ceil(temp);
			Calendar cal=Calendar.getInstance();
			int nowMonth=cal.get(Calendar.MONTH);
			int nowDay=cal.get(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, nowDay-intdayTemp);
			int newMonth=cal.get(Calendar.MONTH);
			result=nowMonth-newMonth;
			break;
		case Calendar.YEAR:
			double temp2=diffs / (1000 * 60 * 60 * 24);
			int intdayTemp2=(int) Math.ceil(temp2);
			Calendar cal2=Calendar.getInstance();
			int nowYear=cal2.get(Calendar.YEAR);
			int nowDay2=cal2.get(Calendar.DAY_OF_MONTH);
			cal2.set(Calendar.DAY_OF_YEAR, nowDay2-intdayTemp2);
			int newYear=cal2.get(Calendar.MONTH);
			result=nowYear-newYear;
			break;
		}
		
		return (int) Math.ceil(result);
	}
	
	/**
	 * 返回本月第一天
	 * @return
	 */
	public static Date firstDayOfMonth(){
		
		Calendar calendar=Calendar.getInstance();
		int year= calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		//int day=calendar.get(Calendar.DAY_OF_MONTH);
		//cal.getActualMaximum(Calendar.DATE)
		String firstDay=year+"-"+month+"-"+"01";
		return stringToDate(firstDay, "yyyy-MM-dd");
	}
	
	/**
	 * 返回本月最后一天
	 * @return
	 */
    public static Date lastDayOfMonth(){
		
		Calendar calendar=Calendar.getInstance();
		int year= calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		//int day=calendar.get(Calendar.DAY_OF_MONTH);
		int last=calendar.getActualMaximum(Calendar.DATE);
		String firstDay=year+"-"+month+"-"+last;
		return stringToDate(firstDay, "yyyy-MM-dd");
	}
}
