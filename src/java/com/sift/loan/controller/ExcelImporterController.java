package com.sift.loan.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.sift.loan.bean.FileBean;
import com.sift.loan.service.ImportService;
import com.sift.loan.service.LoanDisbursementService;

@Controller
@RequestMapping("/upload")
public class ExcelImporterController{
	
    @Autowired
    private ImportService importService;
	
    @RequestMapping(method = RequestMethod.POST)
    public String upload(FileBean uploadItem, BindingResult result){
    	importService.Import(uploadItem);
        return "import/importDone";
    }
}