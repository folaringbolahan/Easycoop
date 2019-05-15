/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.agm.service;

import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.Agmattendees;
import com.sift.easycoopfin.models.Agms;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.VoteAccessQuestions;
import com.sift.easycoopfin.models.Voteoptions;
import com.sift.easycoopfin.models.Voteresults;
import com.sift.easycoopfin.models.Votes;
import com.sift.easycoopfin.models.AgmProxy;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author logzy
 */
public interface AgmService {

    public Agms save(Agms agm);

    public List<Agms> list();

    public List<Member> listMembers(int companyId);

    public List<Agmattendees> viewMembers(int agmId);

    public void addMembers(String ids, String agmId);

    public boolean addMember(int id, int agmId);

    public void removeMembers(String ids, String agmId);

    public int deleteParticipant(String ids, String agmId);

    public List<Votes> listVotes(int companyId);

    public List<Voteoptions> listVoteOption(int voteId);

    public Votes saveVote(Votes vote);

    public Voteoptions saveOptions(Voteoptions options);

    public String castVote(Voteresults vot);

    public List<Member> searchMembers(String query);

    public List<VoteAccessQuestions> getQuestions();

    public List<Company> getCompanies(String name);
    //for votes

    public boolean getMemberByNumber(String memberNo, int companyId);

    public boolean getMemberByEmail(String email, int companyId);

    public boolean getMemberByPhone(String phone, int companyId);

    public float getLastContributionAmount(float amount, int companyId);
    //methods for Proxies

    public AgmProxy save(AgmProxy agmProxy);

    public List<AgmProxy> listProxies();
    public String validateMember(String question1, 
                    String question2, 
                    String question3,
                    String question4,
                    String code1, 
                    String code2, 
                    String code3,
                    String code4,
                    int companyId, HttpServletRequest request);
}
