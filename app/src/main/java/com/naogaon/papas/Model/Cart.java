package com.naogaon.papas.Model;



public class Cart {
    private String pid,pname,price,quantity,discount,productid,pinfo;

    public Cart() {
    }

    public Cart(String pid, String pname, String price, String quantity, String discount,String productid,String pinfo) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.productid=productid;
        this.pinfo=pinfo;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getPinfo() { return pinfo; }

    public void setPinfo(String pinfo) { this.pinfo = pinfo; }
}
