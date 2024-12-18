package com.purchasing.health.models;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class ZoneSante implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dhis2id;
    private String codename;
    private String name;
    @JoinColumn(name = "id")
    private Provinces idProvinces;

    public ZoneSante(String dhis2id, String codename, String name, Provinces idProvinces) {
        this.dhis2id = dhis2id;
        this.codename = codename;
        this.name = name;
        this.idProvinces = idProvinces;
    }

    public ZoneSante() {
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public Provinces getIdProvinces() {
        return idProvinces;
    }

    public void setIdProvinces(Provinces idProvinces) {
        this.idProvinces = idProvinces;
    }
    public Long getId(){
        return this.id;
    }
}
