package com.example.techophile1;

public class Comment_Mod {
    String comment , userName , userId;

    public Comment_Mod() {
    }

    public Comment_Mod(String comment, String userName, String userId) {
        this.comment = comment;
        this.userName = userName;
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
