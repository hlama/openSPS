package com.purchasing.health.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class TypeOrgunit implements Serializable{
    
    @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String libelle;

    public TypeOrgunit(String libelle) {
        this.libelle = libelle;
    }
    public TypeOrgunit (){}

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public Long getId(){
        return this.id;
    }

}
