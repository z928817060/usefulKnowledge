package com.study.usefulknowledge.���ü��㹫ʽ����;

import com.lwx.���ü��㹫ʽ����.MathTools;
import com.lwx.���ü��㹫ʽ����.��ֵ.ConstantVar;

/**
 * Created by wingsby on 2018/4/11.
 * ���մ�ֱ����(v)Ϊ0��
 */
public class MeteologicalCalculateUtils {

    /**
     * λ�Ƹ߶�2���θ߶�
     * @param lat
     * @param lon
     * @param gh
     * @return
     * λ�Ƹ߶Ⱦ������������ĳ�������λ�Ƴ����ȵ�λ��������ʾ�ĸõ�߶ȡ���λ���ױ�ʾ��λ�Ƹ߶ȵ����ã����Ρ��ױ�ʾ�ļ��θ߶ȵ�g/9.8��������gΪ�����������ٶ�
     *
     */
    public static double hh2h(float lat, float lon, float gh){
//        double ch = gh / MeteologicalCalculateUtils.LocalGravity(lat, lon, (float) (gh * 10 / 9.8)) * 10;
//        return ch;
        double g = MeteologicalCalculateUtils.LocalGravity(lat, lon, (float) (gh * 10 / 9.8));
        double ch = gh * 9.8 / calcG(lat , lon);
        return ch;
    }
    /**
     * ��������
     * @param lat
     * @param lon
     * @param h
     * @return
     */
    public static double LocalGravity(float lat, float lon, float h) {
        //�����й������ñ�
        double g0 = 9.80665 * (1 - 0.00265 * Math.cos(2 * lat * 180 / 3.1415926));   //���������������ٶȽ��Ƽ��㹫ʽ
        double gh = g0 * (1 - 1.96 * 1e-7 * h);
        return gh;
    }
    //����g
    public static double calcG(float lat, float lon) {
        double g0 = 9.80665 * (1 - 0.00265 * Math.cos(2 * lat * 180 / 3.1415926));   //���������������ٶȽ��Ƽ��㹫ʽ
        return g0;
    }

    public static double[] UV2WSWD(double u, double v) {
        if(Math.abs(u-ConstantVar.NullValF)<1e-6||Math.abs(v-ConstantVar.NullValF)<1e-6)return null;
        double ws = Math.sqrt(u * u + v * v);
        double wd = ConstantVar.NullValF;
        if (MathTools.isDoubleEqual(u, 0.)) {
            if (MathTools.isDoubleEqual(v, 0.)) wd = 0.;
            else {
                if (v > 0) wd = 360.;
                if (v < 0) wd = 180.;
            }
        } else {
            if (u > 0)
                wd = 270. - Math.atan(v / u) * 180. / Math.PI;
            if (u < 0)
                wd = 90. - Math.atan(v / u) * 180. / Math.PI;
        }
        return new double[]{ws, wd};
    }

    public static double[] WSWD2UV(double ws, double wd) {
        if(Math.abs(ws-ConstantVar.NullValF)<1e-6||Math.abs(wd-ConstantVar.NullValF)<1e-6)return null;
        double seta = 0.;
        if (MathTools.isDoubleEqual(wd, 0.)) return new double[]{0., 0.};
        if ((wd < 180 && wd > 0) || MathTools.isDoubleEqual(wd, 360.))
            seta = wd + 180.;
        else if ((wd < 360 && wd >= 180))
            seta = wd - 180.;
        double u = ws * Math.sin(seta * Math.PI / 180.);
        double v = ws * Math.cos(seta * Math.PI / 180.);
        return new double[]{u, v};
    }

    /**
     * ���ܣ��ַ����缶-->����
     * e.g. 3-4��ת<3��   �����ҳ���ߵķ���
     */
    public static float WSL2WS(String WSL){
        if (WSL == null|| WSL.isEmpty()){return -1;}
        String[] WSLs = WSL.split("ת");
        float maxWSL = 0;
        float maxWS = 0;
        for (String wsl:WSLs) {
            String[] temps = (wsl.replace("<","").replace(">","")
                    .replace("��","")).split("-");
            float temp = Float.parseFloat(temps[temps.length-1]);
            if (temp>maxWSL){maxWSL = temp;}
        }
        maxWS = WSL2WDPro(maxWSL);
        return maxWS;
    }

