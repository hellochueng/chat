package org.lzz.chat.domain;

import java.util.Date;

public class Order {

    private Long id;
    private String serianumber;
    private Date createtime;
    private Double price;

    public Order() {
    }

    public Order(Long id, String serianumber, Date createtime, Double price) {
        this.id = id;
        this.serianumber = serianumber;
        this.createtime = createtime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerianumber() {
        return serianumber;
    }

    public void setSerianumber(String serianumber) {
        this.serianumber = serianumber;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
