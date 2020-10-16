package com.study.usefulknowledge;
/**@title 20个java常用代码
 * String与int互转(一)
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

