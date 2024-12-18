package com.purchasing.health.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class AireSante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dhis2id;
    private String codename;
    private String name;
    @JoinColumn
    private ZoneSante zoneSante;
    @JoinColumn
    private Provinces idProvinces;

    public AireSante(String dhis2id, String codename, String name, ZoneSante zoneSante, Provinces idProvinces) {
        this.dhis2id = dhis2id;
        this.codename = codename;
        this.name = name;
        this.zoneSante = zoneSante;
        this.idProvinces = idProvinces;
    }

    public AireSante() {
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

    public ZoneSante getZoneSante() {
        return zoneSante;
    }

    public void setZoneSante(ZoneSante zoneSante) {
        this.zoneSante = zoneSante;
    }

    public Provinces getIdProvinces() {
        return idProvinces;
    }

    public void setIdProvinces(Provinces idProvinces) {
        this.idProvinces = idProvinces;
    }
}
