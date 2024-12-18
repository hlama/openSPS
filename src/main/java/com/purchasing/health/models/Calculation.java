package com.purchasing.health.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Calculation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String datecreated;
    private String formula;
    private Long startdate;
    private Long enddate;
    @JoinColumn
    private Dataset datasetid;
    private Integer status;

    public Calculation(){}
    public Calculation(String name, String datecreated, String formula, Long startdate, Long enddate, Dataset datasetid,Integer status) {
        this.name = name;
        this.datecreated = datecreated;
        this.formula = formula;
        this.startdate = startdate;
        this.enddate = enddate;
        this.datasetid = datasetid;
        this.status=status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Long getStartdate() {
        return startdate;
    }

    public void setStartdate(Long startdate) {
        this.startdate = startdate;
    }

    public Long getEnddate() {
        return enddate;
    }

    public void setEnddate(Long enddate) {
        this.enddate = enddate;
    }

    public Dataset getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(Dataset datasetid) {
        this.datasetid = datasetid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
