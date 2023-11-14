package com.example.techophile1;

public class PostMod {
    String title , tags , descr , UserId , UserName , UserImg , likes , postID;
    public PostMod() {
    }

    public PostMod(String title, String tags, String descr, String userId, String userName, String userImg, String likes, String postID) {
        this.title = title;
        this.tags = tags;
        this.descr = descr;
        UserId = userId;
        UserName = userName;
        UserImg = userImg;
        this.likes = likes;
        this.postID = postID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImg() {
        return UserImg;
    }

    public void setUserImg(String userImg) {
        UserImg = userImg;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
