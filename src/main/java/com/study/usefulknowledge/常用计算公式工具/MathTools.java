package com.study.usefulknowledge.常用计算公式工具;

/**
 * Created by wingsby on 2018/4/10.
 */
public class MathTools {

    public static boolean isFloatEqual(float a,float b){
        return Math.abs(a-b)<1e-8?true:false;
    }

    public static boolean isDoubleEqual(double a,double b){
        return Math.abs(a-b)<1e-12?true:false;
    }

}
