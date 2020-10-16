package com.study.usefulknowledge;

import java.text.DecimalFormat;

public class DecimalFormat数据格式化以及数组列表习惯 {
    private static final DecimalFormat a = new DecimalFormat("0.00");  //用于格式化数据的类 (可以是："##.##","0，000"逗号的)
    String b= a.format(1.2768768);


}
