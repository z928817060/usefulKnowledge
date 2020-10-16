package com.study.usefulknowledge.远程连接通信json_mysql_ftp_redis.url的json和对象;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
/**
 *
 * 功能: url传\接对象  (手段: 使用 Gson 装箱和拆箱  ,Gson是将数据封装成对象的工具类)
 * @Autor rongxiaokun
 * @Date 2018/8/28  10:12
 */
public class url_对象 {
    private static final Logger LOGGER = LoggerFactory.getLogger(url_对象.class);


    /**
     * 传对象    给出对象接口 ,get和post都可以
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object test() {
        net.xhms.data.gfscache.service.Person person = new net.xhms.data.gfscache.service.Person("lala", 11);
        Gson gson = new Gson();
        return gson.toJson(person);
    }

    @RequestMapping(value = "/test1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String parseLemma(@RequestBody String para) {
        try {

            net.xhms.data.gfscache.service.Person person = new net.xhms.data.gfscache.service.Person(para ,22);

            Gson gson = new Gson();
            return gson.toJson(person);
        } catch (Exception e) {
            logger.error("parse baidu lemma fail:" + e);
            return "";
        }
    }

    /**
     * 接对象    使用相应get\post接收方法接收
     */
    public static void main(String[] args) {
        String result = JSONUtil.doHttpPost("lalaaa", "http://localhost:7000/test1");
        Gson gson = new Gson();
        Person person = gson.fromJson(result, Person.class);
        System.out.println(person.name);
    }

}
