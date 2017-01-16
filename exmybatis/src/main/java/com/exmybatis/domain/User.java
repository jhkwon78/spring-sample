package com.exmybatis.domain;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {
    String user_email;
    String user_name;
    String user_type;
    String user_id;
    String user_password;

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getUserId() {
        return this.user_id;
    }

    public String getEmailAddress() {
        return this.user_email;
    }

    public void setEmailAddress(String user_email) {
        this.user_email = user_email;
    }

    public String getUserName() {
        return this.user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public void setUserType(String user_type) {
        this.user_type = user_type;
    }

    public String getUserType() {
        return this.user_type;
    }

    public void setUserPassword(String user_password) {
        this.user_password = user_password;
    }

    public String getUserPassword() {
        return this.user_password;
    }

    @Override
    public String toString() {
        return this.user_id + "," + this.user_name + "," + this.user_email;
    }
}

