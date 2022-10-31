package com.example.proyectodam;

public class Connection {

    // string variables for our name and password
    private String username;
    private String password;
    private String Tipo;

    public Connection(String username, String password) {
        this.username = username;
        this.password = password;
        this.Tipo = "Login";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
