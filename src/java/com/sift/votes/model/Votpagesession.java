/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.model;

import com.sift.votes.bean.VotVotequestsBean;
import java.util.Map;

/**
 *
 * @author kola
 */
public class Votpagesession {
    private Map<Integer,VotVotequests> questaggregate;
    private Integer prevpagestart = 0;
    private Integer noofpages;
    private Integer noofvoteunits;
     public Integer getPrevpagestart() {
        return prevpagestart;
    }

    public void setPrevpagestart(Integer prevpagestart) {
        this.prevpagestart = prevpagestart;
    }
    
    public Integer getNoofpages() {
        return noofpages;
    }

    public void setNoofpages(Integer noofpages) {
        this.noofpages = noofpages;
    }
    
    public Map<Integer,VotVotequests> getQuestaggregate() {
        return questaggregate;
    }

    public void setQuestaggregate(Map<Integer,VotVotequests> questaggregate) {
        this.questaggregate = questaggregate;
    }
    public Integer getNoofvoteunits() {
        return noofvoteunits;
    }

    public void setNoofvoteunits(Integer noofvoteunits) {
        this.noofvoteunits = noofvoteunits;
    }
}
