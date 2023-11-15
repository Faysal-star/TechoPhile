package com.example.techophile1;

public class NewsMod {
    String title,desc,url,urlImg,date ;

    public NewsMod(String title, String desc, String url, String urlImg, String date) {
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.urlImg = urlImg;
        this.date = date;
    }

    public NewsMod() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
