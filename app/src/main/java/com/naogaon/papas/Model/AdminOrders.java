package com.naogaon.papas.Model;

public class AdminOrders {
    private String name,phone,address,city,area,state,date,time,totalAmount,image,pinfo,order_number,pid, payment_mode;

    public AdminOrders() {
    }

    public AdminOrders(String name, String phone, String address, String city,String area, String state, String date, String time, String totalAmount,String image, String pinfo,String order_number,String pid,String payment_mode) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.area = area;
        this.state = state;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.image= image;
        this.pinfo=pinfo;
        this.order_number=order_number;
        this.pid=pid;
        this.payment_mode=payment_mode;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPinfo() { return pinfo; }

    public void setPinfo(String pinfo) { this.pinfo = pinfo; }

    public String getOrder_number() { return order_number; }

    public void setOrder_number(String order_number) { this.order_number = order_number; }

    public String getPid() { return pid; }

    public void setPid(String pid) { this.pid = pid; }

    public String getPayment_mode() { return payment_mode; }

    public void setPayment_mode(String payment_mode) { this.payment_mode=payment_mode; }
}
