package com.study.usefulknowledge.常用计算公式工具.插值;

//import net.xhms.commons.mathutils.ConstantVar;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by wingsby on 2018/4/10.
 * 思路：高度层的插值：根据海拔高度，对需要要素进行插值。
 * 例如：已知高度[50,150,300]m,温度[18,15,13]度，求100m处的温度，线性插值和拉格朗日都可以
 */
public class LagrangeInterpolation {
    static Logger logger = Logger.getLogger(LagrangeInterpolation.class);
    /**
     * 两点的拉格朗日插值
     * @param input   list: float[]{结果列} float[]{输入列}
     * @param ch    插入输入列的值
     * @return
     * 注意：插值结果可能很离奇，因为是非线性，相当于二阶多项式插值
     */
    //    要求hh从低到高、hh及vals无空值==先使用formatData函数处理
    public static float interpolation3(List<Float[]> input, float ch) {
        if (input == null) {
            logger.error("输入为空值");
            return ConstantVar.NullValF;
        }
        if (input.size() == 2) {
            if (input.get(0).length <= 2 || input.get(1).length <= 2) {
                logger.error("有效输入不足3");
                return ConstantVar.NullValF;
            }
        } else {
            logger.error("输入数据缺失");
            return ConstantVar.NullValF;
        }
        Float[] y = input.get(0);
        Float[] x = input.get(1);
        //find nearest 3 points
        int[] idx = new int[3];
        boolean flag=false;
        for (int i = 0; i < x.length - 1; i++) {
            if (ch >= x[i] && ch <= x[i + 1]) {
                idx[0] = i;
                idx[1] = i + 1;
                if (i > 0 && i < x.length - 2) {
                    if (Math.abs(ch - x[i - 1]) > Math.abs(ch - x[i + 2]))
                        idx[2] = i + 2;
                    else idx[2] = i - 1;
                } else {
                    if (i == 0) idx[2] = i + 2;
                    if (i == x.length - 2) idx[2] = i - 1;
                }
                flag=true;
                break;
            }
        }
        if(!flag){
            logger.info("无法实现外插，请检查输入");
            return ConstantVar.NullValF;
        }
        // 开始插值
        float res = (ch - x[idx[1]]) * (ch - x[idx[2]]) / ((x[idx[0]] - x[idx[1]]) * (x[idx[0]] - x[idx[2]])) *
                y[idx[0]] + (ch - x[idx[0]]) * (ch - x[idx[2]]) / ((x[idx[1]] - x[idx[0]]) * (x[idx[1]] - x[idx[2]])) *
                y[idx[1]] + (ch - x[idx[0]]) * (ch - x[idx[1]]) / ((x[idx[2]] - x[idx[0]]) * (x[idx[2]] - x[idx[1]])) *
                y[idx[2]];

        return res;
    }
    /**
     * 线性插值
     * @param input
     * @param ch
     * @return
     */
    public static float LinearInterpolation(List<Float[]> input, float ch){
        Float[] y = input.get(0);
        Float[] x = input.get(1);
        //find nearest 2 points
        int[] idx = new int[3];
        boolean flag=false;
        for (int i = 0; i < x.length - 1; i++) {
            if (ch >= x[i] && ch <= x[i + 1]) {
                idx[0] = i;
                idx[1] = i + 1;
                flag=true;
                break;
            }
        }
        if(!flag){
            logger.info("无法实现内插，请检查输入");
            return ConstantVar.NullValF;
        }
        float res=y[idx[0]]+(y[idx[1]]-y[idx[0]])/(x[idx[1]]-x[idx[0]])*(ch-x[idx[0]]);
        return res;
    }

    /**
     * 整理数据格式
     * @param vals
     * @param hh
     * @param gh
     * @param desc
     * @return
     */
    public static List<Float[]> formatData(List<Float> vals, List<Float> hh,float gh,boolean desc) {
        if (vals == null || hh == null) return null;
        if (vals.size() != hh.size()) return null;
        List<Float[]> res = new ArrayList<>();
        for (int i = 0; i < vals.size(); i++) {
            if (Math.abs(vals.get(i) - ConstantVar.NullValF) < 1e-6 ||
                    Math.abs(hh.get(i) - ConstantVar.NullValF) < 1e-6) ;
            else {
                if(!desc) {
                    if (hh.get(i) >= gh)
                        res.add(new Float[]{vals.get(i), hh.get(i)});
                }else if (hh.get(i) <= gh)
                    res.add(new Float[]{vals.get(i), hh.get(i)});

            }
        }
        //查询和排序
        Collections.sort(res, new Comparator<Float[]>() {
            @Override
            public int compare(Float[] o1, Float[] o2) {
                //有问题的
                if (o1 == null || o2 == null) return -1;
                if (o1.length != 2 || o2.length != 2) return -1;
                if (o1[1] > o2[1]) return 1;
                else if (Math.abs(o1[1] - o2[1]) < 1e-6) return 0;
                else return -1;
            }
        });

        Float[] x = new Float[res.size()];
        Float[] y = new Float[res.size()];
        int i = 0;
        for (Float[] f : res) {
            x[i] = f[1];
            y[i] = f[0];
            i++;
        }
        List<Float[]> fres = new ArrayList<>();
        fres.add(y);
        fres.add(x);
        return fres;
    }

}
