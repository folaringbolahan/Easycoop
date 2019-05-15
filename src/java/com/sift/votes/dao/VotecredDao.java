package com.sift.votes.dao;

import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotCreds;
import java.util.List;

public interface VotecredDao {
         public boolean addCred(VotCreds cred);
         public List<Object[]> getMembersofagmwithoutcred(Integer agmid);
         public boolean updatemailedCred(Integer agmid,Integer memberid);
         public List<VotAgm> listActiveAgm();
         public List<Object[]> getMembersofagmnotvoted(Integer agmid);
         public List<Object[]> getMemberbyemailofagmnotvoted(String membermail);
         public List<Object[]> getMemberbyemailofagmnotvoted(Integer memberid);
         public boolean updateCred(VotCreds cred);
}