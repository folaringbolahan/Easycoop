/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.controller;

import com.sift.votes.bean.VotAgmDeactivationBean;
import com.sift.votes.model.VotAgmDeactivation;
import com.sift.votes.service.VotAgmDeactivationService;
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
public class AgmDeactivationController {
    @Autowired
    private AgmHelperUtility agmhelperUTIL;
    
    @Autowired
    VotAgmDeactivationService votAgmDeactivationService;
      @RequestMapping(value = "/agmDeactivation", method = RequestMethod.GET)
    public ModelAndView agmDeactivation (@ModelAttribute("votAgmDeactivation") VotAgmDeactivationBean votagmDeactivationBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
         model.put("pendingAgmDeactivation", prepareListofVotAgmDeactivationBean(votAgmDeactivationService.listAgmsforDeactivation()));
        return new ModelAndView("deactivateAgm", model); 

    }
      @RequestMapping(value = "/approveAgmDeactivation", method = RequestMethod.POST)
    public ModelAndView approveAgmDeactivation (@ModelAttribute("votAgm") VotAgmDeactivationBean votagmDeactivationBean, BindingResult result, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        String redurlpath="";
        String approvedby= req.getParameter("approvedby");
          String[] agmid=req.getParameterValues("agmid");
          for(int i=0; i<agmid.length; i++){
           agmhelperUTIL.approveAgmDeactivation(agmid[i],approvedby);
          }
        redurlpath = "redirect:/doFeedback.htm?message=Agm has been succesfully deactivated &redirectURI=agmDeactivation";
        return new ModelAndView(redurlpath);

    }
       private List<VotAgmDeactivationBean> prepareListofVotAgmDeactivationBean(List<VotAgmDeactivation> allvotAgmDeactivation) {
        List<VotAgmDeactivationBean> beans = null;
        System.out.println("i am here 2");
        if ((allvotAgmDeactivation != null) && (!allvotAgmDeactivation.isEmpty())) {
            beans = new ArrayList<VotAgmDeactivationBean>();
            VotAgmDeactivationBean bean = null;

            for (VotAgmDeactivation votagmdeactivation : allvotAgmDeactivation) {
                bean = new VotAgmDeactivationBean();
                bean.setId(votagmdeactivation.getId());
                bean.setAgmid(votagmdeactivation.getAgmid());
                bean.setDescription(votagmdeactivation.getDescription());
                bean.setCreatedate(votagmdeactivation.getCreatedate());
                bean.setCreatedby(votagmdeactivation.getCreatedby());   
               
                beans.add(bean);
            }
        }
          return beans;
       }
}
