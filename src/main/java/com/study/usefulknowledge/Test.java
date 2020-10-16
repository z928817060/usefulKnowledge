package com.study.usefulknowledge;

public class Test {
     Test(){

    }
    public static void main(String[] args){
        Projection摩卡托投影 projection摩卡托投影=new Projection摩卡托投影();
        for(int i=0;i<1;i++){

            Vector2D vector2D=new Vector2D(i,180);

            System.out.println(projection摩卡托投影.lonLat2Mercator(vector2D).Y);
        }
        System.currentTimeMillis();
    }
}
