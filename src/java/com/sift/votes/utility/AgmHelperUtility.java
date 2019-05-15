/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.utility;

import com.sift.admin.bean.UserBean;
import com.sift.votes.bean.VotAgmBean;
import com.sift.votes.bean.VotMembersBean;
import com.sift.votes.bean.VotVoteoptionsBean;
import com.sift.votes.utility.VotMailBean;
import com.sift.votes.bean.VotVotequestsBean;
import com.sift.votes.dao.VotequestionsDao;
import com.sift.votes.model.VotVoteoptions;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
/**
 *
 * @author Nelson Akpos
 */
@Service
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AgmHelperUtility {
 VotBeanMapperUtility votutility = new VotBeanMapperUtility();
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private VotequestionsDao votequestionsDao;

    public int companyid(String agmid) {
        int companyid_int = 0;
        String sql = "SELECT companyid FROM vot_agm where id='" + agmid + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        String companyid = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();

        companyid_int = Integer.parseInt(companyid);



        return companyid_int;
    }

    public int getCompanyrefid(int companyid) {
        int companyrefid_int = 0;
        String sql = "SELECT companyrefid FROM vot_company where companyid='" + companyid + "'";
        try {
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            String companyrefid = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
            companyrefid_int = Integer.parseInt(companyrefid);
        } catch (Exception e) {
            e.printStackTrace();
        }






        return companyrefid_int;
    }

    public boolean decideMovement(String id) {


        // String agmId=Integer.toString(id);
        String value = "";
        //check if anyone has cast their vote
        
        String sql = "select concluded from vot_members where agmid='" + id + "' and concluded = 'Y'";
        //String value2 = "";
        //String sql2 = "select active from vot_agm where agmid='" + id + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        value = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        //query = sessionFactory.getCurrentSession().createSQLQuery(sql2);
        //value2 = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        System.out.println("the vlaue of concluded is " + value);
        boolean decidemov=false;
        if (query == null || query.list().isEmpty()) {
            decidemov=true;
        } 
        //if ("Y".equals(value2)) {
        //    decidemov=false;
        //} 
        /*else {
            decidemov=false;
        }
        if ("N".equals(value2) || value2.isEmpty()) {
            decidemov=true;
        } else if ("Y".equals(value2) || value2.isEmpty()){
            decidemov=false;
        }
        */ 
        return decidemov;
    }

    public String moveMembers(String agmid ,String loggeduser) {
        String action = "";
        String companyid = "";
        String companyrefid = "";
        String ballotid = "";
        boolean conclude = decideMovement(agmid);
        
        System.out.println("the value of a is  " + conclude);
        //get vot companyid for agm  
        String sql1 = "select companyid,ballotid from vot_agm where id='" + agmid + "'";
        Query query1 = sessionFactory.getCurrentSession().createSQLQuery(sql1);
        //companyid = (query1 == null || query1.list().isEmpty()) ? "" : query1.list().get(0).toString();
        List<Object[]>  coyball = null ;
        if ((query1 != null) && (query1.list().isEmpty()==false))
        {
            coyball = query1.list();
            Object [] coyballarr = coyball.get(0);
            companyid = coyballarr[0].toString();
            ballotid = coyballarr[1].toString();
        }
       // List<Object[]>  coyball = (query1 == null || query1.list().isEmpty()) ? "" : query1.list());
      //  if ((coyball.equals("")==false)&&()) 
       // {
       // String [] coyballarr = (String [])coyball; 
       // companyid = (String)coyballarr[0];
       // ballotid = (String)coyballarr[1];
       // }
        //get easycoop companyid
        String sql2 = "select companyrefid from vot_company as a left join vot_agm as b on b.companyid=a.companyid where b.companyid='" + companyid + "'";
        Query query2 = sessionFactory.getCurrentSession().createSQLQuery(sql2);
        companyrefid = (query2 == null || query2.list().isEmpty()) ? "" : query2.list().get(0).toString();

        //query to check if agm member already exist for a particular agm before member import
        String agmSql = "select agmid from vot_members where agmid='" + agmid + "'";
        Query agmQuery = sessionFactory.getCurrentSession().createSQLQuery(agmSql);
        List agm = agmQuery.list();
        System.err.println("the agm list size for selected agm is " + agm.size());

        if (agm.isEmpty() && conclude == true) {
              
            String sql3 = " INSERT into vot_members(memberrefid,userid,firstname,middlename,surname,email,phone,branchid,companyid,agmid,concluded,voteunits)  SELECT member.member_id,email_add_1 ,first_name,middle_name,surname,email_add_1,phone_no_1,branch_id,company_id,'" + agmid + "','N',b.holdings  from  member left join (select sum(holdings) as holdings,member_id  from member_holdings group by member_id) b on member.member_id=b.member_id where  status_id=12 or status_id=2"+
                    " and company_id='" + companyrefid + "'";
            
            if (ballotid.equals("1")) { // handle one man- ne-vote irrespective of units
              sql3 =  " INSERT into vot_members(memberrefid,userid,firstname,middlename,surname,email,phone,branchid,companyid,agmid,concluded,voteunits)  SELECT member.member_id,email_add_1 ,first_name,middle_name,surname,email_add_1,phone_no_1,branch_id,company_id,'" + agmid + "','N',1 from  member where status_id=12 or status_id=2"+
                    " and company_id='" + companyrefid + "'"; 
            }
            // Query query =sessionFactory.getCurrentSession().createSQLQuery(sql);
            sessionFactory.getCurrentSession().createSQLQuery(sql3).executeUpdate();
            //send mail to second admin at this point
            	
            
               List<UserBean> mails= getAdminMails();
               String admin1= mails.get(0).getEmail().toString();
               String admin2= mails.get(1).getEmail().toString();
                System.out.println("the admin mail1 is "+ mails.get(0).getEmail().toString());
                System.out.println("the logged in User is  "+ loggeduser);
                     
                    if( loggeduser.equals(admin1)){
                     System.out.println("i am in this block");
                    String mailBody="NEW AGM SETTUP AWAITING APPROVAL";
                       VotMailBean MB = votutility.getMailConfig();
                       System.out.println("SMTP HOST "+MB.getMailsmtphost());
                       System.out.println("SMTP PASSWORD "+MB.getPassword());
                       System.out.println("SMTP HOST "+MB.getMailsmtphost());
                       System.out.println("SSLORTLS "+MB.getSslortls());
                      MB.setSubject("AGM SETUP NOTIFICATION");
                      MB.setToemail(admin2);
                      MB.setMailBody(mailBody);
                     votutility.sendMail(MB);
                       }else if(loggeduser.equals(admin2)){
                       System.out.println("the second if block");
                       String mailBody="NEW AGM SETTUP AWAITING APPROVAL";
                       VotMailBean MB = VotBeanMapperUtility.getMailConfig();
                      MB.setSubject("AGM SETUP NOTIFICATION");
                      MB.setToemail(admin1);
                      MB.setMailBody(mailBody);
                       votutility.sendMail(MB);
                       System.out.println("Email sent at this point ");
                           }
                        //send email
                     
            //end sending
            action = "1";
            return action;
        } else if (agm.size() > 0 && conclude == true) {

            //delete from vot_members
            String sql3 = " Delete from vot_members where agmid='" + agmid + "'";
            // Query query =sessionFactory.getCurrentSession().createSQLQuery(sql);
            sessionFactory.getCurrentSession().createSQLQuery(sql3).executeUpdate();
            String sql4 = " INSERT into vot_members(memberrefid,userid,firstname,middlename,surname,email,phone,branchid,companyid,agmid,concluded,voteunits)  SELECT member.member_id,email_add_1 ,first_name,middle_name,surname,email_add_1,phone_no_1,branch_id,company_id,'" + agmid + "','N',b.holdings  from  member left join (select sum(holdings) as holdings,member_id  from member_holdings group by member_id) b on member.member_id=b.member_id where  status_id=12 or status_id=2"+
                    " and company_id='" + companyrefid + "'";
            if (ballotid.equals("1")) { // handle one man- ne-vote irrespective of units
              sql4 =  " INSERT into vot_members(memberrefid,userid,firstname,middlename,surname,email,phone,branchid,companyid,agmid,concluded,voteunits)  SELECT member.member_id,email_add_1 ,first_name,middle_name,surname,email_add_1,phone_no_1,branch_id,company_id,'" + agmid + "','N',1 from  member where status_id=12 or status_id=2"+
                    " and company_id='" + companyrefid + "'"; 
            }
            // Query query =sessionFactory.getCurrentSession().createSQLQuery(sql);
            sessionFactory.getCurrentSession().createSQLQuery(sql4).executeUpdate();
            action = "1";
            return action;
        } else {
            action = "2";
            return action;
        }



    }

    public int memberCount(int agmid) {
        System.out.println("the agmid id is " + agmid);
        String sqlCount = "SELECT count(*) FROM easycoopfin.vot_members where agmid='" + agmid + "'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlCount);
        String result = query.list().get(0).toString();
        int result_int = 0;
        if (result != null) {
            result_int = Integer.parseInt(result);
            System.out.println("converted result.." + result_int);
        }
        return result_int;
    }

    public ArrayList<VotAgmBean> listAgmSetup() {
        ArrayList<VotAgmBean> list = null;
        VotAgmBean votagmBean = null;

        String setupSql = "SELECT distinct vot_agm.id, vot_agm.companyid, vot_agm.startdate, vot_agm.enddate,vot_agm.starttime, vot_agm.endtime, vot_agm.agmyear,vot_agm.description,vot_agm.createdby ,vot_company.companyrefid , vot_company.companyname,vot_members.agmid FROM vot_agm "
                + "inner join vot_company  on vot_company.companyid = vot_agm.companyid "
               // + "inner join company on company.id = vot_company.companyrefid "
                + " left join vot_members  on vot_members.agmid = vot_agm.id  "
                + "where vot_members.agmid is not null "
                + " and vot_agm.active = 'N' ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(setupSql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotAgmBean>();
            }
            votagmBean = new VotAgmBean();

            Object[] row = results.next();
            votagmBean.setId((Integer) row[i++]);
            votagmBean.setCompanyid((int) row[i++]);
            votagmBean.setStartdate((java.sql.Date) row[i++]);
            votagmBean.setEnddate((java.sql.Date) row[i++]);
            votagmBean.setStarttime(row[i++].toString());
            votagmBean.setEndtime(row[i++].toString());
            votagmBean.setAgmyear((Integer) row[i++]);
            votagmBean.setDescription((String) row[i++]);
            votagmBean.setCreatedby((String) row[i++]);
            votagmBean.setCompanyrefid((int) row[i++]);
            votagmBean.setCompanyName((String) row[i++]);
            votagmBean.setMemberCount(memberCount(votagmBean.getId()));
            System.out.println("The total member count is " + votagmBean.getMemberCount());

            list.add(votagmBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the list for setup approval " + list.size());
        }

        return list;
    }

    public void UpdateAgmStatus(String id, String modifiedby, String modifieddate, String lastreminderdate) {
        String sql = "update vot_agm set active = 'Y',modifiedby='" + modifiedby + "',modifieddate='" + modifieddate + "',lastreminderdate='" + lastreminderdate + "' where id ='" + id + "'";
        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }
    //Internal Agm object by id

    public VotAgmBean getAgmSetup(int id) {
        // ArrayList<VotAgmBean> list = null;
        VotAgmBean votagmBean = null;

        String Sql = "SELECT a.id,a.companyid,a.startdate,a.enddate,a.starttime,a.endtime,a.agmyear,a.reminderfrequency,a.description,a.ballotid,a.active,a.baseurl,a.closed,a.importsource,a.createdby,b.companyrefid,b.companyname FROM vot_agm as a "
                + " inner join vot_company as b on b.companyid=a.companyid"
               // + " where a.importsource='I' "
                + " and a.active='N'"
                + " and a.id='" + id + "'";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;

            votagmBean = new VotAgmBean();

            Object[] row = results.next();
            votagmBean.setId((Integer) row[i++]);
            votagmBean.setCompanyid((int) row[i++]);
            votagmBean.setStartdate((java.sql.Date) row[i++]);
            votagmBean.setEnddate((java.sql.Date) row[i++]);
            votagmBean.setStarttime(row[i++].toString());
            votagmBean.setEndtime(row[i++].toString());
            votagmBean.setAgmyear((Integer) row[i++]);
            votagmBean.setReminderfrequency((int) row[i++]);
            votagmBean.setDescription((String) row[i++]);

            votagmBean.setBallotid((int) row[i++]);
            votagmBean.setActive((String)row[i++]);
            votagmBean.setBaseurl((String) row[i++]);
            votagmBean.setClosed((String) row[i++]);
            votagmBean.setImportsource((String)row[i++]);
            votagmBean.setCreatedby((String) row[i++]);

            votagmBean.setCompanyrefid((int) row[i++]);
            votagmBean.setCompanyName((String) row[i++]);



        }


        return votagmBean;
    }

    // External Agm object  by id

    public VotAgmBean getExternalAgmSetup(int id) {
        // ArrayList<VotAgmBean> list = null;
        VotAgmBean votagmBean = null;

        String Sql = "SELECT a.id,a.companyid,a.startdate,a.enddate,a.starttime,a.endtime,a.agmyear,a.reminderfrequency,a.description,b.companyrefid,b.companyname FROM vot_agm as a "
                + " inner join vot_company as b on b.companyid=a.companyid"
                + " where a.importsource='E' "
                + " and a.active='N'"
                + " and a.id='" + id + "'";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;

            votagmBean = new VotAgmBean();

            Object[] row = results.next();
            votagmBean.setId((Integer) row[i++]);
            votagmBean.setCompanyid((int) row[i++]);
            votagmBean.setStartdate((java.sql.Date) row[i++]);
            votagmBean.setEnddate((java.sql.Date) row[i++]);
            votagmBean.setStarttime(row[i++].toString());
            votagmBean.setEndtime(row[i++].toString());
            votagmBean.setAgmyear((Integer) row[i++]);
            votagmBean.setReminderfrequency((int) row[i++]);
            votagmBean.setDescription((String) row[i++]);

            votagmBean.setCompanyrefid((int) row[i++]);
            votagmBean.setCompanyName((String) row[i++]);



        }


        return votagmBean;
    }
    //list of agm for external

    public ArrayList<VotAgmBean> listOfExternalAgmSetup() {
        ArrayList<VotAgmBean> list = null;
        VotAgmBean votagmBean = null;

        String Sql = "SELECT a.id,a.companyid,a.startdate,a.enddate,a.starttime,a.endtime,a.agmyear,a.reminderfrequency,a.description,b.companyrefid,b.companyname FROM vot_agm as a "
                + " inner join vot_company as b on b.companyid=a.companyid"
                + " where a.importsource='E' "
                + " and a.active='N'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotAgmBean>();
            }
            votagmBean = new VotAgmBean();

            Object[] row = results.next();
            votagmBean.setId((Integer) row[i++]);
            votagmBean.setCompanyid((int) row[i++]);
            votagmBean.setStartdate((java.sql.Date) row[i++]);
            votagmBean.setEnddate((java.sql.Date) row[i++]);
            votagmBean.setStarttime(row[i++].toString());
            votagmBean.setEndtime(row[i++].toString());
            votagmBean.setAgmyear((Integer) row[i++]);
            votagmBean.setReminderfrequency((int) row[i++]);
            votagmBean.setDescription((String) row[i++]);

            votagmBean.setCompanyrefid((int) row[i++]);
            votagmBean.setCompanyName((String) row[i++]);

            list.add(votagmBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the list for external agm " + list.size());
        }

        return list;
    }
     //list of agm for internal


    public ArrayList<VotAgmBean> listOfInternalAgmSetup() {
        ArrayList<VotAgmBean> list = null;
        VotAgmBean votagmBean = null;

        String Sql = "SELECT a.id,a.companyid,a.startdate,a.enddate,a.starttime,a.endtime,a.agmyear,a.reminderfrequency,a.description,b.companyrefid,b.companyname FROM vot_agm as a "
                + " inner join vot_company as b on b.companyid=a.companyid"
                + " where a.importsource='I' "
                + " and a.active='N'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotAgmBean>();
            }
            votagmBean = new VotAgmBean();

            Object[] row = results.next();
            votagmBean.setId((Integer) row[i++]);
            votagmBean.setCompanyid((int) row[i++]);
            votagmBean.setStartdate((java.sql.Date) row[i++]);
            votagmBean.setEnddate((java.sql.Date) row[i++]);
            votagmBean.setStarttime(row[i++].toString());
            votagmBean.setEndtime(row[i++].toString());
            votagmBean.setAgmyear((Integer) row[i++]);
            votagmBean.setReminderfrequency((int) row[i++]);
            votagmBean.setDescription((String) row[i++]);

            votagmBean.setCompanyrefid((int) row[i++]);
            votagmBean.setCompanyName((String) row[i++]);

            list.add(votagmBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the list for internal agm " + list.size());
        }

        return list;
    }

    public void updateVotCompany(int companyrefid, String companyname, int companyid) {
        String sql = "update vot_company set companyrefid ='" + companyrefid + "', companyname='" + companyname + "'  where companyid ='" + companyid + "'";
        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }
    public void updateAgmSetup(String startdate, String enddate, String starttime, String endtime, String reminderfrequency, String agmyear, String agmname, int id,String modifiedby, String modifieddate) {
        String sql = "update vot_agm set startdate ='" + startdate + "', enddate='" + enddate + "', starttime='" + starttime + "', endtime='" + endtime + "', reminderfrequency='" + reminderfrequency + "', agmyear='" + agmyear + "', description='" + agmname + "',modifiedby='" + modifiedby + "',modifieddate='" +  modifieddate +"' where id ='" + id + "'";
        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }
    public void updateInternalAgmSetup(String startdate, String enddate, String starttime, String endtime, String reminderfrequency, String agmyear, String agmname, int id) {
        String sql = "update vot_agm set startdate ='" + startdate + "', enddate='" + enddate + "', starttime='" + starttime + "', endtime='" + endtime + "', reminderfrequency='" + reminderfrequency + "', agmyear='" + agmyear + "', description='" + agmname + "' where id ='" + id + "'";
        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }

    public void updateExternalAgmSetup(String startdate, String enddate, String starttime, String endtime, String reminderfrequency, String agmyear, String agmname, int id) {
        String sql = "update vot_agm set startdate ='" + startdate + "', enddate='" + enddate + "', starttime='" + starttime + "', endtime='" + endtime + "', reminderfrequency='" + reminderfrequency + "', agmyear='" + agmyear + "', description='" + agmname + "' where id ='" + id + "'";
        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();

    }

    public void deleteAgmSetup(int id) {

        String sql = "delete a.*,b.* from vot_agm a left join vot_company b on b.companyid=a.companyid where a.id='" + id + "'";
        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }

    public ArrayList<UserBean> getAdminMails() {
        ArrayList<UserBean> list = null;
        UserBean userBean = null;

        String Sql = "SELECT email from users where branch='1' and companyid='1'";;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<UserBean>();
            }
            userBean = new UserBean();

            Object row = results.next();
            userBean.setEmail((String) row);

            list.add(userBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the email list " + list.size());
            System.out.println("the  first mail" + list.get(0).getEmail().toString());
        }

        return list;
    }

    public ArrayList<VotVotequestsBean> listofvotequestions(int agmid) {
        ArrayList<VotVotequestsBean> list = null;
        VotVotequestsBean votequestBean = null;

        String Sql = "SELECT distinct id, agmid, description,votetypeid,electionanswertypeid from vot_votequests where agmid='"+agmid+"'"
                + "  and deleted='N' and active='N' ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotVotequestsBean>();
            }
            votequestBean = new VotVotequestsBean();

            Object[] row = results.next();
            votequestBean.setId((Integer) row[i++]);
            votequestBean.setAgmid((Integer) row[i++]);
            votequestBean.setDescription((String) row[i++]);
            votequestBean.setVotetypeid((int) row[i++]);
            votequestBean.setElectionanswertypeid((int) row[i++]);
            list.add(votequestBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the email list " + list.size());
            
        }

        return list;
    }
    
        public ArrayList<VotVotequestsBean> listofDistintVoteSetup() {
        ArrayList<VotVotequestsBean> list = null;
        VotVotequestsBean votequestBean = null;

        String Sql = "select  distinct  agmid, vot_agm.description from vot_votequests inner join vot_agm on vot_agm.id=agmid where vot_votequests.active='N' and vot_votequests.deleted='N' ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotVotequestsBean>();
            }
            votequestBean = new VotVotequestsBean();

            Object[] row = results.next();
           
            votequestBean.setAgmid((int) row[i++]);
            //votequestBean.setCreatedby((String) row[i++]);
            votequestBean.setDescription((String) row[i++]);
           
            list.add(votequestBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the vote setup list " + list.size());
            
        }

        return list;
    }
        
    public ArrayList<VotVotequestsBean> listofDistintVoteSetupold() {
        ArrayList<VotVotequestsBean> list = null;
        VotVotequestsBean votequestBean = null;

        String Sql = "select  distinct  agmid, vot_votequests.createdby, vot_agm.description from vot_votequests inner join vot_agm on vot_agm.id=agmid where vot_votequests.active='N' and vot_votequests.deleted='N' ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotVotequestsBean>();
            }
            votequestBean = new VotVotequestsBean();

            Object[] row = results.next();
           
            votequestBean.setAgmid((int) row[i++]);
            votequestBean.setCreatedby((String) row[i++]);
            votequestBean.setDescription((String) row[i++]);
           
            list.add(votequestBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the vote setup list " + list.size());
            
        }

        return list;
    }    
        
   public void updateVotesetupQuestion(String question, String modifiedby, String modifieddate, int id){
    String sql= "update vot_votequests set description='"+question+"',modifiedby='"+modifiedby+"', modifieddate='"+modifieddate+"' where id='"+id+"'";
     sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
   }
   public void updateVoteSetupOptions( String options, String optionid){
    String sql= "update vot_voteoptions set description='"+options+"' where id ='"+optionid+"'";
     sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
   }
    public ArrayList<VotVotequestsBean> listVoteidByAgmid(int agmid) {
        ArrayList<VotVotequestsBean> list = null;
        VotVotequestsBean votVotequestsBean = null;

        String Sql = "SELECT id  from vot_votequests where agmid='"+agmid+"' and active='N' ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotVotequestsBean>();
            }
           votVotequestsBean = new VotVotequestsBean();

            Object row = results.next();
            votVotequestsBean.setId((Integer) row);

            list.add(votVotequestsBean);

        }
        if (list != null && !list.isEmpty()) {
            System.out.println("the id list size " + list.size());
           
        }

        return list;
    }
  
    
   public void deleteQuestions(int id){   
    String sql = "delete a.*,b.* from vot_votequests a inner join vot_voteoptions b on b.voteid=a.id where a.id='"+id+"' and b.voteid='"+id+"'";
    sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
   
   } 
   

   public void approveVoteSetup(int questionid){
    String sql = "update vot_votequests set active='Y' where id='"+questionid+"'";
        sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
   }
  
   public void updateVotFileuploadRec(int id,int failed,int success){
    String sql= "update vot_fileupload set failed_count='"+failed+"',success_count='"+success+"'where id='"+id+"'";
     sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
   }
   
   public boolean membersEmailValidity(String email,int agmid) {
          boolean validity =false;
        //List list = null;
       // VotMembersBean votMembersBean = null;
       
        String Sql = "SELECT email from vot_members where agmid='"+agmid+"' and email='"+email+"'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        List list = query.list();
         if (list.size()>0) {
               System.out.println(" size of the list "+list.size() );
               validity=true;
            }
            else {
             validity=false;
            }
        
        
        /*
         * 
        ArrayList<VotMembersBean> list = null;
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        
        while (results.hasNext()) {
            i = 0;
             System.out.println(" size of the list before the if statement "+list.size() );
            if (list.size()>0) {
               System.out.println(" size of the list "+list.size() );
               validity=true;
            }
            else {
             validity=false;
            }
          
        }
        */
        return validity;
       
       
    }
   
   
   
   /***public boolean membersEmailValidity(String email,int agmid) {
          boolean defaultmailValue=false;
        ArrayList<VotMembersBean> list = null;
        VotMembersBean votMembersBean = null;
       
        String Sql = "SELECT userid,email from vot_members where agmid='"+agmid+"' and email='"+email+"'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(Sql);
        Iterator<Object[]> results = query.list().iterator();
        int i = 0;

        while (results.hasNext()) {
            i = 0;
            if (list == null) {
                list = new ArrayList<VotMembersBean>();
            }
           votMembersBean = new VotMembersBean();

            Object[] row = results.next();
            votMembersBean.setUserid((String) row[i++]);
            votMembersBean.setEmail((String) row[i++]);
            

            list.add(votMembersBean);

        }
        
            if(list.size()>0){
            System.out.println("the member list size " + list.size());
            return true;
            }
        
      
        return defaultmailValue;
       
    } ***/
   public void moveSuccessfulVotMembers(String succesref_file){  
   String sql_query ="INSERT into vot_members(memberrefid,userid,firstname,middlename,surname,email,phone,branchid,companyid,agmid,concluded,voteunits)"
       + "SELECT memberrefid, userid, firstname, middlename, surname, email, phone, branchid, companyid, agmid, concluded, voteunits "
       + "from vot_members_file where file_ref='"+succesref_file+"'";
       sessionFactory.getCurrentSession().createSQLQuery(sql_query).executeUpdate();   
  }
      
      
      public void deactivateAgm(String agmid,String createdby,String createdate){
      String sql =" INSERT into vot_deactivateagm(agmid,description)SELECT id,description from vot_agm  where  id="+agmid ;
      sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
       String sql2 =" update vot_deactivateagm set createdby='"+createdby+"',createdate='"+createdate+"' where  agmid="+agmid ;
      sessionFactory.getCurrentSession().createSQLQuery(sql2).executeUpdate();
   }
       public void approveAgmDeactivation(String agmid, String approvedby){
      String sql ="update vot_agm set closed='Y' where id='"+agmid+"'";
      sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
       String sql1 ="update vot_deactivateagm set status=1 ,approvedby='"+approvedby+"' where agmid='"+agmid+"'";
      sessionFactory.getCurrentSession().createSQLQuery(sql1).executeUpdate();
   }
   public boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }
   public void updateVotMembMailRecord(int id,String newemail,String oldemail){
     String query ="update vot_emailchange set newemail='"+newemail+"',status=0 where agmid='"+id+"' and oldemail='"+oldemail+"'";
      sessionFactory.getCurrentSession().createSQLQuery(query).executeUpdate();   
   }
  public void approveVotMemberEmail(String id,String agmid,String approvedby,String newemail,String oldemail){
   String query1 ="update vot_emailchange set approvedby='"+approvedby+"',status=1 where id='"+id+"'";
      sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();   
   String query2 ="update vot_members set userid='"+newemail+"',email='"+newemail+"' where agmid='"+agmid+"' and email='"+oldemail+"'";
      sessionFactory.getCurrentSession().createSQLQuery(query2).executeUpdate();   
  }
   public Integer retrievenoofvotesbystatus(int agmid,String appstatus) {
         int noofvotes = 0;
        //Y - approved, N = non approved, A = all votes
        noofvotes = votequestionsDao.returnNoVotequestionsbystatus(agmid, appstatus);
        return noofvotes;
   }
   
}