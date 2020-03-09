package org.lzz.chat.elasticsearch.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "user",type = "userinfo")
public class User {
    private Long id;
    private String username;
    private String pwd;
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String user_name;
    private String email;
    @Field(type = FieldType.Text,fielddata = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String sex;

    public User() {
    }

    public User(Long id, String username, String pwd, String user_name, String email, String sex) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.user_name = user_name;
        this.email = email;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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
}
