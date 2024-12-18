package com.purchasing.health.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class DataSetEncoded implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @JoinColumn
    private Dataset datasetid;
    private Integer year;
    private String month;
    private String dateencode;
    private Long idorgnit;

    public DataSetEncoded(Dataset datasetid, Integer year, String month, String dateencode, Long idorgnit) {
        this.datasetid = datasetid;
        this.year = year;
        this.month = month;
        this.dateencode = dateencode;
        this.idorgnit = idorgnit;
    }


    public DataSetEncoded() {
    }

    public Dataset getDatasetid() {
        return this.datasetid;
    }

    public void setDatasetid(Dataset datasetid) {
        this.datasetid = datasetid;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getMonth() {
        return this.month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDateencode() {
        return this.dateencode;
    }

    public void setDateencode(String dateencode) {
        this.dateencode = dateencode;
    }

    public Long getIdorgnit() {
        return this.idorgnit;
    }

    public void setIdorgnit(Long idorgnit) {
        this.idorgnit = idorgnit;
    }


}
