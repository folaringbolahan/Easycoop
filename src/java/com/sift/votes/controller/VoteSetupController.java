/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.controller;

import com.sift.admin.bean.UserBean;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.votes.bean.VotAgmBean;
import com.sift.votes.bean.VotQuestAndOptionsBean;
import com.sift.votes.bean.VotVoteoptionsBean;
import com.sift.votes.bean.VotVotequestsBean;
import com.sift.votes.model.Activitylog;
import com.sift.votes.model.VotAgm;
import com.sift.votes.model.VotResolanswertypes;
import com.sift.votes.model.VotVoteoptions;
import com.sift.votes.model.VotVotequests;
import com.sift.votes.service.VotAgmService;
import com.sift.votes.service.VotElectionanswertypesService;
import com.sift.votes.service.VotResolanswertypesService;
import com.sift.votes.service.VotVoteoptionsService;
import com.sift.votes.service.VotVotequestsService;
import com.sift.votes.service.VotVotetypesService;
import com.sift.votes.service.AuditlogService;
import com.sift.votes.service.VoterNotificationService;
import com.sift.votes.service.VotersnotificationServiceImpl;
import com.sift.votes.utility.AgmHelperUtility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Nelson Akpos
 */
@Controller
public class VoteSetupController {

    @Autowired
    private VotAgmService votAgmService;
    @Autowired
    private VotVotetypesService votVotetypesService;
    @Autowired
    private VotElectionanswertypesService votElectionanswertypesService;
    @Autowired
    private VotVotequestsService votVotequestsService;
    @Autowired
    private VotResolanswertypesService votResolanswertypesService;
    @Autowired
    private VotVoteoptionsService votVoteoptionsService;
    @Autowired
    private CurrentuserdisplayImpl user;
    @Autowired
    private AgmHelperUtility agmhelperUTIL;
    @Autowired
    private VoterNotificationService voterNotificationService;

    @RequestMapping(value = "/voteSetup", method = RequestMethod.GET)
    public ModelAndView voteSetup(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("allagms", votAgmService.listInactiveAgms());
        model.put("votetypes", votVotetypesService.listallvotetypes());
        model.put("answertypes", votElectionanswertypesService.listallanswertypes());
        return new ModelAndView("addVoteSetup", model);
    }

    @RequestMapping(value = "/resolutionOptions", method = RequestMethod.GET)
    public ModelAndView voteQuestionOptions(@ModelAttribute("votQuest") VotVoteoptionsBean votVoteOptionsBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("resolutions", votResolanswertypesService.listresolanswertypes());
        model.put("votquest", votVotequestsService.getCurrentVotequestRecord());
        return new ModelAndView("addOptions", model);
    }

    @RequestMapping(value = "/options", method = RequestMethod.POST)
    public ModelAndView Options(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        String votetype = req.getParameter("votetypeid");
        String voteoptions = req.getParameter("voteoptions");
        
        VotVotequests votVotequests = prepareVotquestModel(votVotequestsBean);
        votVotequestsService.save(votVotequests);
        Activitylog activity = new Activitylog();
        activity.setEvent(139);
        activity.setAction("CRE-VOT");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID : " + "created vote question" );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(votVotequestsBean.getAgmid());
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        if ("2".equals(votetype)) {
            return new ModelAndView("redirect:/resolutionOptions");
        }
        return new ModelAndView("redirect:/electionOptions?options=" + voteoptions);
    }

    @RequestMapping(value = "/electionOptions", method = RequestMethod.GET)
    public ModelAndView electionOptions(@ModelAttribute("votQuest") VotVoteoptionsBean votVoteOptionsBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("votquest", votVotequestsService.getCurrentVotequestRecord());

        return new ModelAndView("addElectionOptions", model);
    }

