package com.demo;

import com.alibaba.excel.annotation.ExcelProperty;

public class Student {
    public Student(String id, String name, String idCard, int age, String gender) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ExcelProperty(value = "id", index = 0)
    private String id;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
    @ExcelProperty(value = "身份证号", index = 2)
    private String idCard;
    @ExcelProperty(value = "年龄", index = 3)
    private int age;
    @ExcelProperty(value = "性别", index = 4)
    private String gender;
}
