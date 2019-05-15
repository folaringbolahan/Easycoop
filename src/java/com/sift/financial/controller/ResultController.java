package com.sift.financial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.HashMap;

import com.sift.financial.*;


@Controller

public class ResultController {

	@RequestMapping(value="/finResult/result", method = RequestMethod.GET)
	public ModelAndView doFeedback(@ModelAttribute("result")Result result, BindingResult bindResult)
	{
		Map<String ,Object> model = new HashMap<String, Object>();
		model.put("result", result);

		return new ModelAndView("/financial/result", model);
	 } 

}
