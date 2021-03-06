package com.study.usefulknowledge.远程连接通信json_mysql_ftp_redis.url的json和对象;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoyu on 16/4/19.
 * JSONobject
 * JSONArray   相当于封装了list
 */
public final class JSONUtil {
    private static final Log logger = LogFactory.getLog(JSONUtil.class);
    private JSONUtil(){}
    private static JSONArray jsonArray = null;
    private static List<String> stationCode = null;
    public static void writeJSONToResponse(HttpServletResponse response, JSONObject resJSON){
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            logger.debug(resJSON.toString());
            out.write(resJSON.toString());
            out.flush();
            System.out.println("hello");
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
        JSONObject json = null;
        BufferedReader rd = null;
        String jsonText = null;
        try {
            is = new URL(url).openStream();
            rd= new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            jsonText = readAll(rd);
            try {
                json = JSONObject.parseObject(jsonText);
            }catch (Exception e ){
                logger.warn("链接非JSON");
            }
//            return json;
        } catch (IOException e) {
            logger.warn("打开链接失败： "+url);
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

    /**
     * 目的:为了获取站号,简化json
     * @return
     */
    public static List<String> getStationCode(){
        if (stationCode==null || stationCode.isEmpty()){
            stationCode = new ArrayList<>();
        }
        String path = JSONUtil.class.getClassLoader().getResource("").getPath() + "station_china.json";   //给出路径
        jsonArray = readJsonFromFile(path);
        JSONObject json = null;                                                                           //遍历array内容
        for (int i = 0; i < jsonArray.size(); i++) {
            json = (JSONObject) jsonArray.get(i);
            stationCode.add(json.get("station_code").toString());
        }
        return stationCode;
    }


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }


}
