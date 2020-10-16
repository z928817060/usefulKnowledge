package com.study.usefulknowledge.接口书写.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import net.xhms.data.gfs.common.*;
import net.xhms.data.gfs.dao.GFSData;
import net.xhms.data.gfs.parsegrib.Grib2dat;
import net.xhms.data.gfs.service.GFSDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//import net.xhms.cache.CacheDataFrame;
//import net.xhms.commons.constant.NetStatus;
//import net.xhms.commons.constant.ResStatus;

@RestController
public class GFSDataController {


    private static Logger logger = Logger.getLogger(GFSDataController.class);
    private static Grib2dat grib2dat = Grib2dat.getInstance();

    @Autowired
    private GFSData data;
    @Autowired
    NeedEles needEles;
    @Autowired
    private GFSDataService service;

    static {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Grib2dat.getInstance().parseGrib();
                Grib2dat.getInstance().parseDat();
            }
        }).start();
        GFSTimeManger manger =
                new GFSTimeManger(GFSTimeManger.TIME_PER_DAY,
                        new String[]{"992000", "992200", "990200", "990600", "990800", "990900",
                                "991000", "991200", "991400", "991600", "991700"},
                        grib2dat);
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject test() {
        JSONObject resJson = new JSONObject();
        resJson.put("name", "test");
        return resJson;
    }

    @RequestMapping(value = "/point", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPointHighData(@RequestBody String arraystr) {
        long stime = System.currentTimeMillis();
        JSONObject resJSON = new JSONObject();
        JSONArray array = null;
        try {
            array = JSONArray.parseArray(arraystr);
        } catch (Exception e) {
            logger.error("传入数据格式错误");
            resJSON.put("code", NetStatus.ParamError.getCode());
            return resJSON;
        }
        JSONArray paraJson = ParameterCheckUtils.checkGFSHighParamters(array);
        JSONArray resArr = new JSONArray();
        for (Object obj : paraJson) {
            JSONObject json = (JSONObject) obj;
            JSONObject idJSON = new JSONObject();
            if (json.get("code") != null) {
                logger.error("经参数检验发生未知错误");
                idJSON.put("code", NetStatus.ParamError.getCode());
                resArr.add(idJSON);
                continue;
            }
            if (!data.isValidRange(Float.valueOf((String) ((JSONObject) json).get("lat")), Float.valueOf((String) ((JSONObject) json).get("lng")))) {
                idJSON.put("code", NetStatus.ParamError.getCode());
                logger.error("经纬度信息不在数据有效范围内");
                resArr.add(json);
                continue;
            }
            try {
                JSONObject datJson = service.getPointData((JSONArray) (json).get("heights"), (json).get("time").toString(),
                        Float.valueOf((String) (json).get("lat")), Float.valueOf((String) (json).get("lng")), data,0);
                if (!datJson.isEmpty()) {
                    idJSON.put("data", datJson.get("data"));
                    idJSON.put("runtime", datJson.get("runtime"));
                    idJSON.put("code", NetStatus.Success.getCode());
                } else {
                    idJSON.put("code", NetStatus.NOResult.getCode());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                idJSON.put("code", NetStatus.ServerError.getCode());
            }
            idJSON.put("id", json.get("id"));
            resArr.add(idJSON);
        }
        resJSON.put("delay", System.currentTimeMillis() - stime);
        resJSON.put("data", resArr);
        if (resArr.isEmpty()) resJSON.put("code", NetStatus.NOResult.getCode());
        else
            resJSON.put("code", NetStatus.Success.getCode());
        return resJSON;
    }


    @RequestMapping(value = "/timeprofile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getTimeData(@RequestBody String arraystr) {
        long stime = System.currentTimeMillis();
        JSONObject resJSON = new JSONObject();
        JSONArray array = null;
        try {
            array = JSONArray.parseArray(arraystr);
        } catch (Exception e) {
            logger.error("传入数据不是JSONARRAY,发生错误!");
            resJSON.put("code", NetStatus.PostDataError.getCode());
            return resJSON;
        }
        JSONArray paraJson = ParameterCheckUtils.checkGFSHighParamters(array);
        JSONObject resDataJson = new JSONObject();
        for (Object obj : paraJson) {
            JSONObject json = (JSONObject) obj;
            JSONObject idJSON = new JSONObject();
            if (json.get("code") != null) {
                logger.error("经参数检验发生未知错误");
                idJSON.put("code", NetStatus.ParamError.getCode());
//                resArr.add(idJSON);
                continue;
            }
            if (!data.isValidRange(Float.valueOf((String) json.get("lat")), Float.valueOf((String) json.get("lng")))) {
                idJSON.put("code", NetStatus.ParamError.getCode());
                logger.error("经纬度信息不在数据有效范围内");
//                resArr.add(json);
                resDataJson.put(json.get("id").toString(), idJSON.get("data"));
                continue;
            }
            try {
                JSONObject datJson = service.getTimeData((JSONArray) json.get("heights"), json.get("time").toString(),
                        Float.valueOf((String) json.get("lat")), Float.valueOf((String) json.get("lng")),
                        json.get("duration").toString(), data);
                if (!datJson.isEmpty()) {
                    idJSON.put("data", datJson.get("data"));
//                    idJSON.put("times", datJson.get("times"));
                    idJSON.put("code", NetStatus.Success.getCode());
                    idJSON.put("id", json.get("id"));
                } else {
                    idJSON.put("code", NetStatus.NOResult.getCode());
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                idJSON.put("code", NetStatus.ServerError.getCode());
            }
//            resArr.add(idJSON);
            resDataJson.put(idJSON.get("id").toString(), idJSON.get("data"));
        }
        resJSON.put("delay", System.currentTimeMillis() - stime);
        resJSON.put("data", resDataJson);
        if (resDataJson.isEmpty()) resJSON.put("code", NetStatus.NOResult.getCode());
        else
            resJSON.put("code", NetStatus.Success.getCode());
        return resJSON;
    }

    @RequestMapping(value = "/GFSCache", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String GFSCache(@RequestBody JSONObject json) {
        long tt = System.currentTimeMillis();
        JSONObject resJSON = new JSONObject();
        /**
         * 检测参数
         */
        String type = json.getString("type");
        String time = json.getString("time");
        String isobaric = json.getString("isobaric");
        if(type == null ){
            logger.error("未输入数据类型,或输入数据信息错误");
            resJSON.put("code", ResStatus.PARAM_ERROR.getStatusCode());
            return resJSON.toJSONString();
        }else {
            boolean flag = false;
            for (String ele: needEles.getEles()) {
                if (ele.equals(type)){ flag = true; break;}
            }
            if (!flag){
                logger.error("未输入数据类型,或输入数据信息错误");
                resJSON.put("code", ResStatus.PARAM_ERROR.getStatusCode());
                return resJSON.toJSONString();
            }
        }
        if(time == null || !time.matches("\\d{4}+\\d{1,2}+\\d{1,2}+\\d{1,2}+\\d{1,3}")){
            logger.error("未输入时间信息,或输入时间信息错误");
            resJSON.put("code", ResStatus.PARAM_ERROR.getStatusCode());
            return resJSON.toJSONString();
        }
        if (isobaric == null ){
            logger.error("未输入数据类型,或输入数据信息错误");
            resJSON.put("code", ResStatus.PARAM_ERROR.getStatusCode());
            return resJSON.toJSONString();
        }else {
            boolean flag = false;
            for (String isobaric_simple: PropertyUtil.getProperty("isobaric").replace("\"" , "").split(",")) {
                if (isobaric_simple.equals(isobaric) || isobaric.equals("9999")){ flag = true; break;}
            }
            if (!flag){
                logger.error("未输入数据类型,或输入数据信息错误");
                resJSON.put("code", ResStatus.PARAM_ERROR.getStatusCode());
                return resJSON.toJSONString();
            }
        }
        /**
         * 数据部分
         */
        String key = time + "_" + isobaric + "_" + type; //String key = "2018082712001"+"_"+"0750"+"_"+"HGT";
        GFSMem gfsMem = (GFSMem) CacheDataFrame.getInstance().pullData(key);
        resJSON.put("data", gfsMem);
        resJSON.put("code", 0);
        resJSON.put("delay" , System.currentTimeMillis()-tt);
        Gson gson = new Gson();
        return gson.toJson(resJSON);
    }
}
