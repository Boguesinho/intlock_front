package com.example.intlok.models;

public class Cuenta {
    private int idCuenta;
    private int idUsuario;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String genero;
    private String info;

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaellidos() {
        return apellidos;
    }

    public void setApaellidos(String apaellidos) {
        this.apellidos = apaellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getInfo() { return info; }

    public void setInfo (String info){ this.info = info; }
}
