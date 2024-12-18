package com.purchasing.health.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
@Entity
public class DataElement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String dhis2id;
    private String datalelementname;
    private String dataelementtype;
    private String startperiode;
    private String endperiode;
    @JoinColumn(name="id")
    private Dataset dataset;
    private Double point;

    public void setPoint(Double point){
        this.point=point;
    } 
    public Double getPoint(){
        return this.point;
    }
    public Long getId() {
        return this.id;
    }

    public String getStartPeriode() {
        return this.startperiode;
    }

    public void setStartPeriode(String StartPeriode) {
        this.startperiode = StartPeriode;
    }

    public String getEndPeriode() {
        return this.endperiode;
    }

    public void setEndPeriode(String EndPeriode) {
        this.endperiode = EndPeriode;
    }

    public String getDhis2id() {
        return this.dhis2id;
    }

    public void setDhis2id(String dhis2id) {
        this.dhis2id = dhis2id;
    }

    public String getDatalelementname() {
        return this.datalelementname;
    }

    public void setDatalelementname(String datalelementname) {
        this.datalelementname = datalelementname;
    }

    public String getDataelementtype() {
        return this.dataelementtype;
    }

    public void setDataelementtype(String dataelementtype) {
        this.dataelementtype = dataelementtype;
    }

    public Dataset getDataset() {
        return this.dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public DataElement(){}

    public DataElement(String dhis2id, String datalelementname,String startperiode,String endperiode ,String dataelementtype, Dataset dataset,Double point) {
        this.dhis2id = dhis2id;
        this.datalelementname = datalelementname;
        this.dataelementtype = dataelementtype;
        this.startperiode=startperiode;
        this.endperiode=endperiode;
        this.dataset = dataset;
        this.point=point;
    }

}
