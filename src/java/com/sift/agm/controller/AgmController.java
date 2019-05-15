/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.agm.controller;

import com.sift.agm.service.AgmService;
import com.sift.easycoopfin.models.AgmProxy;
import com.sift.easycoopfin.models.Agmattendees;
import com.sift.easycoopfin.models.Agms;
import com.sift.easycoopfin.models.AgmsCriteria;
import com.sift.easycoopfin.models.Company;
import com.sift.easycoopfin.models.Member;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping(value = "/agm")
public class AgmController {

    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(AgmController.class);
    @Autowired
    private AgmService agmService;
    @Autowired
    private CurrentuserdisplayImpl user;

    //@Autowired
    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView showAgmForm(ModelMap model) {

        Agms agmForm = new Agms();
        model.put("agmForm", agmForm);
        List<Agms> agmList = null;
        try {

            int dbranch = user.getCurruser().getBranch();
            int companyId = user.getCurruser().getCompanyid();
            model.put("companyId", companyId);
            AgmsCriteria criteria = new AgmsCriteria();
            criteria.add(Restrictions.eq("companyId", companyId));
            agmList = com.sift.easycoopfin.models.DAOFactory.getDAOFactory().getAgmsDAO().listAllAgmsByCriteria(criteria);
            model.put("agms", agmList);

        } catch (Exception ex) {
            _logger.error("showAgmForm(ModelMap model)", ex);
        }


        //return "product";
        return new ModelAndView("addAgm", "agm", agmForm);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Agms edit(
            @RequestBody Agms agm,
            ModelMap model) {


        return agmService.save(agm);


    }
  @RequestMapping(value = "/addproxy", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AgmProxy edit(
            @RequestBody AgmProxy agmProxy,
            ModelMap model) {


        return agmService.save(agmProxy);


    }
   @RequestMapping(value = "/getcompanies", method = RequestMethod.GET)
        public @ResponseBody
        List<Company> getCompanies
        (@RequestParam
        String name
        
            
            ) {

        return agmService.getCompanies(name);
            
        }
    @RequestMapping(value = "/search", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public 
    List<Member> search(HttpServletRequest request,
            ModelMap model) {
        String condition = null;
        String orCondintion = null;
        int dbranch = user.getCurruser().getBranch();
        StringBuilder query = new StringBuilder();

        if (!request.getParameter("searchName").equals("")) {
            query.append("m.firstName like '%" + request.getParameter("searchName") + "%' and m.branchId=" + dbranch);
        }
        /**
         * if (!request.getParameter("searchName").equals("") &&
         * !request.getParameter("searcMemberNo").equals("") &&
         * request.getParameter("searchPhone").equals("") &&
         * !request.getParameter("searchEmail").equals("")) {
         * query.append("Member.firstName like '%" + dbranch + "%' or
         * Member.memberNo like '%" + request.getParameter("searcMemberNo") +
         * "%' and Member.branchId=" + dbranch);
        }*
         */
        condition = query.toString();
        return agmService.searchMembers(condition);


    }

    @RequestMapping(value = "/viewmembers", method = RequestMethod.GET)
    public @ResponseBody
    String viewMembers(@RequestParam("id") String id) {
        List<Agmattendees> participants = null;
        StringBuilder html = new StringBuilder();
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            System.out.println("company: " + dcompany);
            participants = agmService.viewMembers(Integer.valueOf(id));

            for (Agmattendees m : participants) {

                html.append("<tr><td>"+m.getId() +"</td>");
                html.append("<td>" + m.getParticipantName() + "</td>");
               

                html.append("<td class=\"table-action\"><a id=\"deleteaccount-" + m.getId() + "\" href=\"#\" data-id=\"" + m.getId() + "\"  data-toggle=\"tooltip\" title=\"Delete\" class=\"delete-row tooltips deleteaccount\"><i class=\"fa fa-trash-o\"></i></a> </td></tr>");



            }
            System.out.println(html.toString());
        } catch (Exception ex) {
            _logger.error("getMembers error ", ex);
        }

        return html.toString();
    }

    @RequestMapping(value = "/getallmembers", method = RequestMethod.GET)
    public @ResponseBody
    String getAllMembers() {

        List<Member> members = null;
        StringBuilder html = new StringBuilder();
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            System.out.println("company: " + dcompany);
            members = agmService.listMembers(dcompany);

            for (Member m : members) {

                html.append("<tr><td><input type=\"checkbox\" data-memberId=\"" + m.getId() + "\" class=\"checkboxes\" value=\"" + m.getId() + "\" /></td>");
                html.append("<td>" + m.getId() + "</td>");
                html.append("<td>" + m.getFirstName() + "</td>");
                html.append("<td>" + m.getMiddleName() + "</td>");
                html.append("<td>" + m.getSurname() + "</td>");
                html.append("<td>" + m.getGender() + "</td>");
                html.append("<td class=\"table-action\"><a id=\"deleteaccount-" + m.getId() + "\" href=\"#\" data-id=\"" + m.getId() + "\"  data-toggle=\"tooltip\" title=\"Delete\" class=\"delete-row tooltips deleteaccount\"><i class=\"fa fa-trash-o\"></i></a> </td></tr>");



            }
            System.out.println(html.toString());
        } catch (Exception ex) {
            _logger.error("getMembers error ", ex);
        }

        return html.toString();
    }

    @RequestMapping(value = "/getmembers", method = RequestMethod.GET)
    public @ResponseBody
    List<Member> getMembers() {

        List<Member> members = null;
        try {
            int dbranch = user.getCurruser().getBranch();
            int dcompany = user.getCurruser().getCompanyid();
            System.out.println("company: " + dcompany);
            members = agmService.listMembers(dcompany);

        } catch (Exception ex) {
            _logger.error("getMembers error ", ex);
        }

        return members;
    }

    @RequestMapping(value = "/addAttendees", method = RequestMethod.GET)
    public @ResponseBody
    String addAttendees(@RequestParam("ids") String ids, @RequestParam("agmId") String agmId) {

        agmService.addMembers(ids, agmId);
        return "ok";
    }
    @RequestMapping(value = "/addAttendee", method = RequestMethod.GET)
    public @ResponseBody
    String addAttendee(@RequestParam("id") int id, @RequestParam("agmId") int agmId){
       String message = "";
        if(agmService.addMember(id, agmId)){
           message=  "Operation Successful!. The selected user has been added successfully!";
        }else{
            message = "Operation Failed. Please try again";
        }
        return message;
    }
    @RequestMapping(value = "/deleteAttendees", method = RequestMethod.GET)
    public @ResponseBody
    String deleteAttendees(@RequestParam("ids") String ids, @RequestParam("agmId") String agmId) {

        agmService.deleteParticipant(ids, agmId);
        //agmService.removeMembers(ids, agmId);
        return "ok";
    }
}
