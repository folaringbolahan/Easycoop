/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.easycoopfin.models;

/**
 *
 * @author logzy
 */
import java.io.Serializable;

public class VoteAccessQuestions implements Serializable {

    private int id;
    private String question;
    private String code;
    private String fieldType;
    public VoteAccessQuestions(){
        
    }
    private void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }

    public void setQuestion(String value) {
        this.question= value;
    }

    public String getQuestion() {
        return question;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String getCode() {
        return code;
    }
 public void setFieldType(String value) {
        this.fieldType = value;
    }

    public String getFieldType() {
        return fieldType;
    }
    public String toString() {
        return String.valueOf(getId());
    }
}
