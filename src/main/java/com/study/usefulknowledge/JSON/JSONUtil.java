package com.study.usefulknowledge.JSON;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * Created by xiaoyu on 16/4/19.
 */
public final class JSONUtil {
    private static final Log logger = LogFactory.getLog(JSONUtil.class);
    private JSONUtil(){}
    private static JSONArray jsonArray = null;
    private static JSONObject jsonObject = null;

    public static void writeJSONToResponse(HttpServletResponse response, JSONObject resJSON){
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            logger.debug(resJSON.toString());
            out.write(resJSON.toString());
            out.flush();
//            System.out.println("hello");
        } catch (IOException e) {
            logger.error( e);
        }
    }

    public static void writeJSONToResponseDomain(HttpServletResponse response, JSONObject resJSON){
        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            PrintWriter out = response.getWriter();
            logger.debug(resJSON.toString());
            out.write(resJSON.toString());
            out.flush();
        } catch (IOException e) {
            logger.error( e);
        }
    }


    /**
     * 功能：给出url，返回json
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readJsonFromUrl(String url)  {
        InputStream is = null;
        JSONObject json = new JSONObject();
        BufferedReader rd = null;
        String jsonText = null;
//        if (url.endsWith("png")){
//            json.put("data",1);
//            return json;
//        }
//        if (url.equals("https://weather1.xinhong.net/gfs/areadata?elem=WS&level=0300")){
//            System.out.println();
//        }
        try {
            URL url1 = new URL(url);
            HttpURLConnection htpcon = (HttpURLConnection) url1.openConnection();
            htpcon.setRequestMethod("GET");
            htpcon.setDoOutput(true);
            htpcon.setDoInput(true);
            htpcon.setUseCaches(false);
            htpcon.setConnectTimeout(1000);     //连接这个动作的时间 ms
            htpcon.setReadTimeout(1000);        //已经连接上,之后读取数据的时间 ms
            InputStream in = htpcon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,Charset.forName("UTF-8")));
            jsonText = readAll(bufferedReader);
            try {
                json = JSONObject.parseObject(jsonText);
            }catch (Exception e ){
                logger.warn("链接非JSON");
                json.put("data",1);                 //对于非json的图片,利用catch处理
                return json;
            }
//            return json;
        } catch (IOException e) {
//            logger.warn("打开链接失败： "+url);
            return json;
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (rd!=null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (jsonText!=null && json==null) {
            json = new JSONObject();
            JSONObject json_temp = new JSONObject();
            json_temp.put("type" , "非json类型");
            json.put("data", json_temp);
        }
        return json;
    }

    /**
     * 功能：给出url，返回jsonArray
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static JSONArray readJsonArrayFromUrl(String url)  {
        InputStream is = null;
        JSONArray json = null;
        BufferedReader rd = null;
        String jsonText = null;
        try {
            URL url1 = new URL(url);
            HttpURLConnection htpcon = (HttpURLConnection) url1.openConnection();
            htpcon.setRequestMethod("GET");
            htpcon.setDoOutput(true);
            htpcon.setDoInput(true);
            htpcon.setUseCaches(false);
            htpcon.setConnectTimeout(1000);     //连接这个动作的时间 ms
            htpcon.setReadTimeout(1000);        //已经连接上,之后读取数据的时间 ms
            InputStream in = htpcon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,Charset.forName("UTF-8")));
            jsonText = readAll(bufferedReader);
            try {
                json = JSONArray.parseArray(jsonText);
            }catch (Exception e ){
                logger.warn("链接非JSON");
                return json;
            }
//            return json;
        } catch (IOException e) {
            logger.warn("打开链接失败： "+url);
            return json;
        }finally {
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (rd!=null) {
                try {
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (jsonText!=null && json==null) {
            json = new JSONArray();
//            JSONObject json_temp = new JSONObject();
//            json_temp.put("type" , "非json类型");
//            json.add("data", json_temp);
        }
        return json;
    }
    /**
     * 发送Http post请求
     * @param xmlInfo   json转化成的字符串
     * @param url       请求url  为带有http://协议的
     * @return 返回信息
     */
    public static String doHttpPost(String xmlInfo, String url) {
//        System.out.println("发起的数据:" + xmlInfo);
        byte[] xmlData = xmlInfo.getBytes();
        InputStream instr = null;
//        java.io.ByteArrayOutputStream out = null;
        try {
            URL urls = new URL(url);
            URLConnection urlCon = urls.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("content-Type", "application/json");
//            urlCon.setRequestProperty("content-Type", "text/html");
            urlCon.setRequestProperty("charset", "utf-8");
            urlCon.setRequestProperty("Content-length",
                    String.valueOf(xmlData.length));
            urlCon.setConnectTimeout(1000);     //连接这个动作的时间 ms
            urlCon.setReadTimeout(1000);        //已经连接上,之后读取数据的时间 ms
//            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(
                    urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println("返回空");
            }
//            System.out.println("返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        } finally {
            try {
//                out.close();
                instr.close();
            } catch (Exception ex) {
//                return null;
            }
        }
    }

    /**
     * 功能:读取json文件
     * @param path
     * @return
     */
    public static JSONArray readJsonFromFile(String path){

        try {
//            String path = JSONUtil.class.getClassLoader().getResource("").getPath() + "station_china.json";   //给出路径
            String input = FileUtils.readFileToString(new File(path), "UTF-8");
            jsonArray = JSONArray.parseArray(input);
//            JSONObject json = null;                                                                           //遍历array内容
//            for (int i = 0; i < jsonArray.size(); i++) {
//                json = (JSONObject) jsonArray.get(i);
//                System.out.println(json.get("station_code"));
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }




    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * 多个jsonArray取并集
     * 根据JSONArray中数据格式:[{}{}{}] join [{}{}{}]
     * @param arrays
     * @return
     */
    public static JSONArray joinJSONArray(JSONArray... arrays){
        JSONArray ret = new JSONArray();                      //目标
        JSONObject ret_jsonObject = new JSONObject();
        for (JSONArray array:arrays) {                          //有一个为空,就返回
            if (array == null || array.isEmpty() ){
                return ret;
            }
        }
        for (JSONArray array:arrays) {                          //有一个为空,就返回
            for (int i = 0; i < array.size(); i++) {
                JSONObject temp_json = array.getJSONObject(i);
                for (String key:temp_json.keySet()) {
                    ret_jsonObject.put(key , temp_json.getString(key));
                }
            }
        }
        for (String key:ret_jsonObject.keySet()) {              //转成jsonArray
            JSONObject temp = new JSONObject();
            temp.put(key , ret_jsonObject.getString(key));
            ret.add(temp);
        }
        return ret;
    }

    /**
     * 功能:合并多个jsonobject
     * @param objects
     * @return
     */
    public static JSONObject joinJSONObject(JSONObject... objects){
        JSONObject json_res = new JSONObject();
        for (JSONObject object:objects) {                          //有一个为空,就返回
            if (object == null || object.isEmpty())continue;
            for (String key: object.keySet()) {
                json_res.put(key , object.get(key));
            }
        }
        return json_res;
    }

    /**
     * 把json格式的字符串写到文件
     * @param filePath
     * @param sets      json.toJsonString()
     * @return
     */
    public static boolean writeJson2File(String filePath, String sets) {
        FileWriter fw;
        try {
            fw = new FileWriter(filePath);
            PrintWriter out = new PrintWriter(fw);
            out.write(sets);
            out.println();
            fw.close();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
