package com.example.asus.gaozhijie20171026.mode.bean;

/**
 * 创建时间  2017/10/26 15:36
 * 创建人
 * 类描述   购物车的Bean
 */
public class ShoppingBean {
    private String id;
    private String name;
    private String type;
    private String price;
    private String count;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getCount() {
        return count;
    }
}
