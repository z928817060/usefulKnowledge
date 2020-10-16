package com.study.usefulknowledge.Զ������ͨ��json_mysql_ftp_redis.url��json�Ͷ���;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
/**
 *
 * ����: url��\�Ӷ���  (�ֶ�: ʹ�� Gson װ��Ͳ���  ,Gson�ǽ����ݷ�װ�ɶ���Ĺ�����)
 * @Autor rongxiaokun
 * @Date 2018/8/28  10:12
 */
public class url_���� {
    private static final Logger LOGGER = LoggerFactory.getLogger(url_����.class);


    /**
     * ������    ��������ӿ� ,get��post������
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
     * �Ӷ���    ʹ����Ӧget\post���շ�������
     */
    public static void main(String[] args) {
        String result = JSONUtil.doHttpPost("lalaaa", "http://localhost:7000/test1");
        Gson gson = new Gson();
        Person person = gson.fromJson(result, Person.class);
        System.out.println(person.name);
    }

}
