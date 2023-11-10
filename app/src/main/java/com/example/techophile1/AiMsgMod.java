package com.example.techophile1;

public class AiMsgMod {
    public static String AI = "AI";
    public static String USER = "USER";
    String msg , sentBy ;

    public AiMsgMod(String msg, String sentBy) {
        this.msg = msg;
        this.sentBy = sentBy;
    }

    public AiMsgMod() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }
}
