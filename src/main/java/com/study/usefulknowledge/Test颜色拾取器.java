package com.study.usefulknowledge;

import java.awt.*;

public class Test颜色拾取器
{
    public void Test(){
    }
    public static void main(String[] args) {
        while (true) {
            Point mousepoint = MouseInfo.getPointerInfo().getLocation();
            Robot r = null;
            try {
                r = new Robot();
            } catch (AWTException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(r.getPixelColor(mousepoint.x, mousepoint.y));

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}