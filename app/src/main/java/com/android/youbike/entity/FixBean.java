package com.android.youbike.entity;

import java.io.Serializable;

public class FixBean implements Serializable {
    private String title;
    private String time;
    private String type;
    private String area;
    private String number;
    private String number2;
    private String project;
    private String remark;

    public FixBean(String title, String time, String type, String area, String number, String number2, String project, String remark) {
        this.title = title;
        this.time = time;
        this.type = type;
        this.area = area;
        this.number = number;
        this.number2 = number2;
        this.project = project;
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String title) {
        this.title = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNumber2() {
        return number2;
    }

    public void setNumber2(String number2) {
        this.number2 = number2;
    }
}
