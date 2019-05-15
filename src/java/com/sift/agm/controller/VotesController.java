/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.agm.controller;

import com.sift.agm.service.AgmService;
import com.sift.easycoopfin.models.VoteAccessQuestions;
import com.sift.easycoopfin.models.Voteoptions;
import com.sift.easycoopfin.models.VoteoptionsCriteria;
import com.sift.easycoopfin.models.Voteresults;
import com.sift.easycoopfin.models.Votes;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author logzy
 */
@Controller
@RequestMapping(value = "/votes")
public class VotesController {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AgmController.class);
    @Autowired
    private AgmService agmService;
    @Autowired
    private CurrentuserdisplayImpl user;

    //@Autowired
    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView viewVotes(ModelMap model,HttpServletRequest request) {
       
       // request.getSession().setAttribute("isLoggedIn", null);
        if(request.getSession().getAttribute("isLoggedIn")==null){
         
        return new ModelAndView("redirect:/views/vote/question");
        }else{
            int dcompany = (Integer)request.getSession().getAttribute("voteMemberCompanyId");
        System.out.println("CompanyId:"+dcompany);
        List<Votes> votes = agmService.listVotes(dcompany);
        return new ModelAndView("votelist", "votes", votes);
        }
    }
@RequestMapping(value = "/setup/{agmId}", method = RequestMethod.GET)
    public ModelAndView setUpVote(@PathVariable("agmId") Integer agmId, ModelMap model) {
        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        Votes voteForm = new Votes();
        List<Votes> votes = agmService.listVotes(dcompany);
      
        Voteoptions optionsForm = new Voteoptions();

        
        voteForm.setAgmId(agmId);
        model.put("voteForm", voteForm);
        model.put("optionsForm", optionsForm);
        model.put("agmId", agmId);
        model.put("rvotes", votes);


        return new ModelAndView("votesetup", "votes", voteForm);
    }
    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Votes edit(
            @RequestBody Votes vote,
            ModelMap model) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        vote.setCompanyId(dcompany);
        return agmService.saveVote(vote);



    }
    @RequestMapping(value = "/editOptions", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Voteoptions editOptions(
            @RequestBody Voteoptions options,
            ModelMap model){
        return agmService.saveOptions(options);
    }
    
      @RequestMapping(value = "/getoptions", method = RequestMethod.GET) public
      @ResponseBody List<Voteoptions> getOptions(
      @RequestParam int id) {
     
   
      return agmService.listVoteOption(id); 
      }
     
      @RequestMapping(value = "/castvote1", method = RequestMethod.POST,
              consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
      public String castVote(@RequestBody Voteresults result,
            ModelMap model){
          return agmService.castVote(result);
      }
      
      @RequestMapping(value = "/castvote", method = RequestMethod.GET)
    public @ResponseBody
    String addAttendees(@RequestParam("voteId") String voteId, @RequestParam("voteOptionId") String voteOptionId) {
          Voteresults result = new Voteresults();
          result.setVoteId(Integer.valueOf(voteId));
          result.setVoteOptionId(Integer.valueOf(voteOptionId));
          return agmService.castVote(result);
        
    }
}
