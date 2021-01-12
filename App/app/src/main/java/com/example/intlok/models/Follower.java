package com.example.intlok.models;

import java.sql.Date;

public class Follower {
    private int id;
    private int idFollower;
    private int idUsuario;
    private Date created;
    private Date updated;

    public Follower(int id, int idFollower, int idUsuario, Date created, Date updated) {
        this.id = id;
        this.idFollower = idFollower;
        this.idUsuario = idUsuario;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFollower() {
        return idFollower;
    }

    public void setIdFollower(int idFollower) {
        this.idFollower = idFollower;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
