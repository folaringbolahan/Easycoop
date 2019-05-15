/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.service;

import com.sift.votes.model.VotBallottypes;
import java.util.List;

/**
 *
 * @author Nelson Akpos
 */
public interface VotBallottypesService {
   public List<VotBallottypes> listBallotypesDistinct(String ballotid); 
   public List<VotBallottypes>listBallotypes();
}
