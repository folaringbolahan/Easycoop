/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.bean;

/**
 *
 * @author Nelson Akpos
 */
public class VotVoteoptionsBean {
   private Integer id;
   
    private String description;
    
    private Integer voteid;
   
    private String deleted;  
    private String question;
    private String voteoption;
    private int agmid;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the voteid
     */
    public Integer getVoteid() {
        return voteid;
    }

    /**
     * @param voteid the voteid to set
     */
    public void setVoteid(Integer voteid) {
        this.voteid = voteid;
    }

    /**
     * @return the deleted
     */
    public String getDeleted() {
        return deleted;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    /**
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return the voteoption
     */
    public String getVoteoption() {
        return voteoption;
    }

    /**
     * @param voteoption the voteoption to set
     */
    public void setVoteoption(String voteoption) {
        this.voteoption = voteoption;
    }

    /**
     * @return the agmid
     */
    public int getAgmid() {
        return agmid;
    }

    /**
     * @param agmid the agmid to set
     */
    public void setAgmid(int agmid) {
        this.agmid = agmid;
    }
}
