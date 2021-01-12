package com.example.intlok.models;

import com.google.gson.JsonArray;

import java.sql.Date;

public class FollowerResponse {
    private int id;
    private String username;
    private Date created;
    private Date updated;
    private JsonArray pivot;

    public FollowerResponse(int id, String username, Date created, Date updated, JsonArray pivot) {
        this.id = id;
        this.username = username;
        this.created = created;
        this.updated = updated;
        this.pivot = pivot;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public JsonArray getPivot() {
        return pivot;
    }
}
