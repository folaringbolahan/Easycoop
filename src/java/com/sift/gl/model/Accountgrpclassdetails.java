/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.model;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
public class Accountgrpclassdetails   { // implements Serializable {
   private String code;
   private String description;
    public Accountgrpclassdetails() {
    }
    public Accountgrpclassdetails(String code) {
        this.code = code;
    }
    public Accountgrpclassdetails(String code, String description) {
        this.code = code;
        this.description = description;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
 }
