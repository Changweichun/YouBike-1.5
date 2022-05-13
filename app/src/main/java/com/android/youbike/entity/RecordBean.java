package com.android.youbike.entity;

import java.io.Serializable;

public class RecordBean implements Serializable {
    private String title;
    private String type;
    private String content;
    private String time;
    private String address;
    private String phone;

    public RecordBean(String title, String type, String content, String time, String address, String phone) {
        this.title = title;
        this.type = type;
        this.content = content;
        this.time = time;
        this.address = address;
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
