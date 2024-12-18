package com.purchasing.health.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class DataValue implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @JoinColumn(name = "dataelement")
    private DataElement dataelement;
    @JoinColumn(name = "dataset")
    private Dataset dataset;
    private String month;
    private Integer year;
    private String datecreated;
    private Double value;
    private TypeOrgunit typeOrgunit;
    private Long idorgunit;

    public Long getId() {
        return this.id;
    }
 
    public Double getValue() {
        return this.value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public DataValue() {
    }
    public DataValue(DataElement dataelement, Dataset dataset,TypeOrgunit typeOrgunit ,String month,Integer year, String datecreated,Long idorgunit,Double value) {
        this.dataelement = dataelement;
        this.dataset = dataset;
        this.month = month;
        this.year=year;
        this.datecreated = datecreated;
        this.value=value;
        this.typeOrgunit=typeOrgunit;
        this.idorgunit=idorgunit;
    }

 

    public Long getIdorgunit() {
        return this.idorgunit;
    }

    public void setIdorgunit(Long idorgunit) {
        this.idorgunit = idorgunit;
    }

    public DataElement getDataelement() {
        return this.dataelement;
    }

    public void setDataelement(DataElement dataelement) {
        this.dataelement = dataelement;
    }

    public Dataset getDataset() {
        return this.dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public String getPeriode() {
        return this.month;
    }

    public void setPeriode(String month) {
        this.month = month;
    }

    public String getDatecreated() {
        return this.datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }
    public void setYear(Integer year){
        this.year=year;
    }
    public Integer getYear(){
        return this.year;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public TypeOrgunit getTypeOrgunit() {
        return this.typeOrgunit;
    }

    public void setTypeOrgunit(TypeOrgunit typeOrgunit) {
        this.typeOrgunit = typeOrgunit;
    }

}
