package com.sift.hp.model;

import java.io.Serializable;
public class Hprepaymtfreq implements Serializable {

    public Hprepaymtfreq() {
    }
    private int id;
    private String code;
    private String description;
    

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getCode() {
        return code;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }
}
