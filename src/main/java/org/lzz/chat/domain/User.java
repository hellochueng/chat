package org.lzz.chat.domain;

import java.io.Serializable;

public class User implements Serializable{
    private Long id;
    private String username;
    private String pwd;
    private String name;
    private String email;
    private String sex;

    public User(Long id, String username, String pwd, String name, String email, String sex) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.name = name;
        this.email = email;
        this.sex = sex;
    }

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
