package com.study.usefulknowledge.UI.通讯系统;

public class friend {
    String name;
    String sex;
    String birthday;
    String address;
    String company;
    String duty;
    String salary;
    String tel;
    String phone;
    String xx;

    //    String department;
//    String interesting;  //这个demo里没有
    friend(String name){
        this.name=name;
    }
    public String getname(){
        return name;
    }
//    public void getdepartment(String department){
//        this.department=department;
//    }
    public void setphone(String phone){
        this.phone=phone;
    }
    public void settel(String tel){
        this.tel=tel;
    }
//    public void setinteresting(String interesting){
//        this.interesting=interesting;
//    }
    public void setsexy(String sex){
        this.sex=sex;
    }
    public void setbirthday(String birthday){
        this.birthday=birthday;
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
    public String getphone(){
        return phone;
    }
    public String gettel( ){
        return tel;
    }
    public String getsex( ){
        return sex;
    }
    public String getbirthday( ){
        return birthday;
    }
    public String getaddtess( ){
        return address;
    }
    public String getcompany( ){
        return company;
    }
    public String getsalary( ){
        return salary;
    }
    public String getduty( ){
        return duty;
    }
    public String toString(){
        String[] strings={"同学姓名：","性别：","所在地址：","家庭地址：",
                "所在城市：","所在公司","职责：","薪水：","联系方式","家庭电话","兴趣爱好"};
        String information=strings[0]+name+strings[1]+sex+strings[2]+address+strings[3]+phone
                +strings[4]+birthday+strings[5]+company+strings[6]+duty+strings[7]+salary+strings[8]
                +tel;//没有写完
        return information;
    }
}
