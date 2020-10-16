package com.study.usefulknowledge.接口书写.common;


/**
 * 新宏气象服务接口专用状态码,参考标准码使用
 */
public enum NetStatus {
    Success("查询成功", 0),
    NOResult("正常处理无结果", 1),
    DataCRUDError("数据操作异常", 300),
    ParamError("传入参数错误", 301),
    PostDataError("POST数据传入错误", 302),
    ParamOverRange("传入参数超出范围", 303),
    ServerError("服务出现异常", 500),
    ServerSearchError("服务器查询出现异常",501),
    NORightAccess("无权限查询或TOKEN时效已过", 400),
    SignInavailable("签名无效或邀请码无效", 401),
    UserNotExist("用户不存在", 402),
    PasswdWrong("用户名或密码错误", 403);


    private String cname;
    private int code;

    NetStatus(String cname, int code) {
        this.cname = cname;
        this.code = code;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
