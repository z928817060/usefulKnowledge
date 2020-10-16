package com.study.usefulknowledge;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**@title 20��java���ô���
 **�ַ�����ʱ�以��ת��(��)
 * @create 20130708
 * @author usefulknowledge
 * */
public class StringAndDate {
	
	
	public static void main(String[] args) {
		strToDate();
		System.out.println(dateToStr());
	}

	private static void strToDate() {

		String str=dateToStr();
		SimpleDateFormat dateFormat=new SimpleDateFormat();
		dateFormat.applyPattern("yyyy-MM-dd");
		//str=dateFormat.format(new Date());
		try {
		Date date=	dateFormat.parse(str);
		System.out.println(date);
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		System.out.println(c.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static String dateToStr(){
	String date=	SimpleDateFormat.getDateInstance(DateFormat.SHORT).format(new Date());
		return date;
	}

}

