/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.agm.service.impl;

import com.sift.agm.service.AgmService;
import com.sift.easycoopfin.models.AgmProxy;
import com.sift.easycoopfin.models.Agmattendees;
import com.sift.easycoopfin.models.AgmattendeesCriteria;
import com.sift.easycoopfin.models.Agms;
import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.CompanyCriteria;
import com.sift.easycoopfin.models.DAOFactory;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.VoteAccessQuestions;
import com.sift.easycoopfin.models.Voteoptions;
import com.sift.easycoopfin.models.VoteoptionsCriteria;
import com.sift.easycoopfin.models.Voteresults;
import com.sift.easycoopfin.models.VoteresultsCriteria;
import com.sift.easycoopfin.models.Votes;
import com.sift.easycoopfin.models.VotesCriteria;
import com.sift.financial.member.ws.client.MemberVote;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author logzy
 */
@Service
public class AmgServiceImpl implements AgmService {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AmgServiceImpl.class);
    private DAOFactory daoFactory;
    private static WebResource webResource;
    private static Client client;
    private String theerrormess = "";
    private String DBASE_URI = "";
    private String output;

    public Agms save(Agms agm) {
        String pattern = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = null;
        Date endDate = null;
        try {
            String formatedStartDate = myFormat.format(fromUser.parse(agm.getStringStartDate()));
            startDate = myFormat.parse(formatedStartDate);
            java.sql.Time startTime = java.sql.Time.valueOf(agm.getStringStartTime());
            java.sql.Time endTime = java.sql.Time.valueOf(agm.getStringEndTime());
            agm.setStartDate(startDate);
            agm.setStartTime(startTime);
            agm.setEndTime(endTime);

            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().save(agm)) {
                _logger.info("AGM saved");
            }

        } catch (PersistentException ex) {

            _logger.error("agm save error ", ex);
        } catch (Exception ex) {

            _logger.error("agm save error ", ex);
        }
        return agm;
    }

    @Override
    public List<Agms> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Member> listMembers(int companyId) {

        MemberCriteria criteria;
        List<Member> members = null;
        try {

            criteria = new MemberCriteria();
            criteria.add(Restrictions.eq("companyId", companyId));
            System.out.println("Company Id :" + companyId);
            members = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().listAllMembers(criteria);

            for (Member m : members) {
                System.out.println("Name:" + m.getFirstName() + " " + m.getSurname());
            }

        } catch (PersistentException ex) {
            _logger.error("listMembers()", ex);
        }
        return members;
    }

    @Override
    public void addMembers(String ids, String agmId) {

        ArrayList<String> list = null;
        list = new ArrayList(Arrays.asList(ids.split("\\s*,\\s*")));

        for (String id : list) {
            try {
                Agmattendees attendees = new Agmattendees();
                Member member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().getMemberByORMID(Integer.valueOf(id));
                String particiantName = member.getFirstName() + " " + member.getSurname();
                attendees.setAgmsId(Integer.valueOf(agmId));
                attendees.setMemberId(Integer.valueOf(id));
                attendees.setParticipantName(particiantName);

                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().save(attendees);
            } catch (PersistentException ex) {
                _logger.error("addMembers", ex);
            }
        }
    }

    public void removeMembers(String ids, String agmId) {
        ArrayList<String> list = null;
        list = new ArrayList(Arrays.asList(ids.split("\\s*,\\s*")));

        List<Integer> strList = new ArrayList<Integer>();

        for (String id : list) {
            try {
                strList.add(Integer.valueOf(id));
                String query = "DELETE from Agmattendees P WHERE P.memberId = :member_id AND P.agmsId = :agms_id";
                System.out.println("ID: " + id);
                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().deleteWithQuery(query, Integer.valueOf(id), Integer.valueOf(agmId));
            } catch (PersistentException ex) {
                _logger.error("removeMembers", ex);
            }
        }
    }

    @Override
    public int deleteParticipant(String ids, String agmId) {
        ArrayList<String> list = null;
        list = new ArrayList(Arrays.asList(ids.split("\\s*,\\s*")));
        int result = 0;
        List<Integer> strList = new ArrayList<Integer>();

        for (String id : list) {
            strList.add(Integer.valueOf(id));
        }
        try {
            String query = "DELETE from Agmattendees P WHERE P.memberId in (:member_id) AND P.agmsId = :agms_id";

            result = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().deleteParticipant(query, strList, Integer.valueOf(agmId));
            System.out.println("Result: " + result);
        } catch (PersistentException ex) {
            _logger.error("removeMembers", ex);
        }
        return result;
    }

    @Override
    public List<Agmattendees> viewMembers(int agmId) {
        AgmattendeesCriteria criteria;
        List<Agmattendees> participants = null;
        try {

            criteria = new AgmattendeesCriteria();
            criteria.add(Restrictions.eq("agmsId", agmId));
            participants = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().listParticipantsByCriteria(criteria);



        } catch (PersistentException ex) {
            _logger.error("viewMembers()", ex);
        }
        return participants;
    }

    @Override
    public List<Votes> listVotes(int companyId) {
        VotesCriteria criteria;
        List<Votes> votes = null;

        try {

            criteria = new VotesCriteria();
            criteria.add(Restrictions.eq("companyId", companyId));
            votes = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().listAllVotesByCriteria(criteria);

        } catch (PersistentException ex) {
            _logger.error("listVotes()", ex);
        }
        return votes;
    }

    @Override
    public List<Voteoptions> listVoteOption(int voteId) {
        List<Voteoptions> options = null;
        VoteoptionsCriteria criteria;
        try {
            criteria = new VoteoptionsCriteria();
            criteria.add(Restrictions.eq("voteId", voteId));
            options = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().listAllVoteoptionsByCriteria(criteria);
        } catch (PersistentException ex) {


            _logger.error("loadProdcut error ", ex);
        }

        return options;
    }

    @Override
    public Votes saveVote(Votes vote) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fromUser = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date voteDate = null;
        Date voteEndDate = null;
        try {
            String formatedVoteDate = myFormat.format(fromUser.parse(vote.getStringDate()));
            String formatedEndDate = myFormat.format(fromUser.parse(vote.getStringEndDate()));
            java.sql.Time startTime = java.sql.Time.valueOf(vote.getStringStartTime());
            java.sql.Time endTime = java.sql.Time.valueOf(vote.getStringEndTime());
            voteDate = myFormat.parse(formatedVoteDate);
            voteEndDate = myFormat.parse(formatedEndDate);
            vote.setVoteDate(voteDate);
            vote.setEndDate(voteEndDate);
            vote.setStartTime(startTime);
            vote.setEndTime(endTime);

            com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVotesDAO().save(vote);
        } catch (PersistentException ex) {
            _logger.error("save(Votes vote)", ex);
        } catch (Exception ex) {
            _logger.error("save(Votes vote)", ex);
        }
        return vote;

    }

    @Override
    public Voteoptions saveOptions(Voteoptions options) {
        try {
            com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteoptionsDAO().save(options);
        } catch (PersistentException ex) {
            _logger.error("saveOptions(Votes vote)", ex);
        }
        return options;
    }

    @Override
    public String castVote(Voteresults vot) {
        VoteresultsCriteria criteria = null;
        Voteresults result = null;
        int voteCount = 0;

        Member member = null;
        Company company = null;
        String status = "";
        
        try {
            member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().getMemberByORMID(vot.getMemberId());
            company = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().getCompanyByORMID(member.getCompanyId());
            vot.setMemberName(member.getFirstName() + " " + member.getSurname());
            criteria = new VoteresultsCriteria();
            Criterion memberId = Restrictions.eq("memberId", vot.getMemberId());
            Criterion voteId = Restrictions.eq("voteId", vot.getVoteId());
            LogicalExpression andExp = Restrictions.and(memberId, voteId);
            criteria.add(andExp);
            int memberVoteCast = getMemberVoteCounts(vot.getMemberId(),vot.getVoteId());
            voteCount = getVoteCount(member.getMemberNo(), company.getCode());
            System.out.println("Member Code:"+member.getMemberNo());
            System.out.println("Company Code:"+company.getCode());
            System.out.println("Vote Count:" + voteCount);
            System.out.println("Vote Result Count:"+memberVoteCast);
            
            result = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().loadVoteresultsByCriteria(criteria);

            if (memberVoteCast <= voteCount) {
                
                com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteresultsDAO().save(vot);
                status = "You vote has been accepted successfully";
            } else {
                status = "You have reached your voting limit!";
            }
        } catch (PersistentException ex) {
            _logger.error("castVote(Voteresults vot)", ex);
        }
        return status;
    }

    @Override
    public List<Member> searchMembers(String query) {
        List<Member> members = null;
        try {
            members = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().listMembersByQuery(query, null);


        } catch (PersistentException ex) {
            _logger.error("searchMembers(String query)", ex);
        }
        return members;
    }

    @Override
    public boolean addMember(int id, int agmId) {
        boolean status = false;
        try {
            Agmattendees attendees = new Agmattendees();
            Member member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().getMemberByORMID(id);
            String particiantName = member.getFirstName() + " " + member.getSurname();
            attendees.setAgmsId(agmId);
            attendees.setMemberId(id);
            attendees.setParticipantName(particiantName);

            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmattendeesDAO().save(attendees)) {
                status = true;
            } else {
                status = false;
            }
        } catch (PersistentException ex) {
            _logger.error("addMember(int id, int agmId)", ex);
        }
        return status;
    }

    public List<VoteAccessQuestions> getQuestions() {
        List<VoteAccessQuestions> questions = null;
        try {
            questions = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getVoteAccessQuestionsDAO().listAllVoteAccessQuestionsByQuery(null, null);
        } catch (PersistentException ex) {
            Logger.getLogger(AmgServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            _logger.error("getQuestions()", ex);
        }

        return questions;
    }

    public List<Company> getCompanies(String name) {
        List<Company> companies = null;
        try {
            CompanyCriteria criteria = new CompanyCriteria();
            criteria.add(Restrictions.ilike("name", name + "%"));
            companies = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getCompanyDAO().listCompaniesByCriteria(criteria);

        } catch (PersistentException ex) {
            _logger.error("getCompanies(String name)", ex);
        }
        return companies;
    }

    @Override
    public boolean getMemberByNumber(String memberNo, int companyId) {
        boolean status = false;
        Member member = null;
        try {
            MemberCriteria criteria = new MemberCriteria();
            criteria.add(Restrictions.eq("memberNo", memberNo));
            criteria.add(Restrictions.eq("companyId", companyId));
            member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(criteria);
            if (member != null) {
                status = true;
            } else {
                status = false;
            }
        } catch (PersistentException ex) {
            Logger.getLogger(AmgServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    @Override
    public boolean getMemberByEmail(String email, int companyId) {
        boolean status = false;
        Member member = null;
        try {
            MemberCriteria criteria = new MemberCriteria();
            criteria.add(Restrictions.eq("emailAddress", email));
            criteria.add(Restrictions.eq("companyId", companyId));
            member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(criteria);
            if (member != null) {
                status = true;
            } else {
                status = false;
            }
        } catch (PersistentException ex) {
            Logger.getLogger(AmgServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    @Override
    public boolean getMemberByPhone(String phone, int companyId) {
        boolean status = false;
        Member member = null;
        try {
            MemberCriteria criteria = new MemberCriteria();
            criteria.add(Restrictions.eq("phoneNumber", phone));
            criteria.add(Restrictions.eq("companyId", companyId));
            member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(criteria);
            if (member != null) {
                status = true;
            } else {
                status = false;
            }
        } catch (PersistentException ex) {
            Logger.getLogger(AmgServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    @Override
    public float getLastContributionAmount(float amount, int companyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AgmProxy save(AgmProxy agmProxy) {
        try {
            if (com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmProxyDAO().save(agmProxy)) {
                _logger.info("Proxy saved");
            }
        } catch (PersistentException ex) {
            _logger.error("save(AgmProxy agmProxy)", ex);
        }
        return agmProxy;
    }

    @Override
    public List<AgmProxy> listProxies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getMemberVoteCounts(int memberId,int voteId) throws PersistentException {
        VoteresultsCriteria criteria = new VoteresultsCriteria();
        criteria.add(Restrictions.eq("memberId", memberId));
        criteria.add(Restrictions.eq("voteId", voteId));
        criteria.setProjection(Projections.rowCount());
        criteria.uniqueResult();
        return  (Integer)criteria.uniqueResult();
    }

    public int getVoteCount(String memberCode, String companyCode) {
        boolean success = false;
        int voteCount = 0;
        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
            DBASE_URI = uri;
            System.out.println("URL: " + uri);
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            webResource = client.resource(DBASE_URI).path("coopvote/" + companyCode + "/" + memberCode);

            ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
            } else {
                success = true;
            }

            MemberVote memberVote = response.getEntity(MemberVote.class);
            voteCount = Integer.valueOf(memberVote.getVote());
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return voteCount;
    }

    @Override
    public String validateMember(String question1, String question2, String question3, String question4, String code1, String code2, String code3, String code4, int companyId, HttpServletRequest request) {
        boolean q1 = false;
        boolean q2 = false;
        boolean q3 = false;
        boolean q4 = false;
        String status = "";
        try{
        q1 = getMemberByNumber(question1, companyId);
        q2 = getMemberByEmail(question2, companyId);
        q3 = getMemberByPhone(question3, companyId);
          if(q1==true && q2==true && q3==true){
              byte isLoggedIn = 0;
              MemberCriteria criteria = new MemberCriteria();
              criteria.add(Restrictions.eq("memberNo", question1));
              Member member = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getMemberDAO().loadMemberByCriteria(criteria);
              System.out.println("Member ID:"+member.getId());
              request.getSession().setAttribute("voteMemberId", member.getId());
              request.getSession().setAttribute("voteMemberCompanyId", member.getCompanyId());
              request.getSession().setAttribute("isLoggedIn", isLoggedIn);
              status = "ok";
          }else{
              status = "Access denied! Please reveiew your answers and try again";
          }
        }catch(PersistentException ex){
             _logger.error("validateMember(String question1, String question2, String question3, String question4, String code1, String code2, String code3, String code4, int companyId, HttpServletRequest request)", ex);
        }
        return status;
    }
}
