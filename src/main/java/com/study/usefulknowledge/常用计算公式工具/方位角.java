package com.study.usefulknowledge.常用计算公式工具;

/**
 * Created by Administrator on 2018/5/14.
 */
public class 方位角 {
    public 方位角() {
    }

    public static void main(String[] args) {
        System.out.println(cal(0,0,-100,1));
        System.out.println(cal(0,0,-1,-1));
        System.out.println(cal(0,0,1,-1));
        System.out.println(cal(0,0,1,1));

    }
    /**
     * 正负号处理
     * @param i
     * @return
     */
    static int sgn(int i){
        if(i > 0 )
            return 1;
        if(i<0){
            return -1;
        }
        return 0;
    }

    /**
     * 计算点2 对于点1 的方位角，正北方向 为0, 正西向为90 正南为180 正东为 270
     * @param x1 自身点x1
     * @param y1 自身点y1
     * @param x2 目标点x2
     * @param y2 目标点y2
     * @return
     */
    public static double cal(int x1,int y1,int x2,int y2){

        double z = 0;
        if(x1 - x2!=0){
            z = (180 + 90 * sgn(x2 - x1) + Math.toDegrees(Math.atan((y2*1.0 - y1) / (x2 - x1 ) )));
        }else{
            z = 90 - 90* sgn(y2 - y1);
        }

        return z;
    }
}
