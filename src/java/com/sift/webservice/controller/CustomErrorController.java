/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.controller;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Olakunle Awotunbo
 */
@Controller
public class CustomErrorController {
 // http://blog.codeleak.pl/2013/04/how-to-custom-error-pages-in-tomcat.html
 
    
    @RequestMapping(value="/error", produces="application/json")
    @ResponseBody
    public ModelAndView handle(HttpServletRequest request) {
        /*
        ModelMap model = new ModelMap();
        model.addAttribute("status", request.getAttribute("javax.servlet.error.status_code"));
        model.addAttribute("reason", request.getAttribute("javax.servlet.error.message"));
        */
        //System.out.println("Status : " + request.getAttribute("javax.servlet.error.status_code"));
        //System.out.println("Reason : " + request.getAttribute("javax.servlet.error.message"));
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));
        //map.put("url", request.getRequestURL());

        return new ModelAndView("/error", map);
    }
}