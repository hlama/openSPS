package com.purchasing.health.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Coasting implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long startdate;
    private Long enddate;
    private String datecreated;
    @JoinColumn
    private Dataset datasetid;
    private double coast;

    public Coasting( String name, Long startdate, Long enddate, String datecreated, Dataset datasetid, double coast) {
        this.name = name;
        this.startdate = startdate;
        this.enddate = enddate;
        this.datecreated = datecreated;
        this.datasetid = datasetid;
        this.coast = coast;
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

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String datecreated) {
        this.datecreated = datecreated;
    }

    public Dataset getDatasetid() {
        return datasetid;
    }

    public void setDatasetid(Dataset datasetid) {
        this.datasetid = datasetid;
    }

    public double getCoast() {
        return coast;
    }

    public void setCoast(double coast) {
        this.coast = coast;
    }
}