    private static float WSL2WDPro(float WSL){
        if (WSL == 0) {return 0.1f;}
        if (WSL == 1) {return 0.9f;}
        if (WSL == 2) {return 2.45f;}
        if (WSL == 3) {return 4.4f;}
        if (WSL == 4) {return 6.7f;}
        if (WSL == 5) {return 9.3f;}
        if (WSL == 6) {return 12.3f;}
        if (WSL == 7) {return 15.5f;}
        if (WSL == 8) {return 18.9f;}
        if (WSL == 9) {return 22.6f;}
        if (WSL == 10) {return 26.5f;}
        if (WSL == 11) {return 30.5f;}
        return 61.3f;
    }

    /**
     * 32����������  ת ����
     */
    public static String WW2Num(String WW){
        if (WW == null|| WW.isEmpty()){return "-1";}
        if (WW.equals("��")){return "00";}
        if (WW.equals("����")){return "01";}
        if (WW.equals("��")){return "02";}
        if (WW.equals("����")){return "03";}
        if (WW.equals("������")){return "04";}
        if (WW.equals("��������б���")){return "05";}
        if (WW.equals("���ѩ")){return "06";}
        if (WW.equals("С��")){return "07";}
        if (WW.equals("����")){return "08";}
        if (WW.equals("����")){return "09";}
        if (WW.equals("����")){return "10";}
        if (WW.equals("����")){return "11";}
        if (WW.equals("�ش���")){return "12";}
        if (WW.equals("��ѩ")){return "13";}
        if (WW.equals("Сѩ")){return "14";}
        if (WW.equals("��ѩ")){return "15";}
        if (WW.equals("��ѩ")){return "16";}
        if (WW.equals("��ѩ")){return "17";}
        if (WW.equals("��")){return "18";}
        if (WW.equals("����")){return "19";}
        if (WW.equals("ɳ����")){return "20";}
        if (WW.equals("С��-����")){return "21";}
        if (WW.equals("����-����")){return "22";}
        if (WW.equals("����-����")){return "23";}
        if (WW.equals("����-����")){return "24";}
        if (WW.equals("����-�ش���")){return "25";}
        if (WW.equals("Сѩ-��ѩ")){return "26";}
        if (WW.equals("��ѩ-��ѩ")){return "27";}
        if (WW.equals("��ѩ-��ѩ")){return "28";}
        if (WW.equals("����")){return "29";}
        if (WW.equals("��ɳ")){return "30";}
        if (WW.equals("ǿɳ����")){return "31";}
        if (WW.equals("��")){return "32";}
        return "-1";
    }


    public static void main(String[] args) {
//        double[] wswd=UV2WSWD(0,0);
//        double[] uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//        wswd=UV2WSWD(-2,-5);
//        uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(wswd[0]+" "+wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//        System.out.println("=======================");
//        wswd=UV2WSWD(2,-5);
//        uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(wswd[0]+" "+wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//        System.out.println("=======================");
//        wswd=UV2WSWD(-2,2);
//        uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(wswd[0]+" "+wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//        System.out.println("=======================");
//
//        wswd=UV2WSWD(-2,0);
//        uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(wswd[0]+" "+wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//        System.out.println("=======================");
//
//        wswd=UV2WSWD(2,0);
//        uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(wswd[0]+" "+wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//        System.out.println("=======================");
//
//        wswd=UV2WSWD(0,2);
//        uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//
//        wswd=UV2WSWD(0,-2);
//        uv=WSWD2UV(wswd[0],wswd[1]);
//        System.out.println(wswd[0]+" "+wswd[1]);
//        System.out.println(uv[0]+" "+uv[1]);
//        System.out.println("=======================");

//        Date date=new Date(2018,03,15,12,0);
//        SimpleDateFormat df = new SimpleDateFormat("YY-MM-dd-HH");
//        String str=df.format(date);
//
//        DateTimeFormatter formatter= DateTimeFormat.forPattern("YY-MM-dd-HH");
//        DateTime dateTime=new DateTime(2018,04,15,12,0);
//        System.out.println(dateTime.toString("YY-MM-dd-HH"));
//        String path="12@YYMMDD@SDF";
//        String replace="@YYMMDD@";
//        path = path.replace(replace, dateTime.toString("YY-MM-dd-HH"));
//        System.out.println(path);
//        System.out.println("zsadfasdfaf000".matches(".+?3"));


//        System.out.println("".matches("(\\d){4}"));
        System.out.println("12".matches("(0[1-9])|(1[0-2])"));
        String[] tmp = "sfsaf".split("@");
        System.out.println(tmp);

    }

}
