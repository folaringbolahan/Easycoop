/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.agm.controller;

import com.sift.agm.service.AgmService;
import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.VoteAccessQuestions;
import com.sift.easycoopfin.models.Voteoptions;
import com.sift.easycoopfin.models.Voteresults;
import com.sift.easycoopfin.models.Votes;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author logzy
 */
@Controller
@RequestMapping(value = "/vote")
public class VoteAccessQuestionController {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(VoteAccessQuestionController.class);
    @Autowired
    private AgmService agmService;

    @RequestMapping(value = "/question", method = RequestMethod.GET)
    public ModelAndView viewVotesQuestions(ModelMap model) {

        List<VoteAccessQuestions> voteQuestions = agmService.getQuestions();
        for (VoteAccessQuestions q : voteQuestions) {
            System.out.println("Code: " + q.getCode() + "\n");
        }
        return new ModelAndView("voteQuestions", "voteQuestions", voteQuestions);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listVotes(ModelMap model,HttpServletRequest request) {

         // request.getSession().setAttribute("isLoggedIn", null);
        if(request.getSession().getAttribute("isLoggedIn")==null){
         
        return new ModelAndView("redirect:/views/vote/question");
        }else{
            int dcompany = (Integer)request.getSession().getAttribute("voteMemberCompanyId");
        System.out.println("CompanyId:"+dcompany);
        List<Votes> votes = agmService.listVotes(dcompany);
        model.put("companyId",dcompany);
        return new ModelAndView("votes", "votes", votes);
        }
    }

    @RequestMapping(value = "/getcompanies", method = RequestMethod.GET)
    public @ResponseBody
    List<Company> getCompanies(@RequestParam String name) {

        return agmService.getCompanies(name);

    }

    @RequestMapping(value = "/castvote", method = RequestMethod.GET)
    public @ResponseBody
    String addAttendees(@RequestParam("voteId") String voteId, @RequestParam("voteOptionId") String voteOptionId, HttpServletRequest request) {
        int memberId = (Integer) request.getSession().getAttribute("voteMemberId");
        Voteresults result = new Voteresults();
        result.setVoteId(Integer.valueOf(voteId));
        result.setVoteOptionId(Integer.valueOf(voteOptionId));
        result.setMemberId(memberId);
        return agmService.castVote(result);

    }

    @RequestMapping(value = "/getaccess", method = RequestMethod.GET)
    public @ResponseBody
    String getAccess(@RequestParam("question1") String question1,
            @RequestParam("question2") String question2,
            @RequestParam("question3") String question3,
            @RequestParam("question4") String question4,
            @RequestParam("code1") String code1,
            @RequestParam("code2") String code2,
            @RequestParam("code3") String code3,
            @RequestParam("code4") String code4,
            @RequestParam("companyId") int companyId, HttpServletRequest request) throws PersistentException {

        return agmService.validateMember(question1, question2, question3, question4, code1, code2, code3, code4, companyId, request);


    }

    @RequestMapping(value = "/getoptions", method = RequestMethod.GET)
    public @ResponseBody
    List<Voteoptions> getOptions(
            @RequestParam int id) {


        return agmService.listVoteOption(id);
    }
}
