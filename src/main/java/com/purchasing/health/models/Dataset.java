package com.purchasing.health.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
@Entity
public class Dataset implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String datasetname;
    @JoinColumn(name = "typeorgunit")
    private TypeOrgunit typeOrgunit;
    private String periode;
    private Integer statut;
    public Dataset(){}

    public Dataset(String datasetname, TypeOrgunit typeOrgunit, String periode,int statut) {
        this.datasetname = datasetname;
        this.typeOrgunit = typeOrgunit;
        this.periode = periode;
        this.statut=statut;
    }

    public String getDatasetname() {
        return this.datasetname;
    }

    public void setDatasetname(String datasetname) {
        this.datasetname = datasetname;
    }

    public TypeOrgunit getTypeOrgunit() {
        return this.typeOrgunit;
    }

    public void setTypeOrgunit(TypeOrgunit typeOrgunit) {
        this.typeOrgunit = typeOrgunit;
    }

    public String getPeriode() {
        return this.periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }
    public Integer getStatut() {
            return this.statut;
        }

    public void setStatut(int statut) {
            this.statut = statut;
        }

    public Long getId(){
        return this.id;
    }    
}
