package com.study.usefulknowledge;
/**@title 20��java���ô���
 * String��int��ת(һ)
 * @create 20130708
 * @author usefulknowledge
 * */
public class StringAndInt {
	
	public int stringToInt(String str){
		int defaultVal=0;
		try {
			defaultVal=Integer.parseInt(str);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return defaultVal;
	}
	public String intToString(int i){
		
		String defaultStr="";
		
		try {
			defaultStr=String.valueOf(i);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return defaultStr;
		
	}

}

