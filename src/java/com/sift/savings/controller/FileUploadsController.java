/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.savings.controller;

import com.sift.easycoopfin.models.Custaccountdetails;
import com.sift.easycoopfin.models.CustaccountdetailsCriteria;
import com.sift.easycoopfin.models.FileMeta;
import com.sift.easycoopfin.models.FileUpload;
import com.sift.easycoopfin.models.Member;
import com.sift.easycoopfin.models.MemberCriteria;
import com.sift.easycoopfin.models.Products;
import com.sift.easycoopfin.models.ProductsCriteria;
import com.sift.easycoopfin.models.Savings;
import com.sift.easycoopfin.models.SavingsError;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.Account;
import com.sift.gl.model.Users;
import com.sift.savings.service.SavingService;
import com.sift.savings.utility.EasyCoopFinValidator;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author logzy
 */
public class FileUploadsController {

    LinkedList<FileMeta> files = new LinkedList<FileMeta>();
    FileMeta fileMeta = null;
    private static final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(SavingsController.class);
    @Autowired
    private SavingService savingService;
    @Autowired
    private CurrentuserdisplayImpl user;

    public void setUser(CurrentuserdisplayImpl user) {
        this.user.setdCurruser(user.getCurruser()); //= user;
    }

    @RequestMapping(value = "/verifyuploads", method =
            RequestMethod.GET)
    public ModelAndView listSavings(ModelMap model) {

        int dbranch = user.getCurruser().getBranch();
        int dcompany = user.getCurruser().getCompanyid();
        List<FileUpload> uploads = savingService.listUnVerifiedUploads(dbranch,dcompany);
        for(FileUpload u: uploads){
            System.out.println("Name"+u.getFileName()+" Verified"+u.getVerifierId());
        }
        model.addAttribute("uploads", uploads);

        return new ModelAndView("verifyuploads", "uploads", uploads);
    }
}
