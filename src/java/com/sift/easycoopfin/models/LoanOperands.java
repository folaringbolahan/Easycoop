/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

import java.io.Serializable;

/**
 *
 * @author logzy
 */
public class LoanOperands  implements Serializable{
    public LoanOperands(){
        
    }
    private int id;
    private String code;
    private String name;
    
    public void setId(int value){
        this.id = value;
    }
    public int getId(){
        return id;
    }
    public void setCode(String value){
        this.code = value;
    }
    public String getCode(){
        return code;
    }
    public void setName(String value){
        this.name = value;
    }
    public String getName(){
        return name;
    }
}
