package com.study.usefulknowledge;
/**@title 20��java���ô���
 * ��ȡ��ǰ���õķ�����(��)
 * @create 20130708
 * @author usefulknowledge
 * */
public class CurrentMethod返回当前方法 {
	
	public static void main(String[] args) {
		String methodName=Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println(methodName);
	}

}

