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
public class TxnCode implements Serializable {

    public TxnCode() {
    }
    private int id;
    private String transactionCode;
    private String description;
    private String narrative;

    private void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public int getORMID() {
        return getId();
    }

    public void setTransactionCode(String value) {
        this.transactionCode = value;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public String getDescription() {
        return description;
    }

    public void setNarrative(String value) {
        this.narrative = value;
    }

    public String getNarrative() {
        return narrative;
    }

    public String toString() {
        return String.valueOf(getId());
    }
}
