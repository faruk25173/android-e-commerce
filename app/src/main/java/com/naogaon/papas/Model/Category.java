package com.naogaon.papas.Model;



public class Category {
    private String catname, image,url;
    public Category()
    {

    }
    public Category(String catname,  String image, String url) {
        this.catname = catname;
        this.image = image;
        this.url=url;


    }



    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }





}
