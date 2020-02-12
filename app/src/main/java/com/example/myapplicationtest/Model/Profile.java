package com.example.myapplicationtest.Model;

public class Profile {
    private String name;
    private String status;
    private String avatar;

    public Profile() {
    }

    public Profile(String name, String status, String avatar) {
        this.name = name;
        this.status = status;
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getAvatar() {
        return avatar;
    }
}
