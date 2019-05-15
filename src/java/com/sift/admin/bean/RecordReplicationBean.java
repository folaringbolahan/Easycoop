/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.admin.bean;

/**
 *
 * @author kola
 */
public class RecordReplicationBean {
    private int id;
    private int branchid;
    private int companyid;
    private boolean movecoop;
    private boolean moveproduct;
    private boolean movemember;
    private boolean moveaddress;
    private String branchName;
    private String companyName;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the branchid
     */
    public int getBranchid() {
        return branchid;
    }

    /**
     * @param branchid the branchid to set
     */
    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    /**
     * @return the companyid
     */
    public int getCompanyid() {
        return companyid;
    }

    /**
     * @param companyid the companyid to set
     */
    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    /**
     * @return the movecoop
     */
    public boolean isMovecoop() {
        return movecoop;
    }

    /**
     * @param movecoop the movecoop to set
     */
    public void setMovecoop(boolean movecoop) {
        this.movecoop = movecoop;
    }

    /**
     * @return the moveproduct
     */
    public boolean isMoveproduct() {
        return moveproduct;
    }

    /**
     * @param moveproduct the moveproduct to set
     */
    public void setMoveproduct(boolean moveproduct) {
        this.moveproduct = moveproduct;
    }

    /**
     * @return the movemember
     */
    public boolean isMovemember() {
        return movemember;
    }

    /**
     * @param movemember the movemember to set
     */
    public void setMovemember(boolean movemember) {
        this.movemember = movemember;
    }
    public boolean isMoveaddress() {
        return moveaddress;
    }

    /**
     * @param movemember the movemember to set
     */
    public void setMoveaddress(boolean moveaddress) {
        this.moveaddress = moveaddress;
    }

    /**
     * @return the branchName
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName the branchName to set
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

  
}

