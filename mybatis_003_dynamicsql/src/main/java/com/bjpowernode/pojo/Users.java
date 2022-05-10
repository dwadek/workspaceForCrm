package com.bjpowernode.pojo;

import java.util.Date;

/**
 *
 */
public class Users {
    private Integer id;
    private String username;
    private Date birth;
    private String sex;
    private String address;

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", birthday=" + birth +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Users() {
    }

    public Users(Integer id, String username, Date birth, String sex, String address) {
        this.id = id;
        this.username = username;
        this.birth = birth;
        this.sex = sex;
        this.address = address;
    }

    public Users(String username, Date birth, String sex, String address) {
        this.username = username;
        this.birth = birth;
        this.sex = sex;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