    @RequestMapping(value = "/electionopts", method = RequestMethod.POST)
    public ModelAndView electionopts(@ModelAttribute("votOptions") VotVoteoptionsBean votVoteoptionsBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String[] description = req.getParameterValues("description");
        String agmid= req.getParameter("agmid");
        System.out.println("Size of the voteid " + description.length);
        String redurlpath = "";
        for (int i = 0; i < description.length; i++) {
            try {

                VotVoteoptions votVoteoptions = prepareVotvoteoptionsModel(votVoteoptionsBean);
                votVoteoptions.setDescription(description[i]);
                votVoteoptionsService.save(votVoteoptions);
                   Activitylog activity = new Activitylog();
        activity.setEvent(141);
        activity.setAction("CRE-VOT-OPT");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("Vote setup for election: " +description[i] );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(Integer.parseInt(agmid));
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        redurlpath = "redirect:/doFeedback.htm?message=successful vote setup&redirectURI=voteSetup";
        return new ModelAndView(redurlpath);
    }

    @RequestMapping(value = "/resolopts", method = RequestMethod.POST)
    public ModelAndView resolOptions(@ModelAttribute("votOptions") VotVoteoptionsBean votVoteoptionsBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();

        String[] description = req.getParameterValues("description");
        String agmid= req.getParameter("agmid");
        System.out.println("Size of the description " + description.length);
        String redurlpath = "";
        for (int i = 0; i < description.length; i++) {
            try {

                VotVoteoptions votVoteoptions = prepareVotvoteoptionsModel(votVoteoptionsBean);

                votVoteoptions.setDescription(description[i]);
                votVoteoptionsService.save(votVoteoptions);
         Activitylog activity = new Activitylog();
        activity.setEvent(141);
        activity.setAction("CRE-VOT-OPT");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("Vote setup for reoluton: " +description[i] );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(Integer.parseInt(agmid));
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }


        redurlpath = "redirect:/doFeedback.htm?message=successful vote setup&redirectURI=voteSetup";
        return new ModelAndView(redurlpath);
    }

    @RequestMapping(value = "/editQuestions", method = RequestMethod.GET)
    public ModelAndView editQuestions(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result, HttpServletRequest req) {

        Map<String, Object> model = new HashMap<String, Object>();
        System.out.println("agm id is " + votVotequestsBean.getId());

        model.put("listquest", votVotequestsService.listVotequestionsByAgmId(votVotequestsBean.getId()));
        return new ModelAndView("editQuestions", model);
    }

    @RequestMapping(value = "/questPage", method = RequestMethod.GET)
    public ModelAndView editVoteQuestions(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result, HttpServletRequest req) {

        Map<String, Object> model = new HashMap<String, Object>();
        System.out.println("id is " + votVotequestsBean.getId());
        //get question by id
        model.put("question", votVotequestsService.getQuestionById(votVotequestsBean.getId()));
        //get list of options by is
        model.put("options", votVoteoptionsService.listOptionsById(votVotequestsBean.getId()));
        return new ModelAndView("questPage", model);
    }

    @RequestMapping(value = "/editVoteSetup", method = RequestMethod.GET)
    public ModelAndView editVoteSetup(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        //model.put("votquestions",prepareListofVotequestsBean(votVotequestsService.listvotequestions()));
        model.put("agmList", agmhelperUTIL.listofDistintVoteSetup());
        return new ModelAndView("editVoteSetup", model);
    }

    @RequestMapping(value = "/updateVoteSetup", method = RequestMethod.POST)
    public ModelAndView updateVoteSetup(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result, HttpServletRequest req) {
        String redurlpath = "";
        String question = req.getParameter("question");
        String[] options = req.getParameterValues("description");
        String modifiedby = req.getParameter("modifiedby");
        String modifieddate = req.getParameter("modifieddate");
        Map<String, Object> model = new HashMap<String, Object>();
        String setupid = req.getParameter("id");
        int votequestid = Integer.parseInt(setupid);
        String[] optionid = req.getParameterValues("optionid");
        //update question
        agmhelperUTIL.updateVotesetupQuestion(question, modifiedby, modifieddate, votequestid);
         Activitylog activity = new Activitylog();
        activity.setEvent(140);
        activity.setAction("EDT-VOT");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID: " + question);
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(votVotequestsBean.getAgmid());
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
        //update options
        for (int i = 0; i < options.length; i++) {
            try {

                agmhelperUTIL.updateVoteSetupOptions(options[i], optionid[i]);
        Activitylog optActivity = new Activitylog();
        optActivity.setEvent(142);
        optActivity.setAction("EDT-VOT-OPT");
        optActivity.setActionDate(new java.util.Date());
        optActivity.setActionItem("ID: " + options[i]);
        optActivity.setActionResult("Success : " + user.getCurruser().getUserId());
        optActivity.setDescription("");
        optActivity.setIpaddress(req.getRemoteHost());
        optActivity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        optActivity.setToDate("");
        optActivity.setCompanyid(user.getCurruser().getCompanyid());
        optActivity.setAgmid(votVotequestsBean.getAgmid());
        
        AuditlogService asOpt = new AuditlogService();
        asOpt.create(optActivity);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        redurlpath = "redirect:/doFeedback.htm?message=update is successful&redirectURI=editVoteSetup";
        return new ModelAndView(redurlpath);
    }

    @RequestMapping(value = "/voteSetupApproval", method = RequestMethod.GET)
    public ModelAndView voteSetupApproval(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("agmList", agmhelperUTIL.listofDistintVoteSetup());
        return new ModelAndView("voteSetupApprv", model);
    }

    @RequestMapping(value = "/viewVoteSetup", method = RequestMethod.GET)
    public ModelAndView viewVoteSetup(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result) {
        Map<String, Object> model = new HashMap<String, Object>();
      
        for (int i = 0; i < agmhelperUTIL.listVoteidByAgmid(votVotequestsBean.getId()).size(); i++) { //fixe this error and do the front job and deploy
           
            model.put("setup", listQuestionsAndOptions(votVotequestsBean.getId()));
        }
        return new ModelAndView("apprVoteSetup", model);
    }

    @RequestMapping(value = "/deleteQuestions", method = RequestMethod.GET)
    public ModelAndView deleteQuestions(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result) {
        String redurlpath = "";
        Map<String, Object> model = new HashMap<String, Object>();
        agmhelperUTIL.deleteQuestions(votVotequestsBean.getId());
        redurlpath = "redirect:/doFeedback.htm?message= Vote Question Deleted Successfully&redirectURI=editVoteSetup";
        return new ModelAndView(redurlpath);

    }
    @RequestMapping(value = "/approveVoteSetup", method = RequestMethod.POST)
    public ModelAndView approveVoteSetup(@ModelAttribute("votQuest") VotVotequestsBean votVotequestsBean, BindingResult result, HttpServletRequest req) {
        String redurlpath="";
        String id= req.getParameter("agmid");
        String[] questid= req.getParameterValues("id");
        System.out.println("agmid "+ id);
        int agmid = Integer.parseInt(id);
        for(int i=0; i<questid.length; i++){
            agmhelperUTIL.approveVoteSetup(Integer.parseInt(questid[i]));
        }
        int noofvotesnewlyappr = questid.length;
        int noofvotesapprcum = agmhelperUTIL.retrievenoofvotesbystatus(agmid,"Y");
        int noofvotesnotappr = agmhelperUTIL.retrievenoofvotesbystatus(agmid,"N");
        int noofvotesall = agmhelperUTIL.retrievenoofvotesbystatus(agmid,"A");
        
        String  agmName=votAgmService.getAgmById(agmid).getDescription();
        
         Map model = new HashMap();
         model.put("noofvotesnewlyappr", noofvotesnewlyappr);
         model.put("noofvotesapprcum", noofvotesapprcum);
         model.put("noofvotesnotappr", noofvotesnotappr);
         model.put("noofvotesall", noofvotesall);
         model.put("agm", agmName);
          List<UserBean> mails= agmhelperUTIL.getAdminMails();
          String admin1= mails.get(0).getEmail().toString();
          String admin2= mails.get(1).getEmail().toString();
          VotersnotificationServiceImpl vnotserv = new VotersnotificationServiceImpl();
          //VotersnotificationServiceImpl vnotserv2 = new VotersnotificationServiceImpl();
         //boolean res = vnotserv.firenotification(voterNotificationService,"votesactivated.ftl",model,admin1+";"+admin2, "E-voting - Votes Approval notice");
         boolean res2 = vnotserv.firenotification(voterNotificationService,"votesactivated.ftl",model,admin2, "E-voting - Votes Approval notice");
          try{
           Thread.sleep(1000); //wait 1 min for Email server response
          }
          catch(InterruptedException iex){
            System.out.println("Pausing Exception..."+ iex.getMessage() );  
          }      
         res2 = vnotserv.firenotification(voterNotificationService,"votesactivated.ftl",model,admin1, "E-voting - Votes Approval notice");
        
        System.out.println("agm name is "+ agmName);
         Activitylog activity = new Activitylog();
        activity.setEvent(148);
        activity.setAction("APRV-VOTES");
        activity.setActionDate(new java.util.Date());
        activity.setActionItem("ID: " + agmName );
        activity.setActionResult("Success : " + user.getCurruser().getUserId());
        activity.setDescription("");
        activity.setIpaddress(req.getRemoteHost());
        activity.setUsername(req.getRemoteUser());
        //activity.setTimezone(helperUTIL.getTimeZoneGivenCompanyId(loanRequest.getCompanyId()));
        activity.setToDate("");
        activity.setCompanyid(user.getCurruser().getCompanyid());
        activity.setAgmid(Integer.parseInt(id));
        
        AuditlogService as = new AuditlogService();
        as.create(activity);
       redurlpath = "redirect:/doFeedback.htm?message= Vote Setup Approved&redirectURI=voteSetupApproval";
        return new ModelAndView(redurlpath);
    

    }

    private VotVotequests prepareVotquestModel(VotVotequestsBean votVotequestsBean) {
        VotVotequests votVotequests = new VotVotequests();

        votVotequests.setActive(votVotequestsBean.getActive());
        votVotequests.setAgmid(votVotequestsBean.getAgmid());
        votVotequests.setCreatedate(new java.util.Date());
        votVotequests.setCreatedby(votVotequestsBean.getCreatedby());
        votVotequests.setDeleted(votVotequestsBean.getDeleted());
        votVotequests.setDescription(votVotequestsBean.getDescription());
        votVotequests.setElectionanswertypeid(votVotequestsBean.getElectionanswertypeid());
        votVotequests.setSortorder(votVotequestsBean.getSortorder());
        votVotequests.setVotetypeid(votVotequestsBean.getVotetypeid());
        votVotequests.setId(votVotequestsBean.getId());
        votVotequests.setMaxoption(votVotequestsBean.getMaxoption());





        return votVotequests;

    }

    private VotVoteoptions prepareVotvoteoptionsModel(VotVoteoptionsBean votVoteoptionsBean) {
        VotVoteoptions votVoteoptions = new VotVoteoptions();
        votVoteoptions.setDeleted(votVoteoptionsBean.getDeleted());
        votVoteoptions.setDescription(votVoteoptionsBean.getDescription());
        votVoteoptions.setId(votVoteoptionsBean.getId());
        votVoteoptions.setVoteid(votVoteoptionsBean.getVoteid());
        return votVoteoptions;

    }

    private List<VotVotequestsBean> prepareListofVotequestsBean(List<VotVotequests> votVotequests) {
        List<VotVotequestsBean> beans = null;

        if (votVotequests != null && !votVotequests.isEmpty()) {
            beans = new ArrayList<VotVotequestsBean>();
            VotVotequestsBean bean = null;

            for (VotVotequests votVotequest : votVotequests) {
                bean = new VotVotequestsBean();
                bean.setActive(votVotequest.getActive());
                bean.setDescription(votAgmService.getAgmById(votVotequest.getAgmid()).getDescription());
                bean.setAgmid(votVotequest.getAgmid());
                bean.setDescription(votVotequest.getDescription());
                bean.setId(votVotequest.getId());
                bean.setVoteType(votVotetypesService.getVoteTypeByid(votVotequest.getVotetypeid()).getDescription());

                beans.add(bean);
            }
        }
        return beans;
    }

    private List<VotQuestAndOptionsBean> listQuestionsAndOptions(int agmid) {
        List<VotVotequestsBean> voteids = agmhelperUTIL.listVoteidByAgmid(agmid);
        List<VotQuestAndOptionsBean> list_out = new ArrayList<>();
        if (voteids != null && !voteids.isEmpty()) {

            for (VotVotequestsBean vb : voteids) {
                VotQuestAndOptionsBean votModel = new VotQuestAndOptionsBean();
                votModel.setQestionid(vb.getId());
                VotVotequests vvqt = votVotequestsService.getQuestionById(vb.getId());
                votModel.setQuestion(vvqt.getDescription());
                votModel.setVoteOptions(votVoteoptionsService.listOptionsById(vb.getId()));
                votModel.setCreatedby(vvqt.getCreatedby());
                votModel.setModifiedby(vvqt.getModifiedby());
                //get options by id
               
               // votModel.setVoteOptions(votVoteoptionsService.getVotVoteoptions(vb.getId()).getDescription());
                list_out.add(votModel);
            }
        }


        return list_out;

    }
}
