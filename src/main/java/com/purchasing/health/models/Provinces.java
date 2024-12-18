package com.purchasing.health.models;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Provinces implements Serializable {
    
     @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dhis2id;
    private String codename;
    private String name;
    private String activity;


    public String getDhis2id() {
        return dhis2id;
    }

    public void setDhis2id(String dhis2id) {
        this.dhis2id = dhis2id;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getName() {
        return name;
    }

       public String getActivity() {
        return this.activity;
    }

    public void setName(String name) {
        this.name = name;
    }
     public void setActivity(String activity) {
        this.activity = activity;
    }
   
    public Provinces(String dhis2id, String codename, String name,String activity) {
        this.dhis2id = dhis2id;
        this.codename = codename;
        this.name = name;
        this.activity=activity;
    }
    public Provinces(){}

}
