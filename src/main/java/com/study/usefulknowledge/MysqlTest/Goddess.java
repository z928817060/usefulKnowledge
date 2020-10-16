package com.study.usefulknowledge.MysqlTest;

import java.io.Serializable;

/**
 * 实体类：女神类
 *
 * @author AlanLee
 *
 */
public class Goddess implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * 唯一主键
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobie;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 家庭住址
     */
    private String address;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMobie()
    {
        return mobie;
    }

    public void setMobie(String mobie)
    {
        this.mobie = mobie;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
