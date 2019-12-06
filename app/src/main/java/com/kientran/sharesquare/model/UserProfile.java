package com.kientran.sharesquare.model;

public class UserProfile {

    private String id;
    private String role_id;
    private String name;
    private String email;
    private String avatar;
    private String created_at;



    public UserProfile(){}

    public UserProfile(String id, String role_id, String name, String email, String avatar, String created_at, String updated_at) {
        this.id = id;
        this.role_id = role_id;
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    private String updated_at;

    @Override
    public String toString() {
        return "UserProfile{" +
                "id='" + id + '\'' +
                ", role_id='" + role_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
