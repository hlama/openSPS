package com.purchasing.health.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
@Entity
public class Users implements Serializable {
    
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @JoinColumn(name = "usergroup")
    private UserGroup usergroup;


    public Users() {
    }

    public Users(String name, String username, String password, UserGroup usergroup) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.usergroup = usergroup;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserGroup getUsergroup() {
        return this.usergroup;
    }

    public void setUsergroup(UserGroup usergroup) {
        this.usergroup = usergroup;
    }
    public Long getId(){
        return this.id;
    }


}
