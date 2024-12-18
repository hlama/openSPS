package com.purchasing.health.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class AccountBank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JoinColumn
    private Bank bank;
    private String libelle;
    private String accountnumber;
    private Long idorgunit;
    private String activity;


    public AccountBank(Bank idbank, String libelle, String accountnumber,Long idorgunit,String activity) {
        this.bank = idbank;
        this.libelle = libelle;
        this.accountnumber = accountnumber;
        this.idorgunit=idorgunit;
        this.activity=activity;
    }

    public AccountBank() {
    }

    public Bank getIdbank() {
        return bank;
    }

    public void setIdbank(Bank idbank) {
        this.bank = idbank;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public Long getIdorgunit() {
        return idorgunit;
    }

    public void setIdorgunit(Long idorgunit) {
        this.idorgunit = idorgunit;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
