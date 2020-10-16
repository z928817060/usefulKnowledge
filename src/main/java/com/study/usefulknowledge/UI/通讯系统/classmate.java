package com.study.usefulknowledge.UI.通讯系统;

public class classmate {
    String name;
    String sex;
    String homephone;
    String address;
    String company;
    String duty;
    String salary;
    String contact;
    String homeaddress;
    String city;
    String interesting;  //这个demo里没有
    classmate(String name){
        this.name=name;
    }
    public String getname(){
        return name;
    }
    public void setcity(String city){
        this.city=city;
    }
    public void sethomeaddress(String homeaddress){
        this.homeaddress=homeaddress;
    }
    public void setcontact(String contact){
        this.contact=contact;
    }
    public void setinteresting(String interesting){
        this.interesting=interesting;
    }
    public void setsexy(String sex){
        this.sex=sex;
    }
    public void sethomephone(String homephone){
        this.homephone=homephone;
    }
    public void setaddtess(String address){
        this.address=address;
    }
    public void setcompany(String company){
        this.company=company;
    }
    public void setsalary(String salary){
        this.salary=salary;
    }
    public void setduty(String duty){
        this.duty=duty;
    }

    //get
    public String getcity(){
        return city;
    }
    public String gethomeaddress(){
        return homeaddress;
    }
    public String getcontact(){
        return contact;
    }
    public String getinteresting(){
        return interesting;
    }
    public String getsex(){
        return sex;
    }
    public String gethomephone(){
        return homephone;
    }
    public String getaddtess(){
        return address;
    }
    public String getcompany(){
        return company;
    }
    public String getsalary(){
        return salary;
    }
    public String getduty(){
        return duty;
    }
    public String toString(){
        String[] strings={"同学姓名：","性别：","所在地址：","家庭地址：",
                "所在城市：","所在公司","职责：","薪水：","联系方式","家庭电话","兴趣爱好"};
        String information=strings[0]+name+strings[1]+sex+strings[2]+address+strings[3]+homeaddress
                +strings[4]+city+strings[5]+company+strings[6]+duty+strings[7]+salary+strings[8]
                +contact+strings[9]+homephone+strings[10]+interesting;//没有写完
        return information;
    }
}
