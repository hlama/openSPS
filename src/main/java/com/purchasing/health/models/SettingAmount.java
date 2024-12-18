package com.purchasing.health.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class SettingAmount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String label;
    private Double amount;
    private Long startPeriode;
    private Long endPeriode;
    private Integer year;
    @JoinColumn
    private TypeOrgunit typeOrgunit;
    @JoinColumn
    private Dataset datasetid;

    public SettingAmount(String label, Double amount, Long startPeriode, Long endPeriode,Integer year,TypeOrgunit typeOrgunit,Dataset datasetid) {
        this.label = label;
        this.amount = amount;
        this.startPeriode = startPeriode;
        this.endPeriode = endPeriode;
        this.year=year;
        this.typeOrgunit = typeOrgunit;
        this.datasetid=datasetid;
    }

    public SettingAmount() {
    }

    public Dataset getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(Dataset datasetid) {
        this.datasetid = datasetid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getStartPeriode() {
        return startPeriode;
    }

    public void setStartPeriode(Long startPeriode) {
        this.startPeriode = startPeriode;
    }

    public Long getEndPeriode() {
        return endPeriode;
    }

    public void setEndPeriode(Long endPeriode) {
        this.endPeriode = endPeriode;
    }

    public TypeOrgunit getTypeOrgunit() {
        return typeOrgunit;
    }

    public void setTypeOrgunit(TypeOrgunit typeOrgunit) {
        this.typeOrgunit = typeOrgunit;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }
}
