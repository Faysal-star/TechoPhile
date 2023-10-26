package com.example.techophile1;

public class User {
    String profilePic , userName , mail , dob , pass , role , userID , lastMsg , status , learning , teaching ;

    public User(String profilePic, String userName, String mail, String dob, String pass, String role, String userID, String lastMsg, String status, String learning, String teaching) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.dob = dob;
        this.pass = pass;
        this.role = role;
        this.userID = userID;
        this.lastMsg = lastMsg;
        this.status = status;
        this.learning = learning;
        this.teaching = teaching;
    }

    public User() {
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLearning() {
        return learning;
    }

    public void setLearning(String learning) {
        this.learning = learning;
    }

    public String getTeaching() {
        return teaching;
    }

    public void setTeaching(String teaching) {
        this.teaching = teaching;
    }
}
