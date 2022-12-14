package com.example.rickandmorty;

import java.io.Serializable;

public class user implements Serializable {
    private String username;
    private String email;

    public user(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public void update(String nome, String email) {
        this.username = nome;
        this.email = email;
    }

    public String GetName() {
        return this.username;
    }

    public String GetEmail() {
        return this.email;
    }
}