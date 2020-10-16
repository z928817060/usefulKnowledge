package com.study.usefulknowledge.常用正则;

/**
 * Created by Administrator on 2018/4/17.
 * http://www.runoob.com/java/java-regular-expressions.html
 */
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegexMatches正则匹配
{
    /**
     * 正则表达式匹配两个指定字符串中间的内容(多个就重复给出)
     * @param soap
     * @return
     */
    public static List<String> getSubUtil(String soap,String rgex){
        List<String> list = new ArrayList<String>();
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            int i = 1;
            list.add(m.group(i));
            i++;
        }
        return list;
    }
    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个，方法与getSubUtil一样
     * @param soap
     * @param rgex
     * @return
     */
    public static String getSubUtilSimple(String soap,String rgex){
        Pattern pattern = Pattern.compile(rgex);// 匹配的模式
        Matcher m = pattern.matcher(soap);
        while(m.find()){

//            return m.group(0);  //截取匹配[],有包含的关系，如 截取a,c时，abc
            return m.group(1);    //截取匹配（），无包含的关系 b
        }
        return "";
    }

/**
//总规则：含有Pattern（规则），Matcher（匹配源），group（结果）
//        eg.有个字符串 aeb2dc111
//        Pattern（规则）：a (.*?) c ,表示截取了ac之间的
//                          (.*?)表示作用在整个字符串之间的
//                          （）表示输出的结果，几个，就表示几个结果
//        group（结果）: 假如有一个（），结果就有2个，group（0）（作用域的字符串），group（1）括号里表示的东西
//                      假如有两个（），结果就有3个，group（0）（作用域的字符串），group（1）第一个括号里表示的东西，group（2）第二个括号里表示的东西
规则参考 http://www.runoob.com/java/java-regular-expressions.html
 */
     public static void main( String args[] ){

        String str = "abc3443abcfgjhgabcgfjabc";
        String rgex = "abc(.*?)abc";
        /**
         * 直接字符串匹配判斷boolean，但是無法獲取內容
         */
        System.out.println(str.matches(rgex));
//**********************************************************************************
         /**
          * 利用方法匹配，可獲取內容
          */
        System.out.println(getSubUtil(str,rgex));
        System.out.println(getSubUtilSimple(str, rgex));
//**********************************************************************************
        // 按指定模式在字符串查找
        String line = "This order was placed for QT3000! OK?";
        String pattern = "(\\D*)(\\d+)(.*)";    //非数字、数字、任何
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find( )) {
            System.out.println("Found value: " + m.group(0) );
            System.out.println("Found value: " + m.group(1) ); //This order was placed for QT
            System.out.println("Found value: " + m.group(2) );  //3000
            System.out.println("Found value: " + m.group(3) );  //! OK?
        } else {
            System.out.println("NO MATCH");
        }

//************************************************************************************************

    }
}