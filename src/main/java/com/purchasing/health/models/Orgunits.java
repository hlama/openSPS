package com.purchasing.health.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
@Entity
public class Orgunits implements Serializable{
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String dhis2id;
    private String name;
    @JoinColumn(name = "id")
    private TypeOrgunit typeOrgunit;
    @JoinColumn(name = "id")
    private AireSante aireSante;

    public Orgunits(){}


    public Orgunits(String dhis2id, String name, TypeOrgunit typeOrgunit,AireSante aireSante) {

        this.dhis2id = dhis2id;
        this.name = name;
        this.typeOrgunit = typeOrgunit;
        this.aireSante=aireSante;
    }

    public Long getId() {
        return id;
    }

    public AireSante getAireSante() {
        return aireSante;
    }

    public void setAireSante(AireSante aireSante) {
        this.aireSante = aireSante;
    }

    public String getDhis2id() {
        return this.dhis2id;
    }

    public void setDhis2id(String dhis2id) {
        this.dhis2id = dhis2id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TypeOrgunit getTypeOrgunit() {
        return this.typeOrgunit;
    }

    public void setTypeOrgunit(TypeOrgunit typeOrgunit) {
        this.typeOrgunit = typeOrgunit;
    }


}
