package com.sift.admin.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sift.admin.bean.FeedbackBean;

/**
 * @author XTOFFEL CONSULT
 *
 */
@Controller
public class FeedbackController{

 @RequestMapping(value = "/doFeedback", method = RequestMethod.GET)
 public ModelAndView doFeedback(@ModelAttribute("feedback")FeedbackBean feedbackBean, BindingResult result){
	 Map<String ,Object> model = new HashMap<String, Object>();
	 
	 model.put("feedback", feedbackBean);
	 return new ModelAndView("appFeedback", model);
 } 
 
 @RequestMapping(value = "/doError", method = RequestMethod.GET)
 public ModelAndView doError(@ModelAttribute("feedback")FeedbackBean feedbackBean, BindingResult result){
	 Map<String ,Object> model = new HashMap<String, Object>();
	 
	 model.put("feedback", feedbackBean);
	 return new ModelAndView("appError", model);
 } 
}