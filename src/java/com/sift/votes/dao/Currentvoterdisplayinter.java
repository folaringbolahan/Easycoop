/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.dao;

import com.sift.gl.CurrentuserdisplayException;
import com.sift.gl.model.Company;
import com.sift.gl.model.Users;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotMembers;


/**
 *
 * @author ABAYOMI AWOSUSI
 */
public interface Currentvoterdisplayinter {
    public VotMembers retrieveUserdet(Integer userid) throws CurrentuserdisplayException;
    public VotAgm retrieveAgmdet(Integer id) throws CurrentuserdisplayException;
}
