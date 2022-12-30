package com.naogaon.papas.Model;

public class Users {
    private String name, phone, password, image, address,fpn;
    public Users()
    {

    }

    public Users(String name, String phone, String password, String image, String address, String fpn) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.address = address;
        this.fpn=fpn;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFpn() {
        return fpn;
    }

    public void setFpn(String fpn) {
        this.fpn = fpn;
    }
}
