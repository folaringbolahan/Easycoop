/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.bean;

import com.sift.votes.model.VotVoteoptions;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public class VotQuestAndOptionsBean {
    private  int  qestionid;
    private String question;
    private String createdby;
    private String modifiedby;
   
    private List<VotVoteoptions> voteOptions;

    /**
     * @return the voteQuestions
     */
   
    /**
     * @return the voteOptions
     */
    public List<VotVoteoptions> getVoteOptions() {
        return voteOptions;
    }

    /**
     * @param voteOptions the voteOptions to set
     */
    public void setVoteOptions(List<VotVoteoptions> voteOptions) {
        this.voteOptions = voteOptions;
    }

    /**
     * @return the qestionid
     */
    public int getQestionid() {
        return qestionid;
    }

    /**
     * @param qestionid the qestionid to set
     */
    public void setQestionid(int qestionid) {
        this.qestionid = qestionid;
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
    
    public String getCreatedby() {
        return createdby;
    }

    /**
     * @param createdby the createdby to set
     */
    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }
    
    public String getModifiedby() {
        return modifiedby;
    }

    /**
     * @param createdby the createdby to set
     */
    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    /**
     * @return the option
     */
   
    
}
