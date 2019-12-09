package com.kientran.sharesquare.model;

import java.util.Date;

public class Message {
    private final int id;
    private final String content;



    private  String status;
    private final Date created_at;
    private final String authorname;
    private final String authoremail;
    private final int authorid;

    public Message(int id, String content, String status, Date created_at, String authorname, String authoremail, int authorid) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.created_at = created_at;
        this.authorname = authorname;
        this.authoremail = authoremail;
        this.authorid = authorid;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getCreated_at() {
        return created_at;
    }

    public String getAuthorname() {
        return authorname;
    }

    public String getAuthoremail() {
        return authoremail;
    }

    public int getAuthorid() {
        return authorid;
    }
}
