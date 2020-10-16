package com.study.usefulknowledge.UI.通讯系统;

public class company {
    String name;
    String sex;
    String birthday;
    String address;
    String code;
    String duty;
    String salary;
    String tel;
    String motel;
    String department;
    String interesting;  //这个demo里没有
    company(String name){
        this.name=name;
    }
    public String getname(){
        return name;
    }
    public void setdepartment(String department){
        this.department=department;
    }
    public void setmotel(String motel){
        this.motel=motel;
    }
    public void settel(String tel){
        this.tel=tel;
    }
    public void setinteresting(String interesting){
        this.interesting=interesting;
    }
    public void setsex(String sex){
        this.sex=sex;
    }
    public void setbirthday(String birthday){
        this.birthday=birthday;
    }
    public void setaddtess(String address){
        this.address=address;
    }
    public void setcode(String code){
        this.code=code;
    }
    public void setsalary(String salary){
        this.salary=salary;
    }
    public void setduty(String duty){
        this.duty=duty;
    }
    //
    public String getdepartment(){
        return department;
    }
    public String getmotel(){
        return motel;
    }
    public String gettel(){
        return tel;
    }
    public String getinteresting( ){
        return interesting;
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
    public String getcode( ){
        return code;
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
        String information=strings[0]+name+strings[1]+sex+strings[2]+address+strings[3]+motel
                +strings[4]+department+strings[5]+code+strings[6]+duty+strings[7]+salary+strings[8]
                +tel+strings[9]+birthday;//没有写完
        return information;
    }
}
