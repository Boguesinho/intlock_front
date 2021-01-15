package com.example.intlok.models;

import java.sql.Date;

public class Post {
    private int id;
    private int idUsuario;
    private String descripcion;
    private int idMultimedia;
    private String created;
    private Date updated;
    private String rutaPost;
    private String rutaPerfil;

    public String getRutaPost() {
        return rutaPost;
    }

    public String getRutaPerfil() {
        return rutaPerfil;
    }

    public void setRutaPost(String rutaPost) {
        this.rutaPost = rutaPost;
    }

    public void setRutaPerfil(String rutaPerfil) {
        this.rutaPerfil = rutaPerfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(int idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
