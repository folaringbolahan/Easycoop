package com.sift.loan.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.sift.admin.bean.FiscalPeriodItemBean;
import com.sift.admin.interfaces.Definitions;
import com.sift.admin.model.Branch;
import com.sift.admin.model.Company;
import com.sift.admin.model.Country;
import com.sift.admin.model.LoanRepayFreq;
import com.sift.admin.model.MemberInfo;
import com.sift.admin.model.MemberView;
import com.sift.admin.model.User;
import com.sift.admin.model.UserGroup;
import com.sift.admin.service.BranchService;
import com.sift.admin.service.CompanyService;
import com.sift.admin.service.MemberViewService;
import com.sift.admin.service.UserService;
import com.sift.loan.bean.LoanPayOffBean;
import com.sift.loan.bean.LoanPayOffUploadBean;
import com.sift.loan.bean.LoanPayOffValidationBean;
import com.sift.loan.bean.LoanRepayUploadBean;
import com.sift.loan.bean.LoanRepayValidationBean;
import com.sift.loan.bean.LoanRepaymentScheduleBean;
import com.sift.loan.bean.LoanRequestBean;
import com.sift.loan.bean.RepaymentUploadItemsBean;
import com.sift.loan.model.BulkLoanRePayment;
import com.sift.loan.model.FileUpload;
import com.sift.loan.model.LoanPayOff;
import com.sift.loan.model.LoanPayOffUploadErrors;
import com.sift.loan.model.LoanPayOffUploadItems;
import com.sift.loan.model.LoanRepaymentSchedule;
import com.sift.loan.model.LoanRequest;
import com.sift.loan.model.Product;
import com.sift.loan.model.RepaymentUploadErrors;
import com.sift.loan.model.RepaymentUploadItems;
import com.sift.loan.service.BulkUploadErrorService;
import com.sift.loan.service.BulkUploadItemService;
import com.sift.loan.service.BulkUploadService;
import com.sift.loan.service.LoanPayOffUploadItemService;
import com.sift.loan.service.LoanRepaymentScheduleService;
import com.sift.loan.service.LoanRequestService;
import com.sift.loan.service.ProductService;
import com.sift.gl.*;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Activitylog;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.SMSBean;
import com.sift.gl.model.Txnsheader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EazyCoopUtility {

    @Autowired
    private SessionFactory sessionFactory;
    //@Autowired
    //private JndiObjectFactoryBean datasource; 
    @Autowired
    private UserService userService;
    @Autowired
    private HelperUtility helperUTIL;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private BulkUploadService bulkUploadService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberViewService memberService;
    @Autowired
    private LoanRequestService loanRequestService;
    @Autowired
    private LoanPayOffUploadItemService loanPayOffUploadItemService;
    @Autowired
    private BulkUploadItemService bulkUploadItemService;
    @Autowired
    private BulkUploadErrorService bulkUploadErrorService;
    @Autowired
    private LoanRepaymentScheduleService loanRepaymentScheduleService;
    @Autowired
    private Configuration freemarkerConfiguration;
    private TaskExecutor taskExecutor;
    private static WebResource webResource;
    private static Client client;
    private static String BASE_URI = "http://localhost:7070/EasycoopfinTEST3/webserv";
    private String dpwd;
    private String dname;
    String authenabled = "false";

    public static ClientResponse create_XML(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse create_JSON(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(ClientResponse.class, requestEntity);
    }

    public void close() {
        client.destroy();
    }

    public String getURI() {
        String uri = "";

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
            //BASE_URI = uri;            
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        return uri;
    }

    public String getReportLocation() {
        String reportlocation = "";

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            reportlocation = (String) ctx.lookup("java:comp/env/uploadreports");
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        return reportlocation;
    }

    public String getReportLocation(ServletContext context) {
        String reportlocation = "";

        try {
            reportlocation = context.getRealPath("/uploadreports/");
        } catch (Exception nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        return reportlocation;
    }

    public MailBean getMailConfig() {
        String host = "";
        String port = "";
        String sslenabled = "";
        String user = "";
        String pass = "";
        String from = "";
        String sslortls = "";

        MailBean MB = new MailBean();

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();

            host = (String) ctx.lookup("java:comp/env/mail.host");
            port = (String) ctx.lookup("java:comp/env/mail.port");
            sslenabled = (String) ctx.lookup("java:comp/env/mail.ssl.enabled");
            user = (String) ctx.lookup("java:comp/env/mail.user");
            pass = (String) ctx.lookup("java:comp/env/mail.pass");
            from = (String) ctx.lookup("java:comp/env/mail.sender");
            sslortls = (String) ctx.lookup("java:comp/env/mail.ssl.sslortls");
            authenabled = (String) ctx.lookup("java:comp/env/mail.auth.enabled");

            MB.setFrom(from);
            MB.setMailsmtphost(host);
            MB.setMailsmtpport(port);
            MB.setUserid(user);
            MB.setPassword(pass);
            MB.setSslortls(sslortls);

        } catch (Exception nx) {
            MB = null;
            nx.printStackTrace();
        }

        return MB;
    }
    BeanMapperUtility mapper = new BeanMapperUtility();

    public ArrayList<LoanRepaymentSchedule> generateLoanScheduleFlat(LoanRequestBean loanRequestBean, LoanRepayFreq repayFreq, java.util.Date startDate, String createdBy, java.util.Date creationDate) {
        String companyId = loanRequestBean.getCompanyId();
        String branchId = loanRequestBean.getBranchId();
        double Principal = loanRequestBean.getApprovedAmount();
        double rate = loanRequestBean.getAppliedRate();
        int installments = loanRequestBean.getNoOfInstallments();
        double duration = 0.0;
        double cummPrincipal = Principal;

        ArrayList<LoanRepaymentSchedule> list = null;
        LoanRepaymentSchedule schedule = null;

        //convert duration to year from days
        duration = getLoanDurationAsFractionOfYear(repayFreq, installments);
        double totPay = 0.0;

        double interest = Principal * (rate / 100.00) * (((double) installments) / (double) repayFreq.getYearDivisor());
        double annualInterest = interest;
        double interestPerInstallment = FormatTo2DecimalD(annualInterest / (double) installments);
        double principalPerInstallment = FormatTo2DecimalD(Principal / (double) installments);

        //java.util.Date expectedRepaymentDate = startDate;
        java.util.Date expectedRepaymentDate = getNextRepaymentDate(repayFreq, startDate);

        for (int k = 1; k <= installments; k++) {
            if (list == null) {
                list = new ArrayList<LoanRepaymentSchedule>();
            }

            if (k > 1) {
                expectedRepaymentDate = getNextRepaymentDate(repayFreq, expectedRepaymentDate);
            }

            schedule = new LoanRepaymentSchedule();

            /**
             * *************************************************************************************************
             * double PrincipalInstallments=Math.round((Principal/installments)
             * * 100.0)/100.0; double
             * InterestInstallments=Math.round((interest/installments)*
             * 100.0)/100.0; double
             * expectedRepaymentAmount=Math.round((PrincipalInstallments+InterestInstallments)*
             * 100.0)/100.0;
             * *************************************************************************************************
             */
            double PrincipalInstallments = principalPerInstallment;
            double InterestInstallments = interestPerInstallment;
            double expectedRepaymentAmount = principalPerInstallment + interestPerInstallment;
            cummPrincipal = cummPrincipal - PrincipalInstallments;

            if (k == installments) {//taking care of errors due to round off
                if (cummPrincipal > 0.0) {
                    expectedRepaymentAmount += cummPrincipal;
                    InterestInstallments += cummPrincipal;
                    cummPrincipal = 0.0;
                }
            }

            totPay += PrincipalInstallments;
            schedule.setAmountInterest(roundDouble(InterestInstallments, 2));
            schedule.setAmountPrincipal(roundDouble(PrincipalInstallments, 2));
            schedule.setExpectedRepaymentAmount(roundDouble(expectedRepaymentAmount, 2));
            schedule.setExpectedRepaymentDate(expectedRepaymentDate);
            schedule.setCompanyId(companyId);
            schedule.setBranchId(branchId);
            schedule.setCummPrincipal(roundDouble(cummPrincipal, 2));
            schedule.setActive("Y");
            schedule.setPaymentStatus("N");
            schedule.setMemberNo(loanRequestBean.getMemberNo());
            schedule.setLoanCaseId(loanRequestBean.getLoanCaseId());
            schedule.setCreatedBy(createdBy);
            schedule.setCreationDate(creationDate);

            //System.out.println("Principal:="+schedule.getAmountPrincipal() + "/Interest:="+ schedule.getAmountInterest() + "/EMI:=" + schedule.getExpectedRepaymentAmount() + "/Balance:=" +  Math.round((Principal-totPay)* 100.0)/100.0);
            list.add(schedule);
        }

        return list;
    }

    public ArrayList<LoanRepaymentSchedule> generateLoanScheduleFlatCoop(LoanRequestBean loanRequestBean, LoanRepayFreq repayFreq, java.util.Date startDate, String createdBy, java.util.Date creationDate) {
        String companyId = loanRequestBean.getCompanyId();
        String branchId = loanRequestBean.getBranchId();
        double Principal = loanRequestBean.getApprovedAmount();
        double rate = loanRequestBean.getAppliedRate();
        int installments = loanRequestBean.getNoOfInstallments();
        double duration = 0.0;
        double cummPrincipal = Principal;
        double sumOfPreviousInt = 0.0;
        double sumOfPreviousPrincipal = 0.0;
        double sumOfExpectedRptAmt = 0.0;

        ArrayList<LoanRepaymentSchedule> list = null;
        LoanRepaymentSchedule schedule = null;

        //convert duration to year from days
        duration = getLoanDurationAsFractionOfYear(repayFreq, installments);
        double totPay = 0.0;

        double interest = Principal * (rate / 100.00); // * (((double) installments) / (double) repayFreq.getYearDivisor());
        //double interest = calculateMonthlyPayment(Principal, installments, rate, duration);
        //System.out.println("Interest is : " + interest);
        double annualInterest = interest;
        //double interestPerInstallment = FormatTo2DecimalD(annualInterest / (double) installments);
        //double principalPerInstallment = FormatTo2DecimalD(Principal / (double) installments);
        //System.out.println("Annual Interest : " + annualInterest);
        //System.out.println("Duration : " + duration);
        double interestPerInstallment = FormatTo2DecimalD(annualInterest / (double) installments);
        double principalPerInstallment = FormatTo2DecimalD(Principal / (double) installments);

        //java.util.Date expectedRepaymentDate = startDate;
        java.util.Date expectedRepaymentDate = getNextRepaymentDate(repayFreq, startDate);

        for (int k = 1; k <= installments; k++) {
            if (list == null) {
                list = new ArrayList<LoanRepaymentSchedule>();
            }

            if (k > 1) {
                expectedRepaymentDate = getNextRepaymentDate(repayFreq, expectedRepaymentDate);
            }

            schedule = new LoanRepaymentSchedule();

            /**
             * *************************************************************************************************
             * double PrincipalInstallments=Math.round((Principal/installments)
             * * 100.0)/100.0; double
             * InterestInstallments=Math.round((interest/installments)*
             * 100.0)/100.0; double
             * expectedRepaymentAmount=Math.round((PrincipalInstallments+InterestInstallments)*
             * 100.0)/100.0;
             * *************************************************************************************************
             */
            double PrincipalInstallments = principalPerInstallment;
            double InterestInstallments = interestPerInstallment;
            double expectedRepaymentAmount = principalPerInstallment + interestPerInstallment;
            cummPrincipal = roundDouble((cummPrincipal - PrincipalInstallments), 2);
            //cummPrincipal = roundDouble(cummPrincipal,2) - roundDouble(PrincipalInstallments,2);
            sumOfPreviousInt += interestPerInstallment;
            sumOfPreviousPrincipal += PrincipalInstallments;
            if (k == installments) {//taking care of errors due to round off
                if (cummPrincipal > 0.0) {
                    expectedRepaymentAmount += cummPrincipal;
                    InterestInstallments += cummPrincipal;
                    cummPrincipal = 0.0;
                } else {
                    cummPrincipal = 0.0;
                }

                //System.out.println("K is equal to the last installment");
                //sum previous interest and subtract from annual interest for final interest
                //System.out.println("annualInterest :: "+ annualInterest + " sumOfPreviousInt :: " + sumOfPreviousInt);
                //InterestInstallments = roundDouble((annualInterest - (sumOfPreviousInt - interestPerInstallment)),2);
                if (annualInterest > (sumOfPreviousInt - interestPerInstallment)) {
                    InterestInstallments = roundDouble((annualInterest - (sumOfPreviousInt - interestPerInstallment)), 2);
                } else if (annualInterest < (sumOfPreviousInt - interestPerInstallment)) {
                    InterestInstallments = roundDouble(((sumOfPreviousInt - interestPerInstallment) - annualInterest), 2);
                } else {
                    InterestInstallments = interestPerInstallment;
                }

                //sum previous principal and subtract from total principal for final principalInstallment
                //System.out.println("Actual Principal :: " + Principal +  " Sum of previous principal :: " + sumOfPreviousPrincipal);
                //PrincipalInstallments = roundDouble((Principal - (sumOfPreviousPrincipal - PrincipalInstallments)),2);
                if (Principal > (sumOfPreviousPrincipal - PrincipalInstallments)) {
                    PrincipalInstallments = roundDouble((Principal - (sumOfPreviousPrincipal - PrincipalInstallments)), 2);
                } else if (Principal < (sumOfPreviousPrincipal - PrincipalInstallments)) {
                    PrincipalInstallments = roundDouble(((sumOfPreviousPrincipal - PrincipalInstallments) - Principal), 2);
                } else {
                    PrincipalInstallments = principalPerInstallment;
                }
                expectedRepaymentAmount = roundDouble((PrincipalInstallments + InterestInstallments), 2);
                //cummPrincipal = roundDouble((PrincipalInstallments - cummPrincipal),2);
                //cummPrincipal = cummPrincipal - PrincipalInstallments;
                //InterestInstallments = interestPerInstallment + (annualInterest - (k * interestPerInstallment));
                //System.out.println("The Last InterestInstallments :: " + InterestInstallments);
                //System.out.println("The Last PrincipalInstallments :: " + PrincipalInstallments);
                //System.out.println("The Last expectedRepaymentAmount :: " + expectedRepaymentAmount);
                //System.out.println("The Last cummPrincipal :: " + cummPrincipal);
            }

            totPay += PrincipalInstallments;
            schedule.setAmountInterest(InterestInstallments);
            schedule.setAmountPrincipal(PrincipalInstallments);
            schedule.setExpectedRepaymentAmount(expectedRepaymentAmount);
            schedule.setExpectedRepaymentDate(expectedRepaymentDate);
            schedule.setCompanyId(companyId);
            schedule.setBranchId(branchId);
            schedule.setCummPrincipal(cummPrincipal);
            schedule.setActive("Y");
            schedule.setPaymentStatus("N");
            schedule.setMemberNo(loanRequestBean.getMemberNo());
            schedule.setLoanCaseId(loanRequestBean.getLoanCaseId());
            schedule.setCreatedBy(createdBy);
            schedule.setCreationDate(creationDate);

            //System.out.println("Principal:="+schedule.getAmountPrincipal() + "/Interest:="+ schedule.getAmountInterest() + "/EMI:=" + schedule.getExpectedRepaymentAmount() + "/Balance:=" +  Math.round((Principal-totPay)* 100.0)/100.0);
            list.add(schedule);
        }

        return list;
    }

    private java.util.Date getNextRepaymentDate(LoanRepayFreq repayFreq, java.util.Date currentDate) {
        java.util.Date nextRepaymentDate = null;
        BusinessDayUtil UTIL = new BusinessDayUtil();

        if ("RPM".equals(repayFreq.getCode())) {
            nextRepaymentDate = UTIL.addMonthsToDate(currentDate, 1);
        } else if ("RPW".equals(repayFreq.getCode())) {
            nextRepaymentDate = UTIL.addWeeksToDate(currentDate, 1);
        } else if ("RPB".equals(repayFreq.getCode())) {
            nextRepaymentDate = UTIL.addWeeksToDate(currentDate, 2);
        } else if ("RPD".equals(repayFreq.getCode())) {
            nextRepaymentDate = UTIL.addDaysToDate(currentDate, 1);
        }

        return nextRepaymentDate;
    }

    private double getLoanDurationAsFractionOfYear(LoanRepayFreq repayFreq, int installments) {
        double duration = 0.0;

        if ("RPM".equals(repayFreq.getCode())) {
            duration = ((double) installments) / 12.0;
        } else if ("RPW".equals(repayFreq.getCode())) {
            duration = ((double) installments) / 52.0;
        } else if ("RPB".equals(repayFreq.getCode())) {
            duration = ((double) installments) / 26.0;
        } else if ("RPD".equals(repayFreq.getCode())) {
            duration = ((double) installments) / 365.0;
        }

        return duration;
    }

    private double getLoanDurationAsFractionOfYear_(LoanRepayFreq repayFreq, int installments) {
        double duration = 0.0;

        if ("RPM".equals(repayFreq.getCode())) {
            duration = installments / 12.0;
        } else if ("RPW".equals(repayFreq.getCode())) {
            duration = installments / 52.0;
        } else if ("RPB".equals(repayFreq.getCode())) {
            duration = installments / 26.0;
        } else if ("RPD".equals(repayFreq.getCode())) {
            duration = installments / 365.0;
        }

        return duration;
    }

    public int deriveLoanInstallmentFromLoanDurationInMonths(String repayFreq, int duration) {
        int installments = 0;

        if ("RPM".equals(repayFreq)) {
            installments = Math.round(duration / 1);
        } else if ("RPW".equals(repayFreq)) {
            installments = Math.round(duration * 4);
        } else if ("RPB".equals(repayFreq)) {
            installments = Math.round(duration * 2);
        } else if ("RPD".equals(repayFreq)) {
            installments = Math.round(duration * 30);
        }

        return installments;
    }

    public ArrayList<LoanRepaymentSchedule> generateLoanScheduleReducingEqualInstallments(LoanRequestBean loanRequestBean, LoanRepayFreq repayFreq, java.util.Date startDate, String createdBy, java.util.Date creationDate) {
        String companyId = loanRequestBean.getCompanyId();
        String branchId = loanRequestBean.getBranchId();
        double Principal = loanRequestBean.getApprovedAmount();
        double rate = loanRequestBean.getAppliedRate();
        int installments = loanRequestBean.getNoOfInstallments();
        double duration = 0.0;
        double cummPrincipal = Principal;

        ArrayList<LoanRepaymentSchedule> list = null;
        LoanRepaymentSchedule schedule = null;

        //convert duration to year from days
        duration = getLoanDurationAsFractionOfYear(repayFreq, installments);

        //java.util.Date expectedRepaymentDate = startDate;
        java.util.Date expectedRepaymentDate = getNextRepaymentDate(repayFreq, startDate);
        double principalRemaining = Principal;
        //double i=(rate/100) * (duration)

        /**
         * ************************************************************************************
         * double i=(rate/100) * (duration) / installments; //double EMI = i *
         * principalRemaining / (1-Math.pow((1+i),(-1.0 * installments))) ;
         * double EMI = i * principalRemaining / (1-Math.pow((1+i),(-1.0 *
         * installments))) ; //double EMI2 = i * principalRemaining /
         * (1-Math.pow((1+i),(-1.0))) ;
         * ************************************************************************************
         */
        double i = (rate / 100.00) * ((double) duration) / (double) installments;
        //double EMI = i * principalRemaining / (1-Math.pow((1+i),(-1.0 * installments))) ;
        double EMI = i * principalRemaining / (1 - Math.pow((1 + i), (-1.0 * (double) installments)));

        System.out.println("i:=" + i);
        System.out.println("EMI:=" + EMI);
        //System.out.println("EMI2:="+EMI2);

        for (int k = 1; k <= installments; k++) {
            if (list == null) {
                list = new ArrayList<LoanRepaymentSchedule>();
            }
            if (k > 1) {
                expectedRepaymentDate = getNextRepaymentDate(repayFreq, expectedRepaymentDate);
            }

            double interest = principalRemaining * i;
            //double installmentInterest=interest/installments; /*added to correct anomaly*/
            double amount2P = EMI - interest;

            schedule = new LoanRepaymentSchedule();
            double expectedRepaymentAmount = interest + amount2P;
            //double expectedRepaymentAmount=installmentInterest+amount2P;

            //schedule.setAmountInterest(roundDouble(Math.round(interest* 100.0)/100.0,2));
            //schedule.setAmountPrincipal(roundDouble(Math.round(amount2P* 100.0)/100.0,2));
            schedule.setAmountPrincipal(FormatTo2DecimalD(amount2P));
            cummPrincipal = cummPrincipal - schedule.getAmountPrincipal();

            if (k == installments) {//taking care of error induced by rounding-off
                if (cummPrincipal > 0.0) {
                    expectedRepaymentAmount += cummPrincipal;
                    interest += cummPrincipal;
                    cummPrincipal = 0.0;
                }
            }

            schedule.setAmountInterest(FormatTo2DecimalD(interest));

            schedule.setExpectedRepaymentAmount(FormatTo2DecimalD(EMI));
            schedule.setExpectedRepaymentDate(expectedRepaymentDate);
            schedule.setCompanyId(companyId);
            schedule.setBranchId(branchId);
            schedule.setCummPrincipal(FormatTo2DecimalD(cummPrincipal));
            schedule.setActive("Y");
            schedule.setPaymentStatus("N");
            schedule.setMemberNo(loanRequestBean.getMemberNo());
            schedule.setLoanCaseId(loanRequestBean.getLoanCaseId());
            schedule.setCreatedBy(createdBy);
            schedule.setCreationDate(creationDate);

            principalRemaining = principalRemaining - amount2P;

            //System.out.println("Principal:="+schedule.getAmountPrincipal() + "/Interest:=" + schedule.getAmountInterest() + "/EMI:=" + schedule.getExpectedRepaymentAmount() +"/Balance:=" +  Math.round(principalRemaining* 100.0)/100.0);
            list.add(schedule);
        }

        return list;
    }

    //http://en.flossmanuals.net/mifos-user-manual/how-to-calculate-the-emi/
    public ArrayList<LoanRepaymentSchedule> generateLoanScheduleReducing(LoanRequestBean loanRequestBean, LoanRepayFreq repayFreq, java.util.Date startDate, String createdBy, java.util.Date creationDate) {
        String companyId = loanRequestBean.getCompanyId();
        String branchId = loanRequestBean.getBranchId();
        double Principal = loanRequestBean.getApprovedAmount();
        double rate = loanRequestBean.getAppliedRate();
        int installments = loanRequestBean.getNoOfInstallments();
        double duration = 0.0;
        double cummPrincipal = Principal;

        ArrayList<LoanRepaymentSchedule> list = null;
        LoanRepaymentSchedule schedule = null;

        //convert duration to year from days
        duration = getLoanDurationAsFractionOfYear(repayFreq, installments);
        //java.util.Date expectedRepaymentDate = startDate;
        java.util.Date expectedRepaymentDate = getNextRepaymentDate(repayFreq, startDate);
        double principalRemaining = Principal;

        for (int k = 1; k <= installments; k++) {
            if (list == null) {
                list = new ArrayList<LoanRepaymentSchedule>();
            }
            if (k > 1) {
                expectedRepaymentDate = getNextRepaymentDate(repayFreq, expectedRepaymentDate);
            }

            /**
             * *********************************************************************************
             * double i=(rate/100) * (duration); double EMI = i *
             * principalRemaining / (1-Math.pow((1+i),(-1.0 * installments))) ;
             * //System.out.println("i:="+i);
             *
             * double interest=principalRemaining*i; double
             * installmentInterest=interest/installments; double
             * amount2P=Principal/installments;
             * *********************************************************************************
             */
            double i = (rate / 100.00) * ((double) duration);
            double EMI = i * principalRemaining / (1 - Math.pow((1 + i), (-1.0 * (double) installments)));

            double interest = principalRemaining * i;
            double installmentInterest = interest / (double) installments; /*added to correct anomaly*/

            double amount2P = Principal / (double) installments;

            schedule = new LoanRepaymentSchedule();
            //double expectedRepaymentAmount=interest+amount2P;
            double expectedRepaymentAmount = installmentInterest + amount2P;

            //schedule.setAmountInterest(roundDouble(Math.round(interest* 100.0)/100.0,2));
            //schedule.setAmountPrincipal(roundDouble(Math.round(amount2P* 100.0)/100.0,2));
            schedule.setAmountPrincipal(FormatTo2DecimalD(amount2P));
            cummPrincipal = cummPrincipal - schedule.getAmountPrincipal();

            if (k == installments) {//taking care of errors due to round off
                if (cummPrincipal > 0.0) {
                    expectedRepaymentAmount += cummPrincipal;
                    installmentInterest += cummPrincipal;
                    cummPrincipal = 0.0;
                }
            }

            schedule.setAmountInterest(FormatTo2DecimalD(installmentInterest));

            //schedule.setExpectedRepaymentAmount(roundDouble(Math.round(expectedRepaymentAmount* 100.0)/100.0,2));			
            schedule.setExpectedRepaymentAmount(FormatTo2DecimalD(expectedRepaymentAmount));
            schedule.setExpectedRepaymentDate(expectedRepaymentDate);
            schedule.setCompanyId(companyId);
            schedule.setBranchId(branchId);
            schedule.setCummPrincipal(FormatTo2DecimalD(cummPrincipal));
            schedule.setActive("Y");
            schedule.setPaymentStatus("N");
            schedule.setMemberNo(loanRequestBean.getMemberNo());
            schedule.setLoanCaseId(loanRequestBean.getLoanCaseId());
            schedule.setCreatedBy(createdBy);
            schedule.setCreationDate(creationDate);

            principalRemaining = principalRemaining - amount2P;

            //System.out.println("Principal:="+schedule.getAmountPrincipal() + "/Interest:=" + schedule.getAmountInterest() + "/EMI:=" + schedule.getExpectedRepaymentAmount() +"/Balance:=" +  Math.round(principalRemaining* 100.0)/100.0);
            list.add(schedule);
        }

        return list;
    }

    public static boolean isLeapYear() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = gc.get(Calendar.YEAR);
        return gc.isLeapYear(year);
    }

    public double calculatePenalty(double scheduleAmount, int delayDays, double yearlyRate) {
        double penalty = 0.0;
        int yearDays = isLeapYear() ? 366 : 365;

        penalty = (yearlyRate / yearDays) * scheduleAmount * (delayDays > 0 ? delayDays : 0);
        return penalty;
    }

    public static void main_(String args[]) {
        //new EazyCoopUtility().generateLoanScheduleFlat("1", "1", 1000.0, 0.1, 4, null, 1, new java.util.Date());
        System.out.println("");
        System.out.println("");

        //new EazyCoopUtility().generateLoanScheduleReducing("1", "1", 1000.0, 0.1, 4, null, 1, new java.util.Date());
        System.out.println("");
        System.out.println("");
        //new EazyCoopUtility().generateLoanScheduleReducingEqualInstallments("1", "1", 1000.0, 0.1, 4, null, 1, new java.util.Date());
    }

    public static void main(String args[]) {
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(new EazyCoopUtility().deriveBranchCode("3"));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            System.out.println(formatter.parse("01/09/2015"));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String dateInString = "07/06/2013";

        try {
            java.util.Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getLoggedonUser(HttpServletRequest req) {
        return req.getRemoteUser() == null ? "" : (String) req.getRemoteUser();
    }

    public User getCurrentUser(HttpServletRequest req) {
        return userService.getUserByEmail(req.getRemoteUser());
    }

    public String deriveCompanyCode(String companyId) {
        String resultStr = companyId == null ? "" : companyId.trim();

        while (resultStr.length() < 2) {
            resultStr = "0" + resultStr;
            //if(){break;}
        }

        return resultStr;
    }

    public String deriveBranchCode(String branchId) {
        String resultStr = branchId == null ? "" : branchId.trim();

        while (resultStr.length() < 3) {
            resultStr = "0" + resultStr;
            //break;
        }

        return resultStr;
    }

    public String createProductAccount(Accnowbs obj) {
        //Transaction Entry Object with headers & content
        String str = null;

        try {
            BASE_URI = getURI();
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(BASE_URI).path("glwsprdacno");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, obj);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus() + ". Operation failed");
            }

            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
            str = output;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public boolean LogUserAction(Activitylog activity) {
        boolean success = false;

        try {
            BASE_URI = getURI();
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(BASE_URI).path("glwsaudlog");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, activity);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
                success = false;
            } else {
                success = true;
            }

            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public boolean sendSMS(SMSBean obj) {
        boolean success = false;

        try {
            BASE_URI = getURI();
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(BASE_URI).path("glwssms");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, obj);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
                success = false;
            } else {
                success = true;
            }

            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public boolean postEntry(Entrys ent) {
        boolean success = false;

        try {
            BASE_URI = getURI();
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            webResource = client.resource(BASE_URI).path("glws");

            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, ent);
            System.out.println("Server response : \n" + response.getStatus());

            if (response.getStatus() != 201) {
                //throw new RuntimeException("Failed : HTTP error code : "
                //        + response.getStatus() + ". Operation failed");
                success = false;
            } else {
                success = true;
            }

            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public static void writeRepaymentUploadErrorListToFile(String fileName, List<LoanRepayUploadBean> uploadedListFailed) throws Exception {
        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Upload Report");

        Iterator<LoanRepayUploadBean> iterator = uploadedListFailed.iterator();

        int rowIndex = 0;
        Row row = null;
        boolean createHeader = true;

        if (createHeader) {
            row = sheet.createRow(rowIndex++);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue("Loan Id");

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Member No");

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Repayment Amount");

            Cell cell3 = row.createCell(3);
            cell3.setCellValue("Repayment Date");

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("Validation Error");
        }

        while (iterator.hasNext()) {
            LoanRepayUploadBean obj = iterator.next();
            row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(obj.getLoanCaseId());

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(obj.getMemberNo());

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(obj.getRepayAmount());

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(obj.getPaymentDate());

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(obj.getValidationMessage());
        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(fileName);
        workbook.write(fos);
        fos.close();
        System.out.println(fileName + " written successfully");
    }

    //triggered by Job
    public void readExcelData() {
        System.out.println("Job Schedule  ************************");
        //get pending upload and run.
        Company coy = null;
        List<FileUpload> list = bulkUploadService.listPendingBulkUploadsByType("LR");
        List<FileUpload> list0 = bulkUploadService.listPendingBulkUploadsByType("LP");

        if (list != null && list.size() > 0) {
            for (FileUpload item : list) {
                double uploadSum = 0.0;
                double successSum = 0.0;
                double failedSum = 0.0;
                double penaltySum = 0.0;

                //LoanRepayValidationBean valObj = readExcelData(item.getUploadFilename(), item.getUploadedBy(), item.getBatchId(), item.getCompanyId(), item.getBranchId());
                LoanRepayValidationBean valObj = readExcelData(item.getUploadFilename(), item.getUploadedBy(), item.getBatchId(), item.getCompanyId(), item.getBranchId(),
                        helperUTIL, memberService, loanRequestService,
                        productService, loanRepaymentScheduleService);
                List<LoanRepayUploadBean> uploadedListSuccess = valObj.getUploadedListSuccess();

                //we need to set status to ongoing first
                item.setUploadStatus("O");
                bulkUploadService.addBulkUpload(item);

                int totalRecords = valObj.getUploadedListAll() == null ? 0 : valObj.getUploadedListAll().size();
                int successRecords = valObj.getUploadedListSuccess() == null ? 0 : valObj.getUploadedListSuccess().size();
                int failedRecords = valObj.getUploadedListFailed() == null ? 0 : valObj.getUploadedListFailed().size();

                //process good upload entries
                if (successRecords > 0) {
                    List<RepaymentUploadItems> listX = valObj.getPayableModels();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (RepaymentUploadItems itemF : listX) {
                            uploadSum += itemF.getAmount();
                            successSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadItemService.addBulkUploadItems(listX);
                    }

                }
                /**
                 * else{ //now update upload file table
                 * item.setFailedCount(failedRecords);
                 * item.setSuccessCount(successRecords);
                 * item.setTotalRecords(totalRecords); item.setProcessedDate(new
                 * java.util.Date()); item.setUploadStatus("U");
                 * item.setProcessingStatus("F");
                 *
                 * bulkUploadService.addBulkUpload(item); }*
                 */
                //insert failed records to DB
                if (failedRecords > 0) {
                    List<RepaymentUploadErrors> listX = valObj.getErrorBeans();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (RepaymentUploadErrors itemF : listX) {
                            uploadSum += itemF.getAmount();
                            failedSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadErrorService.addBulkUploadErrors(listX);
                    }
                }

                if (totalRecords > 0) {
                    //now update upload  file table
                    item.setFailedCount(failedRecords);
                    item.setSuccessCount(successRecords);
                    item.setTotalRecords(totalRecords);
                    item.setProcessedDate(new java.util.Date());
                    item.setUploadStatus("U");
                    item.setProcessingStatus("U");

                    item.setPenaltySum(penaltySum);
                    item.setFailedSum(failedSum);
                    item.setSuccessSum(successSum);
                    item.setUploadSum(uploadSum);

                    bulkUploadService.addBulkUpload(item);
                }

                boolean mailSent = false;

                //send email notifying the uploader
                try {
                    //mail bean setup
                    MailBean MB = new MailBean();
                    MB = getMailConfig();
                    MB.setToemail(item.getUploadedBy());
                    MB.setSubject(Definitions.MAIL_SUBJECT_REPAYMENT_UPLOAD);

                    MB.setAttachments(null);
                    MB.setMailBody("");

                    String uri = "";

                    try {
                        uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }

                    /*					     String mailBody= "	<style type=\"text/css\">" +
                     "	<!--" +
                     "	.style2 {" +
                     "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                     "		font-size: 14px;" +
                     "	}" +
                     "	-->" +
                     "	</style>" +
                     "	 <p class=\"style2\">Hello "+item.getUploadedBy()+", </p>" +
                     "	<p class=\"style2\">The Processing of your Bulk Loan Repayment has been completed. <br>" +
                     "   <strong>Below is your processing reports: </strong></p>" +
                     "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
                     "	  <tr>" +
                     "	    <td width=\"50%\"><span class=\"style2\">Upload Batch Id:</span></td>" +
                     "	    <td width=\"50%\"><span class=\"style2\">" + item.getBatchId() + "</span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Total Records Processed:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getTotalRecords() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Success Count:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getSuccessCount() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Success Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getSuccessSum() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Failed Count:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getFailedCount() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Failed Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getFailedSum() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Penalty Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getPenaltySum() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Upload Current Status:</span></td>" +
                     "	    <td><span class=\"style2\">Awaiting Validation By 2nd Level Oficer </span></td>" +
                     "	  </tr>" +
								
                     "     <tr>" +
                     "	    <td colspan=2><hr/></td>" +
                     "	    " +
                     "	  </tr>" +	
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">User Specified Record Count:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getUserUploadcount() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">User Specified Record Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getUserUploadSum() + " </span></td>" +
                     "	  </tr>" +
								
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">User Specified Penalty Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getUserUploadFine() + " </span></td>" +
                     "	  </tr>" +
								
                     "     <tr>" +
                     "	    <td colspan=2><hr/></td>" +
                     "	    " +
                     "	  </tr>" +        
                     "	</table>" +
                     "	<p class=\"style2\"><br>" +
                     "	  Thanks </p> " ;
				    
                     MB.setMailBody(mailBody);	    	
                     */
                    String mailBody = "";
                    String template = "loanrepaymentupload.ftl";
                    Map model = new HashMap();
                    model.put("getUploadedBy", item.getUploadedBy());
                    model.put("getBatchId", item.getBatchId());
                    model.put("getTotalRecords", item.getTotalRecords().toString());
                    model.put("getSuccessCount", item.getSuccessCount().toString());
                    model.put("getSuccessSum", item.getSuccessSum().toString());
                    model.put("getFailedCount", item.getFailedCount().toString());
                    model.put("getFailedSum", item.getFailedSum().toString());
                    model.put("getPenaltySum", item.getPenaltySum().toString());
                    model.put("getUserUploadcount", item.getUserUploadcount().toString());
                    model.put("getUserUploadSum", item.getUserUploadSum().toString());
                    model.put("getUserUploadFine", item.getUserUploadSum().toString());

                    try {
                        mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                    } catch (TemplateNotFoundException tpex) {
                        System.out.println("Error - missing email template :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (MalformedTemplateNameException tpex) {
                        System.out.println("Error - email template name :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (TemplateException tpex) {
                        System.out.println("Error - email template :" + tpex.getMessage());
                        tpex.printStackTrace();

                    } catch (IOException tpex) {
                        System.out.println("Error - email template IO :" + tpex.getMessage());
                        tpex.printStackTrace();
                    }

                    MB.setMailBody(mailBody);

                    //send email
                    this.sendMail(MB);
                    mailSent = true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * ****************** LOAN PAYOFF UPLOAD PROCESSING
         * ********************
         */
        /**
         * *********************************************************************
         */
        //read Loan PayOff Upload
        /**
         * *********************************************************************
         */
        /**
         * *********************************************************************
         */
        if (list0 != null && list0.size() > 0) {
            for (FileUpload item : list0) {
                double uploadSum = 0.0;
                double successSum = 0.0;
                double failedSum = 0.0;
                double penaltySum = 0.0;

                LoanPayOffValidationBean valObj = readExcelDataLP(item.getUploadFilename(), item.getUploadedBy(), item.getBatchId(), item.getCompanyId(), item.getBranchId(),
                        helperUTIL, memberService, loanRequestService,
                        productService, loanRepaymentScheduleService);
                List<LoanPayOffUploadBean> uploadedListSuccess = valObj.getUploadedListSuccess();

                //we need to set status to ongoing first
                item.setUploadStatus("O");
                bulkUploadService.addBulkUpload(item);

                int totalRecords = valObj.getUploadedListAll() == null ? 0 : valObj.getUploadedListAll().size();
                int successRecords = valObj.getUploadedListSuccess() == null ? 0 : valObj.getUploadedListSuccess().size();
                int failedRecords = valObj.getUploadedListFailed() == null ? 0 : valObj.getUploadedListFailed().size();

                //process good upload entries
                if (successRecords > 0) {
                    List<LoanPayOffUploadItems> listX = valObj.getPayableModels();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (LoanPayOffUploadItems itemF : listX) {
                            uploadSum += itemF.getAmount();
                            successSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadItemService.addLoanPayOffUploadItems(listX);
                    }
                }
                /**
                 * else{ //now update upload file table
                 * item.setFailedCount(failedRecords);
                 * item.setSuccessCount(successRecords);
                 * item.setTotalRecords(totalRecords); item.setProcessedDate(new
                 * java.util.Date()); item.setUploadStatus("U");
                 * item.setProcessingStatus("F");
                 *
                 * bulkUploadService.addBulkUpload(item); }**
                 */
                //insert failed records to DB
                if (failedRecords > 0) {
                    List<LoanPayOffUploadErrors> listX = valObj.getErrorBeans();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (LoanPayOffUploadErrors itemF : listX) {
                            uploadSum += itemF.getAmount();
                            failedSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadErrorService.addLoanPayOffUploadErrors(listX);
                    }
                }

                if (totalRecords > 0) {
                    //now update upload  file table
                    item.setFailedCount(failedRecords);
                    item.setSuccessCount(successRecords);
                    item.setTotalRecords(totalRecords);
                    item.setProcessedDate(new java.util.Date());
                    item.setUploadStatus("U");
                    item.setProcessingStatus("U");

                    item.setPenaltySum(penaltySum);
                    item.setFailedSum(failedSum);
                    item.setSuccessSum(successSum);
                    item.setUploadSum(uploadSum);

                    bulkUploadService.addBulkUpload(item);
                }

                //send email notifying the uploader
                boolean mailSent = false;

                try {
                    //mail bean setup
                    MailBean MB = new MailBean();
                    MB = getMailConfig();
                    MB.setToemail(item.getUploadedBy());
                    MB.setSubject(Definitions.MAIL_SUBJECT_REPAYMENT_UPLOAD);

                    MB.setAttachments(null);
                    MB.setMailBody("");

                    String uri = "";

                    try {
                        uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }

                    /*				     String mailBody= "	<style type=\"text/css\">" +
                     "	<!--" +
                     "	.style2 {" +
                     "		font-family: Verdana, Arial, Helvetica, sans-serif;" +
                     "		font-size: 14px;" +
                     "	}" +
                     "	-->" +
                     "	</style>" +
                     "	 <p class=\"style2\">Hello "+item.getUploadedBy()+", </p>" +
                     "	<p class=\"style2\">The Processing of your Bulk Loan PayOff has been completed. <br>" +
                     "   <strong>Below is your processing reports: </strong></p>" +
                     "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">" +
                     "	  <tr>" +
                     "	    <td width=\"50%\"><span class=\"style2\">Upload Batch Id:</span></td>" +
                     "	    <td width=\"50%\"><span class=\"style2\">" + item.getBatchId() + "</span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Total Records Processed:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getTotalRecords() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Success Count:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getSuccessCount() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Success Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getSuccessSum() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Failed Count:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getFailedCount() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Failed Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getFailedSum() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Penalty Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getPenaltySum() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">Upload Current Status:</span></td>" +
                     "	    <td><span class=\"style2\">Awaiting Validation By 2nd Level Oficer </span></td>" +
                     "	  </tr>" +
							
                     "     <tr>" +
                     "	    <td colspan=2><hr/></td>" +
                     "	    " +
                     "	  </tr>" +	
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">User Specified Record Count:</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getUserUploadcount() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">User Specified Record Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getUserUploadSum() + " </span></td>" +
                     "	  </tr>" +
							
                     "	  <tr>" +
                     "	    <td><span class=\"style2\">User Specified Penalty Sum (Amount):</span></td>" +
                     "	    <td><span class=\"style2\">" + item.getUserUploadFine() + " </span></td>" +
                     "	  </tr>" +
							
                     "     <tr>" +
                     "	    <td colspan=2><hr/></td>" +
                     "	    " +
                     "	  </tr>" +        
                     "	</table>" +
                     "	<p class=\"style2\"><br>" +
                     "	  Thanks </p> " ;
			    
                     MB.setMailBody(mailBody);	    	
                     */
                    String mailBody = "";
                    String template = "loanpayoffupload.ftl";
                    Map model = new HashMap();
                    model.put("getUploadedBy", item.getUploadedBy());
                    model.put("getBatchId", item.getBatchId());
                    model.put("getTotalRecords", item.getTotalRecords().toString());
                    model.put("getSuccessCount", item.getSuccessCount().toString());
                    model.put("getSuccessSum", item.getSuccessSum().toString());
                    model.put("getFailedCount", item.getFailedCount().toString());
                    model.put("getFailedSum", item.getFailedSum().toString());
                    model.put("getPenaltySum", item.getPenaltySum().toString());
                    model.put("getUserUploadcount", item.getUserUploadcount().toString());
                    model.put("getUserUploadSum", item.getUserUploadSum().toString());
                    model.put("getUserUploadFine", item.getUserUploadSum().toString());

                    try {
                        mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                    } catch (TemplateNotFoundException tpex) {
                        System.out.println("Error - missing email template :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (MalformedTemplateNameException tpex) {
                        System.out.println("Error - email template name :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (TemplateException tpex) {
                        System.out.println("Error - email template :" + tpex.getMessage());
                        tpex.printStackTrace();

                    } catch (IOException tpex) {
                        System.out.println("Error - email template IO :" + tpex.getMessage());
                        tpex.printStackTrace();
                    }

                    MB.setMailBody(mailBody);

                    //send email
                    this.sendMail(MB);
                    mailSent = true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        //end of upload
    }

    //triggered by Job
    public void doBulkRepayment() {
        //list all pending bulk repayments
        Company coy = null;
        Branch brnc = null;
        LoanRequest obj = null;

        List<FileUpload> pendingPayments = bulkUploadService.listPendingBulkUploads("E");

        if (pendingPayments != null && pendingPayments.size() > 0) {
            for (FileUpload itemX : pendingPayments) {
                if ("LP".equalsIgnoreCase(itemX.getUploadType())) {
                    List<LoanPayOffUploadItems> listX = loanPayOffUploadItemService.listPendingLoanPayOffUploadItems(itemX.getBatchId());
                    boolean posted = false;

                    int countA = 0;
                    int countB = 0;

                    if (listX != null && listX.size() > 0) {
                        //send payment request via web service	
                        //update item status to paid
                        countA = listX.size();

                        for (LoanPayOffUploadItems item : listX) {
                            coy = companyService.getCompany(Integer.parseInt(item.getCompanyId()));
                            brnc = branchService.getBranch(Integer.parseInt(item.getBranchId()));
                            obj = loanRequestService.getLoanRequestByCaseId(item.getLoanCaseId());
                            Product product = productService.getProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType());
                            List<LoanRepaymentSchedule> schedules = loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId());

                            double penaltyIncurred = 0.0;

                            //
                            for (LoanRepaymentSchedule itemK : schedules) {
                                if (!"P".equals(itemK.getPaymentStatus())) {
                                    penaltyIncurred += helperUTIL.getLoanPenalty(itemK, product, getDaysDiff(itemK.getActualRepaymentDate(), itemK.getExpectedRepaymentDate()));
                                }
                            }

                            String sourcemod = "LN";
                            String txntype = "JV";
                            Date txndate = item.getUploadedDate();
                            Date postdate = brnc.getPostDate();

                            String dtimezone = "";
                            dtimezone = getTimeZoneGivenCountry(coy.getCountryId());
                            Date entrydate = getTimeByZone(dtimezone);

                            double currrate = 1.0;
                            double tranXAmt = 0.0;
                            double tranXAmtNeg = 0.0;
                            String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);

                            double intXAmt = obj.getLoanIntTotal();
                            double loanXAmt = obj.getApprovedAmount();
                            double fineXAmt = penaltyIncurred;

                            double intXAmtNeg = intXAmt * -1.0;
                            double loanXAmtNeg = loanXAmt * -1.0;
                            double fineXAmtNeg = penaltyIncurred * -1.0;

                            boolean hasPenalty = fineXAmt > 0.0 ? true : false;

                            Entrys ent = new Entrys();
                            NewSerialno nvSerial = new NewSerialno();
                            Integer varSerialint;
                            varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            String varSerial = Integer.toString(varSerialint);

                            //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);
                            //Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE,coy.getCurrentYear(), coy.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()), dtimezone);

                            ent.setTxnsheader(txnhdr);

                            tranXAmt = item.getAmount();
                            tranXAmtNeg = tranXAmt * -1.0;

                            //still need to derive these from customer records
                            //String customerFloatAccount="0003222b";
                            //String inerestFloatAccount="0003222b";
                            String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
                            String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";

                            String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";

                            //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
                            //LN Int MemNo: %s  AcNo: %s , ID: %s
                            //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                            String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                            if (narrationMain.length() > 80) {
                                narrationMain = narrationMain.substring(0, 80);
                            }
                            if (narrationInterest.length() > 80) {
                                narrationInterest = narrationInterest.substring(0, 80);
                            }
                             // narrationMain = narrationMain.substring(0, 80);
                            //  narrationInterest = narrationInterest.substring(0, 80);

                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            java.util.LinkedList<Entry> dentlist;
                            dentlist = new LinkedList<Entry>();
                            dentlist.add(mainLeg1);
                            dentlist.add(mainLeg2);

                            dentlist.add(intLeg1);
                            dentlist.add(intLeg2);

                            //Penalty Leg
                            if (hasPenalty && penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0) {
                                //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                                Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmt, fineXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
                                Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmtNeg, fineXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

                                dentlist.add(pntyLeg1);
                                dentlist.add(pntyLeg2);
                            }

                            ent.setEntrys(dentlist);

                            try {
                                postEntry(ent);
                                posted = true;
                                countB += 1;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                posted = false;
                            }

                            if (posted) {//now let us update schedule to paid
                                //Mark itemX as processed
                                item.setProcessedStatus("P");
                                loanPayOffUploadItemService.addLoanPayOffUploadItem(item);

                                //now mark all pending schedules as paid
                                loanRepaymentScheduleService.doLoanPayOff(item.getLoanCaseId());

                                //update loan balances
                                LoanRepaymentSchedule objSc = null;  //not require just to avoid ambiguity
                                loanRequestService.doUpdateLoanBalance(obj, objSc, true);
                            }
                        }
                    }

                    //update payment status to paid
                    if (countA == countB) {
                        helperUTIL.markBatchAsPaid(itemX.getBatchId());
                    }

                } else if ("LR".equalsIgnoreCase(itemX.getUploadType())) {
                    List<RepaymentUploadItems> listX = bulkUploadItemService.listPendingBulkUploadItems(itemX.getBatchId());
                    boolean posted = false;

                    int countA = 0;
                    int countB = 0;

                    if (listX != null && listX.size() > 0) {
                        //send payment request via web service	
                        //update item status to paid
                        countA = listX.size();

                        for (RepaymentUploadItems item : listX) {
                            coy = companyService.getCompany(Integer.parseInt(item.getCompanyId()));
                            brnc = branchService.getBranch(Integer.parseInt(item.getBranchId()));
                            obj = loanRequestService.getLoanRequestByCaseId(item.getLoanCaseId());
                            Product product = productService.getProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType());

                            String sourcemod = "LN";
                            String txntype = "JV";
                            Date txndate = item.getUploadedDate();
                            Date postdate = brnc.getPostDate();

                            String dtimezone = "";
                            dtimezone = getTimeZoneGivenCountry(coy.getCountryId());
                            Date entrydate = getTimeByZone(dtimezone);

                            double currrate = 1.0;
                            double tranXAmt = 0.0;
                            double tranXAmtNeg = 0.0;
                            String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);
                            LoanRepaymentSchedule objSc = loanRepaymentScheduleService.getLoanRepaymentSchedule(item.getScheduleId());
                            double penaltyIncurred = helperUTIL.getLoanPenalty(objSc, product, getDaysDiff(objSc.getActualRepaymentDate(), objSc.getExpectedRepaymentDate()));

                            double intXAmt = objSc.getAmountInterest();
                            double loanXAmt = objSc.getAmountPrincipal();
                            //double fineXAmt=objSc.getPenaltyIncurred();
                            double fineXAmt = penaltyIncurred;

                            double intXAmtNeg = objSc.getAmountInterest() * -1.0;
                            double loanXAmtNeg = objSc.getAmountPrincipal() * -1.0;
                            //double fineXAmtNeg=objSc.getPenaltyIncurred() * -1.0;
                            double fineXAmtNeg = penaltyIncurred * -1.0;

                            boolean hasPenalty = fineXAmt > 0.0 ? true : false;

                            Entrys ent = new Entrys();
                            NewSerialno nvSerial = new NewSerialno();
                            Integer varSerialint;
                            varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            String varSerial = Integer.toString(varSerialint);
                            //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

                            //Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE,coy.getCurrentYear(), coy.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()), dtimezone);

                            ent.setTxnsheader(txnhdr);

                            tranXAmt = item.getAmount();
                            tranXAmtNeg = tranXAmt * -1.0;

                            //still need to derive these from customer records
                            //String customerFloatAccount="0003222b";
                            //String inerestFloatAccount="0003222b";
                            String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
                            String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";

                            String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";

                            //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
                            //LN Int MemNo: %s  AcNo: %s , ID: %s
                            //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                            String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());

                            if (narrationMain.length() > 80) {
                                narrationMain = narrationMain.substring(0, 80);
                            }
                            if (narrationInterest.length() > 80) {
                                narrationInterest = narrationInterest.substring(0, 80);
                            }
                            // narrationMain = narrationMain.substring(0, 80);
                            //  narrationInterest = narrationInterest.substring(0, 80);
                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            java.util.LinkedList<Entry> dentlist;
                            dentlist = new LinkedList<Entry>();
                            dentlist.add(mainLeg1);
                            dentlist.add(mainLeg2);

                            dentlist.add(intLeg1);
                            dentlist.add(intLeg2);

                            //Penalty Leg
                            if (hasPenalty && penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0) {
                                //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                                Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmt, fineXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
                                Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmtNeg, fineXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

                                dentlist.add(pntyLeg1);
                                dentlist.add(pntyLeg2);
                            }

                            ent.setEntrys(dentlist);

                            try {
                                postEntry(ent);
                                posted = true;
                                countB += 1;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                posted = false;
                            }

                            if (posted) {//now let us update schedule to paid
                                //Mark itemX as processed
                                item.setProcessedStatus("P");
                                bulkUploadItemService.addBulkUploadItem(item);

                                //now mark the schedule as paid
                                objSc.setPaymentStatus("P");
                                objSc.setLastModificationDate(currentDate());
                                objSc.setActualRepaymentDate(entrydate);
                                objSc.setActualRepaymentAmount(tranXAmt);
                                objSc.setLastModifiedBy(item.getUploadedBy());

                                //update item status to paid
                                loanRepaymentScheduleService.addLoanRepaymentSchedule(objSc);

                                //update loan balances
                                loanRequestService.doUpdateLoanBalance(obj, objSc, false);

                                //close out loan if this is the last repayment
                                String scheduleIdStr = String.valueOf(helperUTIL.getLoanScheduleID(obj.getLoanCaseId()));

                                if (objSc.getId() == Integer.parseInt(scheduleIdStr)) {
                                    obj.setLoanStatus("C"); //closed

                                    //now update loan to closed
                                    loanRequestService.addLoanRequest(obj);
                                }
                            }
                        }
                    }

                    //update payment status to paid
                    if (countA == countB) {
                        helperUTIL.markBatchAsPaid(itemX.getBatchId());
                    }
                }
            }
        }
    }

    //triggered by task
    public void doBulkRepayment(String companyId, String branchId, BulkUploadService bulkUploadService, HelperUtility helperUTIL,
            LoanRequestService loanRequestService, ProductService productService,
            LoanRepaymentScheduleService loanRepaymentScheduleService,
            BulkUploadItemService bulkUploadItemService,
            LoanPayOffUploadItemService loanPayOffUploadItemService,
            CompanyService companyService, BranchService branchService) {
        //list all pending bulk repayments
        Company coy = null;
        Branch brnc = null;
        LoanRequest obj = null;

        List<FileUpload> pendingPayments = bulkUploadService.listPendingBulkUploads("E", companyId, branchId);

        if (pendingPayments != null && pendingPayments.size() > 0) {
            for (FileUpload itemX : pendingPayments) {
                if ("LP".equalsIgnoreCase(itemX.getUploadType())) {
                    List<LoanPayOffUploadItems> listX = loanPayOffUploadItemService.listPendingLoanPayOffUploadItems(itemX.getBatchId());
                    boolean posted = false;

                    int countA = 0;
                    int countB = 0;

                    if (listX != null && listX.size() > 0) {
                        //send payment request via web service	
                        //update item status to paid
                        countA = listX.size();

                        for (LoanPayOffUploadItems item : listX) {
                            coy = companyService.getCompany(Integer.parseInt(item.getCompanyId()));
                            brnc = branchService.getBranch(Integer.parseInt(item.getBranchId()));
                            obj = loanRequestService.getLoanRequestByCaseId(item.getLoanCaseId());
                            Product product = productService.getProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType());
                            List<LoanRepaymentSchedule> schedules = loanRepaymentScheduleService.listLoanRepaymentScheduleByLoanCaseId(obj.getLoanCaseId());

                            double penaltyIncurred = 0.0;

                            //
                            for (LoanRepaymentSchedule itemK : schedules) {
                                if (!"P".equals(itemK.getPaymentStatus())) {
                                    penaltyIncurred += helperUTIL.getLoanPenalty(itemK, product, getDaysDiff(itemK.getActualRepaymentDate(), itemK.getExpectedRepaymentDate()));
                                }
                            }

                            String sourcemod = "LN";
                            String txntype = "JV";
                            Date txndate = item.getUploadedDate();
                            Date postdate = brnc.getPostDate();

                            String dtimezone = "";
                            //dtimezone = getTimeZoneGivenCountry(coy.getCountryId());
                            dtimezone = helperUTIL.getTimeZoneGivenCompanyId(coy.getId().toString());
                            Date entrydate = getTimeByZone(dtimezone);

                            double currrate = 1.0;
                            double tranXAmt = 0.0;
                            double tranXAmtNeg = 0.0;
                            String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);

                            double intXAmt = obj.getLoanIntTotal();
                            double loanXAmt = obj.getApprovedAmount();
                            double fineXAmt = penaltyIncurred;

                            double intXAmtNeg = intXAmt * -1.0;
                            double loanXAmtNeg = loanXAmt * -1.0;
                            double fineXAmtNeg = penaltyIncurred * -1.0;

                            boolean hasPenalty = fineXAmt > 0.0 ? true : false;

                            Entrys ent = new Entrys();
                            NewSerialno nvSerial = new NewSerialno();
                            Integer varSerialint;
                            varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            String varSerial = Integer.toString(varSerialint);

                            //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);
                            //Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE,coy.getCurrentYear(), coy.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()), dtimezone);

                            ent.setTxnsheader(txnhdr);

                            tranXAmt = item.getAmount();
                            tranXAmtNeg = tranXAmt * -1.0;

                            //still need to derive these from customer records
                            //String customerFloatAccount="0003222b";
                            //String inerestFloatAccount="0003222b";
                            String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
                            String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";

                            String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";

                            //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
                            //LN Int MemNo: %s  AcNo: %s , ID: %s
                            //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                            String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                            if (narrationMain.length() > 80) {
                                narrationMain = narrationMain.substring(0, 80);
                            }
                            if (narrationInterest.length() > 80) {
                                narrationInterest = narrationInterest.substring(0, 80);
                            }
                             // narrationMain = narrationMain.substring(0, 80);
                            //  narrationInterest = narrationInterest.substring(0, 80);

                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            java.util.LinkedList<Entry> dentlist;
                            dentlist = new LinkedList<Entry>();
                            dentlist.add(mainLeg1);
                            dentlist.add(mainLeg2);

                            dentlist.add(intLeg1);
                            dentlist.add(intLeg2);

                            //Penalty Leg
                            if (hasPenalty && penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0) {
                                //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                                Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmt, fineXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
                                Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmtNeg, fineXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

                                dentlist.add(pntyLeg1);
                                dentlist.add(pntyLeg2);
                            }

                            ent.setEntrys(dentlist);

                            try {
                                postEntry(ent);
                                posted = true;
                                countB += 1;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                posted = false;
                            }

                            if (posted) {//now let us update schedule to paid
                                //Mark itemX as processed
                                item.setProcessedStatus("P");
                                loanPayOffUploadItemService.addLoanPayOffUploadItem(item);

                                //now mark all pending schedules as paid
                                loanRepaymentScheduleService.doLoanPayOff(item.getLoanCaseId());

                                //update loan balances
                                LoanRepaymentSchedule objSc = null;  //not require just to avoid ambiguity
                                loanRequestService.doUpdateLoanBalance(obj, objSc, true);
                            }
                        }
                    }

                    //update payment status to paid
                    if (countA == countB) {
                        helperUTIL.markBatchAsPaid(itemX.getBatchId());
                    }

                } else if ("LR".equalsIgnoreCase(itemX.getUploadType())) {
                    List<RepaymentUploadItems> listX = bulkUploadItemService.listPendingBulkUploadItems(itemX.getBatchId());
                    boolean posted = false;

                    int countA = 0;
                    int countB = 0;

                    if (listX != null && listX.size() > 0) {
                        //send payment request via web service	
                        //update item status to paid
                        countA = listX.size();

                        for (RepaymentUploadItems item : listX) {
                            coy = companyService.getCompany(Integer.parseInt(item.getCompanyId()));
                            brnc = branchService.getBranch(Integer.parseInt(item.getBranchId()));
                            obj = loanRequestService.getLoanRequestByCaseId(item.getLoanCaseId());
                            Product product = productService.getProductsDistinctByCodeByBranch(obj.getBranchId(), obj.getLoanType());

                            String sourcemod = "LN";
                            String txntype = "JV";
                            Date txndate = item.getUploadedDate();
                            Date postdate = brnc.getPostDate();

                            String dtimezone = "";
                            //dtimezone = getTimeZoneGivenCountry(coy.getCountryId());
                            dtimezone = helperUTIL.getTimeZoneGivenCompanyId(coy.getId().toString());
                            Date entrydate = getTimeByZone(dtimezone);

                            double currrate = 1.0;
                            double tranXAmt = 0.0;
                            double tranXAmtNeg = 0.0;
                            String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);
                            LoanRepaymentSchedule objSc = loanRepaymentScheduleService.getLoanRepaymentSchedule(item.getScheduleId());
                            double penaltyIncurred = helperUTIL.getLoanPenalty(objSc, product, getDaysDiff(objSc.getActualRepaymentDate(), objSc.getExpectedRepaymentDate()));

                            double intXAmt = objSc.getAmountInterest();
                            double loanXAmt = objSc.getAmountPrincipal();
                            //double fineXAmt=objSc.getPenaltyIncurred();
                            double fineXAmt = penaltyIncurred;

                            double intXAmtNeg = objSc.getAmountInterest() * -1.0;
                            double loanXAmtNeg = objSc.getAmountPrincipal() * -1.0;
                            //double fineXAmtNeg=objSc.getPenaltyIncurred() * -1.0;
                            double fineXAmtNeg = penaltyIncurred * -1.0;

                            boolean hasPenalty = fineXAmt > 0.0 ? true : false;

                            Entrys ent = new Entrys();
                            NewSerialno nvSerial = new NewSerialno();
                            Integer varSerialint;
                            varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            String varSerial = Integer.toString(varSerialint);
                            //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

                            //Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE,coy.getCurrentYear(), coy.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                            Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()), dtimezone);

                            ent.setTxnsheader(txnhdr);

                            tranXAmt = item.getAmount();
                            tranXAmtNeg = tranXAmt * -1.0;

                            //still need to derive these from customer records
                            //String customerFloatAccount="0003222b";
                            //String inerestFloatAccount="0003222b";
                            String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
                            String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";

                            String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                            String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";

                            //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
                            //LN Int MemNo: %s  AcNo: %s , ID: %s
                            //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                            String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                            String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                            if (narrationMain.length() > 80) {
                                narrationMain = narrationMain.substring(0, 80);
                            }
                            if (narrationInterest.length() > 80) {
                                narrationInterest = narrationInterest.substring(0, 80);
                            }
                             // narrationMain = narrationMain.substring(0, 80);
                            //  narrationInterest = narrationInterest.substring(0, 80);

                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                            Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                            java.util.LinkedList<Entry> dentlist;
                            dentlist = new LinkedList<Entry>();
                            dentlist.add(mainLeg1);
                            dentlist.add(mainLeg2);

                            dentlist.add(intLeg1);
                            dentlist.add(intLeg2);

                            //Penalty Leg
                            if (hasPenalty && penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0) {
                                //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                                Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmt, fineXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
                                Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmtNeg, fineXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

                                dentlist.add(pntyLeg1);
                                dentlist.add(pntyLeg2);
                            }

                            ent.setEntrys(dentlist);

                            try {
                                postEntry(ent);
                                posted = true;
                                countB += 1;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                posted = false;
                            }

                            if (posted) {//now let us update schedule to paid
                                //Mark itemX as processed
                                item.setProcessedStatus("P");
                                bulkUploadItemService.addBulkUploadItem(item);

                                //now mark the schedule as paid
                                objSc.setPaymentStatus("P");
                                objSc.setLastModificationDate(currentDate());
                                objSc.setActualRepaymentDate(entrydate);
                                objSc.setActualRepaymentAmount(tranXAmt);
                                objSc.setLastModifiedBy(item.getUploadedBy());

                                //update item status to paid
                                loanRepaymentScheduleService.addLoanRepaymentSchedule(objSc);

                                //update loan balances
                                loanRequestService.doUpdateLoanBalance(obj, objSc, false);

                                //close out loan if this is the last repayment
                                String scheduleIdStr = String.valueOf(helperUTIL.getLoanScheduleID(obj.getLoanCaseId()));

                                if (objSc.getId() == Integer.parseInt(scheduleIdStr)) {
                                    obj.setLoanStatus("C"); //closed

                                    //now update loan to closed
                                    loanRequestService.addLoanRequest(obj);
                                }
                            }
                        }
                    }

                    //update payment status to paid
                    if (countA == countB) {
                        helperUTIL.markBatchAsPaid(itemX.getBatchId());
                    }
                }
            }
        }
    }
    //triggered by task

    public void readExcelData(String companyId, String branchId, BulkUploadService bulkUploadService, HelperUtility helperUTIL,
            MemberViewService memberService, LoanRequestService loanRequestService, ProductService productService,
            LoanRepaymentScheduleService loanRepaymentScheduleService,
            BulkUploadItemService bulkUploadItemService,
            BulkUploadErrorService bulkUploadErrorService,
            Configuration freemarkerConfiguration) {
        System.out.println("Job Schedule  ************************");
        //get pending upload and run.
        Company coy = null;
        List<FileUpload> list = bulkUploadService.listPendingBulkUploadsByType("LR", companyId, branchId);
        List<FileUpload> list0 = bulkUploadService.listPendingBulkUploadsByType("LP", companyId, branchId);

        if (list != null && list.size() > 0) {
            for (FileUpload item : list) {
                double uploadSum = 0.0;
                double successSum = 0.0;
                double failedSum = 0.0;
                double penaltySum = 0.0;

                LoanRepayValidationBean valObj = readExcelData(item.getUploadFilename(), item.getUploadedBy(), item.getBatchId(), item.getCompanyId(), item.getBranchId(),
                        helperUTIL, memberService, loanRequestService,
                        productService, loanRepaymentScheduleService);
                List<LoanRepayUploadBean> uploadedListSuccess = valObj.getUploadedListSuccess();

                //we need to set status to ongoing first
                item.setUploadStatus("O");
                bulkUploadService.addBulkUpload(item);

                int totalRecords = valObj.getUploadedListAll() == null ? 0 : valObj.getUploadedListAll().size();
                int successRecords = valObj.getUploadedListSuccess() == null ? 0 : valObj.getUploadedListSuccess().size();
                int failedRecords = valObj.getUploadedListFailed() == null ? 0 : valObj.getUploadedListFailed().size();

                //process good upload entries
                if (successRecords > 0) {
                    List<RepaymentUploadItems> listX = valObj.getPayableModels();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (RepaymentUploadItems itemF : listX) {
                            uploadSum += itemF.getAmount();
                            successSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadItemService.addBulkUploadItems(listX);
                    }

                }
                /**
                 * else{ //now update upload file table
                 * item.setFailedCount(failedRecords);
                 * item.setSuccessCount(successRecords);
                 * item.setTotalRecords(totalRecords); item.setProcessedDate(new
                 * java.util.Date()); item.setUploadStatus("U");
                 * item.setProcessingStatus("F");
                 *
                 * bulkUploadService.addBulkUpload(item); }*
                 */
                //insert failed records to DB
                if (failedRecords > 0) {
                    List<RepaymentUploadErrors> listX = valObj.getErrorBeans();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (RepaymentUploadErrors itemF : listX) {
                            uploadSum += itemF.getAmount();
                            failedSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadErrorService.addBulkUploadErrors(listX);
                    }
                }

                if (totalRecords > 0) {
                    //now update upload  file table
                    item.setFailedCount(failedRecords);
                    item.setSuccessCount(successRecords);
                    item.setTotalRecords(totalRecords);
                    item.setProcessedDate(new java.util.Date());
                    item.setUploadStatus("U");
                    item.setProcessingStatus("U");

                    item.setPenaltySum(penaltySum);
                    item.setFailedSum(failedSum);
                    item.setSuccessSum(successSum);
                    item.setUploadSum(uploadSum);

                    bulkUploadService.addBulkUpload(item);
                }

                boolean mailSent = false;

                //send email notifying the uploader
                try {
                    //mail bean setup
                    MailBean MB = new MailBean();
                    MB = getMailConfig();
                    MB.setToemail(item.getUploadedBy());
                    MB.setSubject(Definitions.MAIL_SUBJECT_REPAYMENT_UPLOAD);

                    MB.setAttachments(null);
                    MB.setMailBody("");

                    String uri = "";

                    try {
                        uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }

                    String mailBody = "";
                    String template = "loanrepaymentupload.ftl";
                    Map model = new HashMap();
                    model.put("getUploadedBy", item.getUploadedBy());
                    model.put("getBatchId", item.getBatchId());
                    model.put("getTotalRecords", item.getTotalRecords().toString());
                    model.put("getSuccessCount", item.getSuccessCount().toString());
                    model.put("getSuccessSum", item.getSuccessSum().toString());
                    model.put("getFailedCount", item.getFailedCount().toString());
                    model.put("getFailedSum", item.getFailedSum().toString());
                    model.put("getPenaltySum", item.getPenaltySum().toString());
                    model.put("getUserUploadcount", item.getUserUploadcount().toString());
                    model.put("getUserUploadSum", item.getUserUploadSum().toString());
                    model.put("getUserUploadFine", item.getUserUploadSum().toString());

                    try {
                        mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                    } catch (TemplateNotFoundException tpex) {
                        System.out.println("Error - missing email template :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (MalformedTemplateNameException tpex) {
                        System.out.println("Error - email template name :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (TemplateException tpex) {
                        System.out.println("Error - email template :" + tpex.getMessage());
                        tpex.printStackTrace();

                    } catch (IOException tpex) {
                        System.out.println("Error - email template IO :" + tpex.getMessage());
                        tpex.printStackTrace();
                    }

                    MB.setMailBody(mailBody);

                    //send email
                    // this.sendMail(MB);    
                    // send mail async
                    sendMailasync(MB);
                    mailSent = true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * ****************** LOAN PAYOFF UPLOAD PROCESSING
         * ********************
         */
        /**
         * *********************************************************************
         */
        //read Loan PayOff Upload
        /**
         * *********************************************************************
         */
        /**
         * *********************************************************************
         */
        if (list0 != null && list0.size() > 0) {
            for (FileUpload item : list0) {
                double uploadSum = 0.0;
                double successSum = 0.0;
                double failedSum = 0.0;
                double penaltySum = 0.0;

                LoanPayOffValidationBean valObj = readExcelDataLP(item.getUploadFilename(), item.getUploadedBy(), item.getBatchId(), item.getCompanyId(), item.getBranchId(),
                        helperUTIL, memberService, loanRequestService,
                        productService, loanRepaymentScheduleService);
                List<LoanPayOffUploadBean> uploadedListSuccess = valObj.getUploadedListSuccess();

                //we need to set status to ongoing first
                item.setUploadStatus("O");
                bulkUploadService.addBulkUpload(item);

                int totalRecords = valObj.getUploadedListAll() == null ? 0 : valObj.getUploadedListAll().size();
                int successRecords = valObj.getUploadedListSuccess() == null ? 0 : valObj.getUploadedListSuccess().size();
                int failedRecords = valObj.getUploadedListFailed() == null ? 0 : valObj.getUploadedListFailed().size();

                //process good upload entries
                if (successRecords > 0) {
                    List<LoanPayOffUploadItems> listX = valObj.getPayableModels();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (LoanPayOffUploadItems itemF : listX) {
                            uploadSum += itemF.getAmount();
                            successSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadItemService.addLoanPayOffUploadItems(listX);
                    }
                }
                /**
                 * else{ //now update upload file table
                 * item.setFailedCount(failedRecords);
                 * item.setSuccessCount(successRecords);
                 * item.setTotalRecords(totalRecords); item.setProcessedDate(new
                 * java.util.Date()); item.setUploadStatus("U");
                 * item.setProcessingStatus("F");
                 *
                 * bulkUploadService.addBulkUpload(item); }**
                 */
                //insert failed records to DB
                if (failedRecords > 0) {
                    List<LoanPayOffUploadErrors> listX = valObj.getErrorBeans();

                    //do batch insertion of records
                    if (listX != null && listX.size() > 0) {
                        for (LoanPayOffUploadErrors itemF : listX) {
                            uploadSum += itemF.getAmount();
                            failedSum += itemF.getAmount();
                            penaltySum += itemF.getPenalty();
                        }

                        bulkUploadErrorService.addLoanPayOffUploadErrors(listX);
                    }
                }

                if (totalRecords > 0) {
                    //now update upload  file table
                    item.setFailedCount(failedRecords);
                    item.setSuccessCount(successRecords);
                    item.setTotalRecords(totalRecords);
                    item.setProcessedDate(new java.util.Date());
                    item.setUploadStatus("U");
                    item.setProcessingStatus("U");

                    item.setPenaltySum(penaltySum);
                    item.setFailedSum(failedSum);
                    item.setSuccessSum(successSum);
                    item.setUploadSum(uploadSum);

                    bulkUploadService.addBulkUpload(item);
                }

                //send email notifying the uploader
                boolean mailSent = false;

                try {
                    //mail bean setup
                    MailBean MB = new MailBean();
                    MB = getMailConfig();
                    MB.setToemail(item.getUploadedBy());
                    MB.setSubject(Definitions.MAIL_SUBJECT_REPAYMENT_UPLOAD);

                    MB.setAttachments(null);
                    MB.setMailBody("");

                    String uri = "";

                    try {
                        uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }

                    String mailBody = "";
                    String template = "loanpayoffupload.ftl";
                    Map model = new HashMap();
                    model.put("getUploadedBy", item.getUploadedBy());
                    model.put("getBatchId", item.getBatchId());
                    model.put("getTotalRecords", item.getTotalRecords().toString());
                    model.put("getSuccessCount", item.getSuccessCount().toString());
                    model.put("getSuccessSum", item.getSuccessSum().toString());
                    model.put("getFailedCount", item.getFailedCount().toString());
                    model.put("getFailedSum", item.getFailedSum().toString());
                    model.put("getPenaltySum", item.getPenaltySum().toString());
                    model.put("getUserUploadcount", item.getUserUploadcount().toString());
                    model.put("getUserUploadSum", item.getUserUploadSum().toString());
                    model.put("getUserUploadFine", item.getUserUploadSum().toString());

                    try {
                        mailBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(template, "UTF-8"), model);
                    } catch (TemplateNotFoundException tpex) {
                        System.out.println("Error - missing email template :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (MalformedTemplateNameException tpex) {
                        System.out.println("Error - email template name :" + tpex.getMessage());
                        tpex.printStackTrace();
                    } catch (TemplateException tpex) {
                        System.out.println("Error - email template :" + tpex.getMessage());
                        tpex.printStackTrace();

                    } catch (IOException tpex) {
                        System.out.println("Error - email template IO :" + tpex.getMessage());
                        tpex.printStackTrace();
                    }

                    MB.setMailBody(mailBody);

                    //send email
                    //this.sendMail(MB);
                    // send mail async
                    sendMailasync(MB);
                    mailSent = true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        //end of upload
    }

    //triggered by Job
    public void doBulkRepayment_() {
        //list all pending bulk repayments
        Company coy = null;
        Branch brnc = null;
        LoanRequest obj = null;

        List<FileUpload> pendingPayments = bulkUploadService.listPendingBulkUploads("E");

        if (pendingPayments != null && pendingPayments.size() > 0) {

            for (FileUpload itemX : pendingPayments) {
                List<RepaymentUploadItems> listX = bulkUploadItemService.listPendingBulkUploadItems(itemX.getBatchId());
                boolean posted = false;

                int countA = 0;
                int countB = 0;

                if (listX != null && listX.size() > 0) {
                    //send payment request via web service	
                    //update item status to paid
                    countA = listX.size();

                    for (RepaymentUploadItems item : listX) {
                        coy = companyService.getCompany(Integer.parseInt(item.getCompanyId()));
                        brnc = branchService.getBranch(Integer.parseInt(item.getBranchId()));
                        obj = loanRequestService.getLoanRequestByCaseId(item.getLoanCaseId());

                        String sourcemod = "LN";
                        String txntype = "JV";
                        Date txndate = item.getUploadedDate();
                        Date postdate = brnc.getPostDate();

                        String dtimezone = "";
                        dtimezone = getTimeZoneGivenCountry(coy.getCountryId());
                        Date entrydate = getTimeByZone(dtimezone);

                        double currrate = 1.0;
                        double tranXAmt = 0.0;
                        double tranXAmtNeg = 0.0;
                        String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);
                        LoanRepaymentSchedule objSc = loanRepaymentScheduleService.getLoanRepaymentSchedule(item.getScheduleId());

                        double intXAmt = objSc.getAmountInterest();
                        double loanXAmt = objSc.getAmountPrincipal();
                        double fineXAmt = objSc.getPenaltyIncurred();

                        double intXAmtNeg = objSc.getAmountInterest() * -1.0;
                        double loanXAmtNeg = objSc.getAmountPrincipal() * -1.0;
                        double fineXAmtNeg = objSc.getPenaltyIncurred() * -1.0;

                        boolean hasPenalty = fineXAmt > 0.0 ? true : false;

                        Entrys ent = new Entrys();
                        NewSerialno nvSerial = new NewSerialno();
                        Integer varSerialint;
                        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                        String varSerial = Integer.toString(varSerialint);
                        //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

                        //Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE,coy.getCurrentYear(), coy.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()));
                        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Repayment Bulk: ID/" + item.getLoanCaseId(), sourcemod, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), Integer.parseInt(item.getCompanyId()), dtimezone);

                        ent.setTxnsheader(txnhdr);

                        tranXAmt = item.getAmount();
                        tranXAmtNeg = tranXAmt * -1.0;

                        //still need to derive these from customer records
                        //String customerFloatAccount="0003222b";
                        //String inerestFloatAccount="0003222b";
                        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "INT", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
                        String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "INT", obj.getCompanyId(), obj.getBranchId()); //"0003222b";

                        //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
                        //LN Int MemNo: %s  AcNo: %s , ID: %s
                        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
                        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
                        if (narrationMain.length() > 80) {
                            narrationMain = narrationMain.substring(0, 80);
                        }
                        if (narrationInterest.length() > 80) {
                            narrationInterest = narrationInterest.substring(0, 80);
                        }
                             // narrationMain = narrationMain.substring(0, 80);
                        //  narrationInterest = narrationInterest.substring(0, 80);

                        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                        Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                        Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                        Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));
                        Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, item.getUploadedBy(), Integer.parseInt(item.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(item.getCompanyId()));

                        java.util.LinkedList<Entry> dentlist;
                        dentlist = new LinkedList<Entry>();
                        dentlist.add(mainLeg1);
                        dentlist.add(mainLeg2);

                        dentlist.add(intLeg1);
                        dentlist.add(intLeg2);

                        //Penalty Leg
                        if (hasPenalty && penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0) {
                            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
                            Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmt, fineXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
                            Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount, valueDate, item.getLoanCaseId(), item.getLoanCaseId(), narrationInterest, varSerial, fineXAmtNeg, fineXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

                            dentlist.add(pntyLeg1);
                            dentlist.add(pntyLeg2);
                        }

                        ent.setEntrys(dentlist);

                        try {
                            postEntry(ent);
                            posted = true;
                            countB += 1;
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            posted = false;
                        }

                        if (posted) {//now let us update schedule to paid
                            //Mark itemX as processed
                            item.setProcessedStatus("P");
                            bulkUploadItemService.addBulkUploadItem(item);

                            //now mark the schedule as paid
                            objSc.setPaymentStatus("P");
                            objSc.setLastModificationDate(currentDate());
                            objSc.setActualRepaymentDate(entrydate);
                            objSc.setActualRepaymentAmount(tranXAmt);
                            objSc.setLastModifiedBy(item.getUploadedBy());

                            //update item status to paid
                            loanRepaymentScheduleService.addLoanRepaymentSchedule(objSc);

                            //update loan balances
                            loanRequestService.doUpdateLoanBalance(obj, objSc, false);

                            //close out loan if this is the last repayment
                            String scheduleIdStr = String.valueOf(helperUTIL.getLoanScheduleID(obj.getLoanCaseId()));

                            if (objSc.getId() == Integer.parseInt(scheduleIdStr)) {
                                obj.setLoanStatus("C"); //closed

                                //now update loan to closed
                                loanRequestService.addLoanRequest(obj);
                            }
                        }
                    }
                }

                //update payment status to paid
                if (countA == countB) {
                    helperUTIL.markBatchAsPaid(itemX.getBatchId());
                }
            }
        }
    }

    public boolean processCustomerLoanRepayment(LoanRepaymentSchedule objX, Company coy, LoanRequest obj, String timeZone, Date Postdate) {
        boolean posted = false;

        String sourcemod = "LN";
        String txntype = Definitions.LOAN_REPAYMENT_TRAN_CODE;
        Date txndate = currentDate();
        Date postdate = Postdate;

        String dtimezone = timeZone;
        Date entrydate = getTimeByZone(dtimezone);

        double currrate = 1.0;

        String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);

        Entrys ent = new Entrys();
        NewSerialno nvSerial = new NewSerialno();
        Integer varSerialint;
        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()));
        String varSerial = Integer.toString(varSerialint);
        //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

        Branch brnc = helperUTIL.getBranch(Integer.parseInt(obj.getBranchId()));
        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Schedule Repayment: ID/" + obj.getLoanCaseId(), sourcemod, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()), dtimezone);
        ent.setTxnsheader(txnhdr);

        String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
        String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";

        //still need to derive these from customer records
        //still need to derive these from customer records
        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";

        double intXAmt = FormatTo2DecimalD(objX.getAmountInterest());
        double loanXAmt = FormatTo2DecimalD(objX.getAmountPrincipal());
        double fineXAmt = FormatTo2DecimalD(objX.getPenaltyIncurred());

        double intXAmtNeg = objX.getAmountInterest() * -1.0;
        double loanXAmtNeg = objX.getAmountPrincipal() * -1.0;
        double fineXAmtNeg = objX.getPenaltyIncurred() * -1.0;

        System.out.println("customerFloatAccount :: " + customerFloatAccount + " :: loanXAmt ::" + loanXAmt);
        System.out.println("interestFloatAccount :: " + interestFloatAccount + " :: intXAmt ::" + intXAmt);
        System.out.println("penaltyFloatAccount :: " + penaltyFloatAccount + " :: fineXAmt :: " + fineXAmt);

        //double productPenalty=helperUTIL.getLoanPenalty(obj, product, defaultDays);
        boolean hasPenalty = fineXAmt > 0.0 ? true : false;

        //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
        //LN Int MemNo: %s  AcNo: %s , ID: %s
        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        if (narrationMain.length() > 80) {
            narrationMain = narrationMain.substring(0, 80);
        }
        if (narrationInterest.length() > 80) {
            narrationInterest = narrationInterest.substring(0, 80);
        }
        // narrationMain = narrationMain.substring(0, 80);
        //  narrationInterest = narrationInterest.substring(0, 80);

        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE),  obj.getLoanAccountNumber(), obj.getLoanCaseId(),obj.getMemberNo());
        //narrationInterest=narrationInterest.substring(0,78);
        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, objX.getLoanCaseId(), objX.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, objX.getLoanCaseId(), objX.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        //Entry intLeg1 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,obj.getLoanAccountNumber().trim(),valueDate,objX.getLoanCaseId() ,objX.getLoanCaseId(), narrationInterest, varSerial, intXAmt,intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        //Entry intLeg2 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,interestFloatAccount,valueDate,objX.getLoanCaseId() ,objX.getLoanCaseId(), narrationInterest, varSerial,  intXAmtNeg,  intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        /**
         * * Credit Interest / Debit Fund Source **
         */
        Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), valueDate, objX.getLoanCaseId(), objX.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, customerFloatAccount, valueDate, objX.getLoanCaseId(), objX.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        java.util.LinkedList<Entry> dentlist;
        dentlist = new LinkedList<Entry>();
        dentlist.add(mainLeg1);
        dentlist.add(mainLeg2);

        dentlist.add(intLeg1);
        dentlist.add(intLeg2);

        //Penalty Leg
        if (hasPenalty && penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0 && fineXAmt > 0.0) {
            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
            Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, objX.getLoanCaseId(), objX.getLoanCaseId(), narrationInterest, varSerial, fineXAmt, fineXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
            Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount, valueDate, objX.getLoanCaseId(), objX.getLoanCaseId(), narrationInterest, varSerial, fineXAmtNeg, fineXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

            dentlist.add(pntyLeg1);
            dentlist.add(pntyLeg2);
        }

        ent.setEntrys(dentlist);

        try {
            posted = postEntry(ent);
        } catch (Exception ex) {
            ex.printStackTrace();
            posted = false;
        }

        return posted;
    }

    public boolean processBalloonRepayment(Company coy, LoanRequest obj) {
        boolean posted = false;

        String sourcemod = "LN";
        String txntype = "JV";
        Date txndate = currentDate();
        Date postdate = currentDate();
        //Date entrydate = currentDate();

        String dtimezone = "";
        dtimezone = getTimeZoneGivenCountry(coy.getCountryId());
        Date entrydate = getTimeByZone(dtimezone);

        double currrate = 1.0;

        String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);

        Entrys ent = new Entrys();
        NewSerialno nvSerial = new NewSerialno();
        Integer varSerialint;
        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()));
        String varSerial = Integer.toString(varSerialint);
        //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

        Branch brnc = helperUTIL.getBranch(Integer.parseInt(obj.getBranchId()));

        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "BALLOON REPAYMENT:ID/" + obj.getLoanCaseId(), sourcemod, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()), dtimezone);
        ent.setTxnsheader(txnhdr);

        //still need to derive these from customer records
        //still need to derive these from customer records
        //String customerFloatAccount="0003222b";
        //String inerestFloatAccount="0003222b";
        String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
        String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";

        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";

        //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
        //LN Int MemNo: %s  AcNo: %s , ID: %s
        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        if (narrationMain.length() > 80) {
            narrationMain = narrationMain.substring(0, 80);
        }
        if (narrationInterest.length() > 80) {
            narrationInterest = narrationInterest.substring(0, 80);
        }
                             // narrationMain = narrationMain.substring(0, 80);
        //  narrationInterest = narrationInterest.substring(0, 80);

        double intXAmt = FormatTo2DecimalD(obj.getLoanIntTotal());
        double loanXAmt = FormatTo2DecimalD(obj.getApprovedAmount());

        double intXAmtNeg = intXAmt * -1.0;
        double loanXAmtNeg = loanXAmt * -1.0;

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        java.util.LinkedList<Entry> dentlist;
        dentlist = new LinkedList<Entry>();
        dentlist.add(mainLeg1);
        dentlist.add(mainLeg2);

        dentlist.add(intLeg1);
        dentlist.add(intLeg2);

        ent.setEntrys(dentlist);

        try {
            posted = postEntry(ent);
            //posted=true;
        } catch (Exception ex) {
            ex.printStackTrace();
            posted = false;
        }

        return posted;
    }

    public boolean processLoanPayOff(Company coy, LoanRequest obj, LoanPayOff loanPayOff, String dtimezone, Date Postdate) {
        boolean posted = false;

        String sourcemod = "LN";
        String txntype = "JV";
        Date txndate = currentDate();
        Date postdate = Postdate;

        Date entrydate = getTimeByZone(dtimezone);

        double currrate = 1.0;

        String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);

        Entrys ent = new Entrys();
        NewSerialno nvSerial = new NewSerialno();
        Integer varSerialint;
        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()));
        String varSerial = Integer.toString(varSerialint);
        //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

        Branch brnc = helperUTIL.getBranch(Integer.parseInt(obj.getBranchId()));

        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "BALLOON REPAYMENT:ID/" + obj.getLoanCaseId(), sourcemod, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()), dtimezone);
        ent.setTxnsheader(txnhdr);

        //still need to derive these from customer records
        //still need to derive these from customer records
        //String customerFloatAccount="0003222b";
        //String inerestFloatAccount="0003222b";        
        String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
        //String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";
        //String intAcctCode = "INT".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";
        String intAcctCode = "INT";
        String intAcctRvCode = "INR";

        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestRvFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctRvCode, obj.getCompanyId(), obj.getBranchId());

        //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
        //LN Int MemNo: %s  AcNo: %s , ID: %s
        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        if (narrationMain.length() > 80) {
            narrationMain = narrationMain.substring(0, 80);
        }
        if (narrationInterest.length() > 80) {
            narrationInterest = narrationInterest.substring(0, 80);
        }
        // narrationMain = narrationMain.substring(0, 80);
        //  narrationInterest = narrationInterest.substring(0, 80);

        //System.out.println("Loan Interest :: " +loanPayOff.getRepayTotInt());
        //System.out.println("Repay Loan Amt :: " + loanPayOff.getRepayTotAmt());
        //System.out.println("RepayTotPrl :: " + loanPayOff.getRepayTotPrl());
        //System.out.println("RepayPenalty :: " + loanPayOff.getRepayPenalty());
        /*
         double intXAmt = FormatTo2DecimalD(obj.getLoanIntTotal());
         double loanXAmt = FormatTo2DecimalD(obj.getApprovedAmount());
         * */
        double intXAmt = FormatTo2DecimalD(loanPayOff.getRepayTotInt());
        double loanXAmt = FormatTo2DecimalD(loanPayOff.getRepayTotAmt());

        double intXAmtNeg = intXAmt * -1.0;
        double loanXAmtNeg = loanXAmt * -1.0;

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company){
        //Entry intLeg1 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,obj.getLoanAccountNumber().trim(),valueDate,obj.getLoanCaseId() ,obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt,intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        //Entry intLeg2 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,interestFloatAccount,valueDate,obj.getLoanCaseId() ,obj.getLoanCaseId(), narrationInterest, varSerial,  intXAmtNeg,  intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        /**
         * * Credit Interest / Debit Fund Source **
         */
        Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg3 = new Entry();
        double intAmttoincome = intXAmt;
        double intAmttoreceivable = 0.0;
        Accrualutility acut = new Accrualutility();
        if ((accountingMethod.equalsIgnoreCase("ACCRUAL") == true) && (obj.getInterestType().equalsIgnoreCase("FFL") || obj.getInterestType().equalsIgnoreCase("FFC"))) {
            intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestRvFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intAmttoincome, intAmttoincome, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        }
        //
        if ((accountingMethod.equalsIgnoreCase("ACCRUAL") == true) && (obj.getInterestType().equalsIgnoreCase("RED") || obj.getInterestType().equalsIgnoreCase("REQ"))) {
            intAmttoreceivable = acut.getLoanAccruedInterest(obj.getLoanAccountNumber().trim(), obj.getLoanCaseId().trim(), obj.getActualCommencementDate(), obj.getBranchId(), obj.getCompanyId(), interestRvFloatAccount) - acut.getLoanAccruedInterestpaid(obj.getLoanAccountNumber().trim(), obj.getActualCommencementDate(), obj.getBranchId(), obj.getCompanyId(), interestRvFloatAccount);
            if (intAmttoreceivable < 0.0) {
                intAmttoreceivable = 0.0; //due to rounding errors? 
            }
            intLeg3 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestRvFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intAmttoreceivable, intAmttoreceivable, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
            intAmttoincome = intXAmt - intAmttoreceivable;
            intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intAmttoincome, intAmttoincome, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        }

        java.util.LinkedList<Entry> dentlist;
        dentlist = new LinkedList<Entry>();
        dentlist.add(mainLeg1);
        dentlist.add(mainLeg2);

        dentlist.add(intLeg1);
        dentlist.add(intLeg2);
        if ((accountingMethod.equalsIgnoreCase("ACCRUAL") == true) && (intAmttoreceivable != 0.0)) {
            dentlist.add(intLeg3);
        }

        double penaltyAmount = FormatTo2DecimalD(loanPayOff.getRepayPenalty());

        //Penalty Leg
        /**
         * * Credit Penalty Account / Debit Fund Source **
         */
        if (penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0 && penaltyAmount > 0.0) {
            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
            Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, penaltyAmount, penaltyAmount, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
            Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, penaltyAmount * -1.0, penaltyAmount * -1.0, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

            dentlist.add(pntyLeg1);
            dentlist.add(pntyLeg2);
        }

        ent.setEntrys(dentlist);

        try {
            posted = postEntry(ent);
            //true;
        } catch (Exception ex) {
            ex.printStackTrace();
            posted = false;
        }

        return posted;
    }

    public boolean processLoanPayOffDailyacc(Company coy, LoanRequest obj, LoanPayOff loanPayOff, String dtimezone, Date Postdate) {
        boolean posted = false;

        String sourcemod = "LN";
        String txntype = "JV";
        Date txndate = currentDate();
        Date postdate = Postdate;

        Date entrydate = getTimeByZone(dtimezone);

        double currrate = 1.0;

        String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);

        Entrys ent = new Entrys();
        NewSerialno nvSerial = new NewSerialno();
        Integer varSerialint;
        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()));
        String varSerial = Integer.toString(varSerialint);
        //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

        Branch brnc = helperUTIL.getBranch(Integer.parseInt(obj.getBranchId()));

        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "BALLOON REPAYMENT:ID/" + obj.getLoanCaseId(), sourcemod, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()), dtimezone);
        ent.setTxnsheader(txnhdr);

        //still need to derive these from customer records
        //still need to derive these from customer records
        //String customerFloatAccount="0003222b";
        //String inerestFloatAccount="0003222b";        
        String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
        //String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";
        //String intAcctCode = "INT".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";
        String intAcctCode = "INT";
        String intAcctRvCode = "INR";

        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestRvFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctRvCode, obj.getCompanyId(), obj.getBranchId());

        //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
        //LN Int MemNo: %s  AcNo: %s , ID: %s
        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
       
        if (narrationMain.length() > 80) {
            narrationMain = narrationMain.substring(0, 80);
        }
        if (narrationInterest.length() > 80) {
            narrationInterest = narrationInterest.substring(0, 80);
        }
        // narrationMain = narrationMain.substring(0, 80);
        //  narrationInterest = narrationInterest.substring(0, 80);


        //System.out.println("Loan Interest :: " +loanPayOff.getRepayTotInt());
        //System.out.println("Repay Loan Amt :: " + loanPayOff.getRepayTotAmt());
        //System.out.println("RepayTotPrl :: " + loanPayOff.getRepayTotPrl());
        //System.out.println("RepayPenalty :: " + loanPayOff.getRepayPenalty());
        /*
         double intXAmt = FormatTo2DecimalD(obj.getLoanIntTotal());
         double loanXAmt = FormatTo2DecimalD(obj.getApprovedAmount());
         * */
        double intXAmt = FormatTo2DecimalD(loanPayOff.getRepayTotInt());
        double loanXAmt = FormatTo2DecimalD(loanPayOff.getRepayTotAmt());

        double intXAmtNeg = intXAmt * -1.0;
        double loanXAmtNeg = loanXAmt * -1.0;

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company){
        //Entry intLeg1 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,obj.getLoanAccountNumber().trim(),valueDate,obj.getLoanCaseId() ,obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt,intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        //Entry intLeg2 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,interestFloatAccount,valueDate,obj.getLoanCaseId() ,obj.getLoanCaseId(), narrationInterest, varSerial,  intXAmtNeg,  intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        /**
         * * Credit Interest / Debit Fund Source **
         */
        Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg3 = new Entry();
        double intAmttoincome = intXAmt;
        double intAmttoreceivable = 0.0;
        Accrualutility acut = new Accrualutility();
        if (accountingMethod.equalsIgnoreCase("ACCRUAL") == true) {
            intAmttoreceivable = acut.getLoanAccruedInterest(obj.getLoanAccountNumber().trim(), obj.getLoanCaseId().trim(), obj.getActualCommencementDate(), obj.getBranchId(), obj.getCompanyId(), interestRvFloatAccount) - acut.getLoanAccruedInterestpaid(obj.getLoanAccountNumber().trim(), obj.getActualCommencementDate(), obj.getBranchId(), obj.getCompanyId(), interestRvFloatAccount);
            if (intAmttoreceivable < 0.0) {
                intAmttoreceivable = 0.0; //due to rounding errors? 
            }
            intLeg3 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestRvFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intAmttoreceivable, intAmttoreceivable, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
            intAmttoincome = intXAmt - intAmttoreceivable;
            intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intAmttoincome, intAmttoincome, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        }

        java.util.LinkedList<Entry> dentlist;
        dentlist = new LinkedList<Entry>();
        dentlist.add(mainLeg1);
        dentlist.add(mainLeg2);

        dentlist.add(intLeg1);
        dentlist.add(intLeg2);
        if ((accountingMethod.equalsIgnoreCase("ACCRUAL") == true) && (intAmttoreceivable != 0.0)) {
            dentlist.add(intLeg3);
        }

        double penaltyAmount = FormatTo2DecimalD(loanPayOff.getRepayPenalty());

        //Penalty Leg
        /**
         * * Credit Penalty Account / Debit Fund Source **
         */
        if (penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0 && penaltyAmount > 0.0) {
            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
            Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, penaltyAmount, penaltyAmount, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
            Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, penaltyAmount * -1.0, penaltyAmount * -1.0, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

            dentlist.add(pntyLeg1);
            dentlist.add(pntyLeg2);
        }

        ent.setEntrys(dentlist);

        try {
            posted = postEntry(ent);
            //true;
        } catch (Exception ex) {
            ex.printStackTrace();
            posted = false;
        }

        return posted;
    }

    public boolean processBulkLoanRepayment(Company coy, LoanRequest obj, BulkLoanRePayment bulkLoanRePayment, String timeZone, Date PostDate, String ipAdddress) {
        boolean posted = false;

        String sourcemod = "LN";
        String txntype = "JV";
        Date txndate = currentDate();
        Date postdate = PostDate;

        String dtimezone = timeZone;
        Date entrydate = getTimeByZone(dtimezone);

        double currrate = 1.0;

        String valueDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(entrydate);

        Entrys ent = new Entrys();
        NewSerialno nvSerial = new NewSerialno();
        Integer varSerialint;
        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()));
        String varSerial = Integer.toString(varSerialint);
        //Txnsheader txnhdr = new Txnsheader("1",txntype,coyyear,coyperiod, txndate,postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

        Branch brnc = helperUTIL.getBranch(Integer.parseInt(obj.getBranchId()));

        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_REPAYMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "BALLOON REPAYMENT:ID/" + obj.getLoanCaseId(), sourcemod, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()), dtimezone);
        ent.setTxnsheader(txnhdr);

        //still need to derive these from customer records
        //still need to derive these from customer records
        //String customerFloatAccount="0003222b";
        //String inerestFloatAccount="0003222b";
        String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
        String intAcctCode = "CASH".equalsIgnoreCase(accountingMethod) ? "INT" : "INR";

        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), intAcctCode, obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String penaltyFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "PIN", obj.getCompanyId(), obj.getBranchId()); //"0003222b";

        //LN Repmnt MemNo: %s  to  AcNo: %s , ID: %s
        //LN Int MemNo: %s  AcNo: %s , ID: %s
        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_REPAYMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        
       if (narrationMain.length() > 80) {
            narrationMain = narrationMain.substring(0, 80);
        }
        if (narrationInterest.length() > 80) {
            narrationInterest = narrationInterest.substring(0, 80);
        }
        // narrationMain = narrationMain.substring(0, 80);
        //  narrationInterest = narrationInterest.substring(0, 80);


        double intXAmt = FormatTo2DecimalD(bulkLoanRePayment.getRepayTotInt());
        double loanXAmt = FormatTo2DecimalD(bulkLoanRePayment.getRepayTotPrl());

        double intXAmtNeg = intXAmt * -1.0;
        double loanXAmtNeg = loanXAmt * -1.0;

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative, txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry mainLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmt, loanXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry mainLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_REPAYMENT_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationMain, varSerial, loanXAmtNeg, loanXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        //Entry intLeg1 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,obj.getLoanAccountNumber().trim(),valueDate,obj.getLoanCaseId() ,obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt,intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        //Entry intLeg2 =new Entry(Definitions.LOAN_INTEREST_TRAN_CODE,Definitions.LOAN_INTEREST_TRAN_CODE,interestFloatAccount,valueDate,obj.getLoanCaseId() ,obj.getLoanCaseId(), narrationInterest, varSerial,  intXAmtNeg,  intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()),       brnc.getCurrentYear(), brnc.getCurrentPeriod(),     Integer.parseInt(obj.getCompanyId()));
        /**
         * * Credit Interest / Debit Fund Source **
         */
        Entry intLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        java.util.LinkedList<Entry> dentlist;
        dentlist = new LinkedList<Entry>();
        dentlist.add(mainLeg1);
        dentlist.add(mainLeg2);

        dentlist.add(intLeg1);
        dentlist.add(intLeg2);

        double penaltyAmount = FormatTo2DecimalD(bulkLoanRePayment.getRepayTotPenalty());

        //Penalty Leg
        /**
         * * Credit Penalty Account / Debit Fund Source **
         */
        if (penaltyFloatAccount != null && penaltyFloatAccount.trim().length() > 0 && penaltyAmount > 0.0) {
            //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
            Entry pntyLeg1 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, penaltyFloatAccount.trim(), valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, penaltyAmount, penaltyAmount, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
            Entry pntyLeg2 = new Entry(Definitions.LOAN_REPAYMENT_TRAN_CODE, Definitions.LOAN_PENALTY_TRAN_CODE, customerFloatAccount, valueDate, obj.getLoanCaseId(), obj.getLoanCaseId(), narrationInterest, varSerial, penaltyAmount * -1.0, penaltyAmount * -1.0, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

            dentlist.add(pntyLeg1);
            dentlist.add(pntyLeg2);
        }

        ent.setEntrys(dentlist);

        try {
            posted = postEntry(ent);

            if (posted) {
                Activitylog activity = new Activitylog();

                //Audit action
                activity.setEvent(Definitions.EVENT_LOAN_REPAYMENT);
                activity.setAction("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
                activity.setActionDate(new java.util.Date());
                activity.setActionItem("LN Repay Loan ID: " + obj.getLoanCaseId());
                activity.setActionResult("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
                activity.setDescription("Loan Repayment Processing for Loan ID: " + obj.getLoanCaseId());
                activity.setIpaddress(ipAdddress);
                activity.setUsername(obj.getDisburseBy());
                activity.setTimezone(timeZone);
                activity.setToDate("");

                try {
                    LogUserAction(activity);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            posted = false;
        }

        return posted;
    }

    public boolean doDisburseLoanToCustomer(LoanRequest obj, Company coy, String timeZone, Date PostDate) {
        boolean posted = false;

        String sourcemod = "LN";
        String txntype = Definitions.LOAN_DISBURSEMENT_TRAN_CODE;
        Date txndate = currentDate();
        Date postdate = PostDate;
        String dtimezone = timeZone;
        Date entrydate = getTimeByZone(dtimezone);

        double currrate = 1.0;

        Entrys ent = new Entrys();
        NewSerialno nvSerial = new NewSerialno();
        Integer varSerialint;
        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()));
        String varSerial = Integer.toString(varSerialint);
        //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

        Branch brnc = helperUTIL.getBranch(Integer.parseInt(obj.getBranchId()));
        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_DISBURSEMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Disbursement ID/:" + obj.getLoanCaseId(), sourcemod, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()), dtimezone);
        ent.setTxnsheader(txnhdr);

        //still need to derive these from customer records
        //String customerFloatAccount="0003222b";
        //String interestFloatAccount="0003222b";
        String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());

        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "INT", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestRvFloatAccount = "";
        if (accountingMethod.equalsIgnoreCase("ACCRUAL") == true) {
            interestRvFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "INR", obj.getCompanyId(), obj.getBranchId());
        }

        double intXAmt = FormatTo2DecimalD(obj.getLoanIntTotal());
        double intXAmtNeg = intXAmt * -1.0;

        //LN Disburs MemNo: %s , AcNo: %s , ID: %s
        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_DISBURSEMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_DISBURSEMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_DISBURSEMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        System.out.println("narrationMain :: " + narrationMain + " Length :: " + narrationMain.length() + " obj.getLoantitle() :: " + obj.getLoantitle() );
        System.out.println("narrationInterest :: " + narrationInterest + " Length :: " + narrationInterest.length());
        if (narrationMain.length() > 80) {
            narrationMain = narrationMain.substring(0, 80);
        }
        if (narrationInterest.length() > 80) {
            narrationInterest = narrationInterest.substring(0, 80);
        }
        //narrationMain = narrationMain.substring(0, 80);
        //narrationInterest = narrationInterest.substring(0, 80);

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry mainLeg1 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_DISBURSEMENT_TRAN_CODE, customerFloatAccount.trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationMain, varSerial, obj.getApprovedAmount(), obj.getApprovedAmount(), currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry mainLeg2 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_DISBURSEMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationMain, varSerial, obj.getApprovedAmount() * -1.0, obj.getApprovedAmount() * -1.0, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        Entry intLeg1 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg2 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestRvFloatAccount.trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        java.util.LinkedList<Entry> dentlist;
        dentlist = new LinkedList<Entry>();
        dentlist.add(mainLeg1);
        dentlist.add(mainLeg2);
        if ((accountingMethod.equalsIgnoreCase("ACCRUAL") == true) && (obj.getInterestType().equalsIgnoreCase("FFL") || obj.getInterestType().equalsIgnoreCase("FFC"))) {
            dentlist.add(intLeg1);
            dentlist.add(intLeg2);
        }

        ent.setEntrys(dentlist);

        try {
            posted = postEntry(ent);
        } catch (Exception ex) {
            ex.printStackTrace();
            posted = false;
        }

        return posted;
    }
public boolean doDisburseLoanToCustomerContraAcct(LoanRequest obj, Company coy, String timeZone, Date PostDate,String ChangeContraAcct) {
        boolean posted = false;

        String sourcemod = "LN";
        String txntype = Definitions.LOAN_DISBURSEMENT_TRAN_CODE;
        Date txndate = currentDate();
        Date postdate = PostDate;
        String dtimezone = timeZone;
        Date entrydate = getTimeByZone(dtimezone);

        double currrate = 1.0;

        Entrys ent = new Entrys();
        NewSerialno nvSerial = new NewSerialno();
        Integer varSerialint;
        varSerialint = nvSerial.returnSerialno("Dref", Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()));
        String varSerial = Integer.toString(varSerialint);
        //Txnsheader txnhdr = new Txnsheader("1", txntype, coyyear, coyperiod, txndate, postdate, entrydate, varSerial, "Journal Posting:", sourcemod, userid, branchcode, coyid);

        Branch brnc = helperUTIL.getBranch(Integer.parseInt(obj.getBranchId()));
        Txnsheader txnhdr = new Txnsheader("1", Definitions.LOAN_DISBURSEMENT_TRAN_CODE, brnc.getCurrentYear(), brnc.getCurrentPeriod(), txndate, postdate, entrydate, varSerial, "Loan Disbursement ID/:" + obj.getLoanCaseId(), sourcemod, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), Integer.parseInt(obj.getCompanyId()), dtimezone);
        ent.setTxnsheader(txnhdr);

        //still need to derive these from customer records
        //String customerFloatAccount="0003222b";
        //String interestFloatAccount="0003222b";
        String accountingMethod = helperUTIL.getBranchSetting(obj.getBranchId(), obj.getCompanyId());
      
        String customerFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "SRC", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        try{
         if(!ChangeContraAcct.isEmpty() && ChangeContraAcct!=null){
         customerFloatAccount = ChangeContraAcct;   
       }
        }
         catch(NullPointerException e)
         {
          System.out.println("NullPointerException caught");
          }
        String interestFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "INT", obj.getCompanyId(), obj.getBranchId()); //"0003222b";
        String interestRvFloatAccount = "";
        if (accountingMethod.equalsIgnoreCase("ACCRUAL") == true) {
            interestRvFloatAccount = helperUTIL.getContraAccount(obj.getLoanType(), "INR", obj.getCompanyId(), obj.getBranchId());
        }

        double intXAmt = FormatTo2DecimalD(obj.getLoanIntTotal());
        double intXAmtNeg = intXAmt * -1.0;

        //LN Disburs MemNo: %s , AcNo: %s , ID: %s
        //String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_DISBURSEMENT_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        //String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_INTEREST_TRAN_CODE), obj.getMemberNo(), obj.getLoanAccountNumber(), obj.getLoanCaseId());
        String narrationMain = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_DISBURSEMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        String narrationInterest = String.format(helperUTIL.getTransactionNaration(Definitions.LOAN_DISBURSEMENT_TRAN_CODE), obj.getLoanCaseId(), obj.getLoantitle());
        System.out.println("narrationMain :: " + narrationMain + " Length :: " + narrationMain.length() + " obj.getLoantitle() :: " + obj.getLoantitle() );
        System.out.println("narrationInterest :: " + narrationInterest + " Length :: " + narrationInterest.length());
        if (narrationMain.length() > 80) {
            narrationMain = narrationMain.substring(0, 80);
        }
        if (narrationInterest.length() > 80) {
            narrationInterest = narrationInterest.substring(0, 80);
        }
        //narrationMain = narrationMain.substring(0, 80);
        //narrationInterest = narrationInterest.substring(0, 80);

        //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,   rate,                  userId, branchId,year, period,company) {
        Entry mainLeg1 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_DISBURSEMENT_TRAN_CODE, customerFloatAccount.trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationMain, varSerial, obj.getApprovedAmount(), obj.getApprovedAmount(), currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry mainLeg2 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_DISBURSEMENT_TRAN_CODE, obj.getLoanAccountNumber().trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationMain, varSerial, obj.getApprovedAmount() * -1.0, obj.getApprovedAmount() * -1.0, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        Entry intLeg1 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestFloatAccount.trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmt, intXAmt, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));
        Entry intLeg2 = new Entry(Definitions.LOAN_DISBURSEMENT_TRAN_CODE, Definitions.LOAN_INTEREST_TRAN_CODE, interestRvFloatAccount.trim(), currentDate("yyyy-MM-dd"), "Ref" + obj.getId(), obj.getLoanCaseId(), narrationInterest, varSerial, intXAmtNeg, intXAmtNeg, currrate, obj.getDisburseBy(), Integer.parseInt(obj.getBranchId()), brnc.getCurrentYear(), brnc.getCurrentPeriod(), Integer.parseInt(obj.getCompanyId()));

        java.util.LinkedList<Entry> dentlist;
        dentlist = new LinkedList<Entry>();
        dentlist.add(mainLeg1);
        dentlist.add(mainLeg2);
        if ((accountingMethod.equalsIgnoreCase("ACCRUAL") == true) && (obj.getInterestType().equalsIgnoreCase("FFL") || obj.getInterestType().equalsIgnoreCase("FFC"))) {
            dentlist.add(intLeg1);
            dentlist.add(intLeg2);
        }

        ent.setEntrys(dentlist);

        try {
            posted = postEntry(ent);
        } catch (Exception ex) {
            ex.printStackTrace();
            posted = false;
        }

        return posted;
    }
    public Date currentDate() {
        return new java.util.Date();
    }

    public String currentDate(String format) {
        return new java.text.SimpleDateFormat(format).format(new java.util.Date());
    }

    public String formattedDate(Date dDate, String format) {
        return new java.text.SimpleDateFormat(format).format(dDate);
    }

    public LoanRepayValidationBean readExcelData(String fileName, String uploaderStr, String batchId, String companyId, String branchId,
            HelperUtility helperUTIL,
            MemberViewService memberService, LoanRequestService loanRequestService, ProductService productService,
            LoanRepaymentScheduleService loanRepaymentScheduleService) {
        List<LoanRepayUploadBean> uploadedListAll = new ArrayList<LoanRepayUploadBean>();
        List<LoanRepayUploadBean> uploadedListSuccess = new ArrayList<LoanRepayUploadBean>();
        List<LoanRepayUploadBean> uploadedListFailed = new ArrayList<LoanRepayUploadBean>();

        LoanRepayValidationBean lrv = new LoanRepayValidationBean();
        boolean SkipFirstRow = true;
        int rowCount = 0;

        //HelperUtility helper=new HelperUtility();
        try {
            //Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream(fileName);

            //create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;

            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            //Get the number of sheets in the xlsx file
            int numberOfSheets = workbook.getNumberOfSheets();
            numberOfSheets = 1;

            //loop through each of the sheets
            for (int i = 0; i < numberOfSheets; i++) {
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);

                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();
                int COLUMN_INDEX = 0;

                //need to skip first row                
                while (rowIterator.hasNext()) {
                    String loanCaseIdStr = "";
                    String memberNoStr = "";
                    String amountStr = "";
                    String penaltyStr = "";
                    double amountDbl = 0.0;
                    double dblPenalty = 0.0;
                    Date uploadDate = null;
                    String uploadDateStr = "";
                    String uploader = uploaderStr;
                    String uploadError = "N";
                    String validationMsg = "";
                    int daysOverDue = 0;
                    double fineToPay = 0.0;
                    int scheduleIdInt = -1;

                    LoanRepaymentSchedule nextSchedule = null;

                    //Get the row object
                    Row row = rowIterator.next();

                    if (row.getRowNum() == 0) {
                        continue;
                        //just skip the rows if row number is 0 
                    }

                    rowCount += 1;

                    //Every row has columns, get the column iterator and iterate over them
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        if (COLUMN_INDEX == 5) {
                            COLUMN_INDEX = 0;
                        }
                        COLUMN_INDEX += 1;
                        //get the cell object
                        Cell cell = cellIterator.next();

                        //if(loanCaseIdStr.equalsIgnoreCase("")){
                        if (COLUMN_INDEX == 1) {
                            try {
                                loanCaseIdStr = cell.getStringCellValue().trim();
                                boolean valid = helperUTIL.LoanExist(loanCaseIdStr);
                                //System.out.println("valid:="+valid);

                                if (!valid) {
                                    validationMsg += "; Invalid Loan Id";
                                }

                                nextSchedule = loanRepaymentScheduleService.getNextLoanScheduleForRepayment(loanCaseIdStr);

                                if (nextSchedule != null) {
                                    scheduleIdInt = nextSchedule.getId();
                                    System.out.println("scheduleIdInt:=" + scheduleIdInt);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                validationMsg += "; Invalid Loan Id";
                            }
                            //}else if(memberNoStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 2) {
                            //2nd column MEMER NO
                            try {
                                memberNoStr = cell.getStringCellValue().trim();
                                MemberView mem = memberService.getMember(branchId, memberNoStr);

                                if (mem == null) {
                                    //member does not exist
                                    validationMsg += "; Invalid Member No";
                                } else {
                                    System.out.println("memberNoStr:" + memberNoStr);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                validationMsg += "; Invalid Member No";
                            }
                            //}else if(amountStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 3) {
                            //3rd column  REPAYMENT AMOUNT 
                            try {
                                amountStr = cell.getStringCellValue().trim();
                            } catch (Exception ex) {
                                validationMsg += "; Invalid Upload Amount";
                            }
                            try {
                                amountDbl = Double.parseDouble(amountStr);
                            } catch (NumberFormatException ex) {
                                validationMsg += "; Invalid Upload Amount";
                            }

                            System.out.println("amountStr:" + amountStr);

                            double amountToPay = 0.0;

                            if (nextSchedule != null) {
                                amountToPay = nextSchedule.getExpectedRepaymentAmount();

                                amountToPay = this.roundDouble(amountToPay, 2);
                                amountDbl = this.roundDouble(amountDbl, 2);

                                if (amountToPay > amountDbl) {
                                    validationMsg += "; Repayment Amount Not Enough";
                                }
                            }
                            //}else if(uploadDateStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 4) {
                            //4th Column  REPAYMENT DATE
                            try {
                                uploadDateStr = cell.getStringCellValue().trim();
                            } catch (Exception ex) {
                                validationMsg += "; Invalid Upload Date";
                            }
                            try {
                                uploadDate = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(uploadDateStr);
                            } catch (Exception ex) {
                                validationMsg += "; Invalid Date Specified";
                            }

                            System.out.println("uploadDateStr:" + uploadDateStr);

                            if (nextSchedule != null) {
                                daysOverDue = this.getDaysDiff(nextSchedule.getExpectedRepaymentDate(), uploadDate);
                            }
                            //}else if(penaltyStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 5) {
                            //4th Column  PENALTY
                            boolean validPenalty = true;

                            try {
                                penaltyStr = cell.getStringCellValue().trim();
                            } catch (Exception ex) {
                                validPenalty = false;
                                validationMsg += "; Invalid Value specified for penalty";
                            }
                            try {
                                dblPenalty = Double.parseDouble(penaltyStr);
                            } catch (Exception ex) {
                                validPenalty = false;
                                validationMsg += "; Invalid Value Specified for Penalty";
                            }
                            LoanRequest reqObj = loanRequestService.getLoanRequestByCaseId(loanCaseIdStr);
                            Product product = productService.getProductByTypeCode(reqObj.getLoanType(), reqObj.getCompanyId(), reqObj.getBranchId());

                            double calculatedPenalty = this.calculatePenalty(nextSchedule.getExpectedRepaymentAmount(), daysOverDue, product.getPenalty());

                            if (calculatedPenalty > 0.0) {
                                if (validPenalty && dblPenalty < calculatedPenalty) {
                                    validationMsg += "; Penalty Amount is not sufficient: Expected: [" + calculatedPenalty + "]";
                                }
                            }
                        }
                    }//end of cell iterator

                    if (validationMsg.length() > 0) {
                        uploadError = "Y";
                    }

                    LoanRepayUploadBean c = new LoanRepayUploadBean(scheduleIdInt, loanCaseIdStr, memberNoStr, amountDbl, uploadDate, uploader, validationMsg, uploadError, daysOverDue, batchId, companyId, branchId, dblPenalty);
                    uploadedListAll.add(c);

                    if ("N".equals(uploadError)) {
                        uploadedListSuccess.add(c);
                    } else if ("Y".equals(uploadError)) {
                        uploadedListFailed.add(c);
                    }

                    uploadError = "N";
                    validationMsg = "";
                    nextSchedule = null;
                }//end of rows iterator                

            }//end of sheets for loop

            List<LoanRepaymentScheduleBean> payableList = prepareRepayScheduleBean(uploadedListSuccess);
            List<RepaymentUploadItemsBean> payableBeans = prepareRepaymentUploadItemScheduleBean(uploadedListSuccess);
            List<RepaymentUploadItems> payableModels = prepareRepaymentUploadItemScheduleModel(uploadedListSuccess);
            List<RepaymentUploadErrors> errorBeans = prepareRepaymentUploadErrorScheduleModel(uploadedListFailed);

            try {
                //create error file
                if (errorBeans != null && errorBeans.size() > 0) {
                    writeRepaymentUploadErrorsListToFile(batchId + "_LR_errors.xls", errorBeans);
                }

                //create success file
                if (payableModels != null && payableModels.size() > 0) {
                    writeRepaymentUploadSuccessListToFile(batchId + "_LR_success.xls", payableModels);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            lrv.setPayableList(payableList);
            lrv.setPayableModels(payableModels);
            lrv.setPayableBeans(payableBeans);
            lrv.setUploadedListAll(uploadedListAll);
            lrv.setUploadedListSuccess(uploadedListSuccess);
            lrv.setUploadedListFailed(uploadedListFailed);
            lrv.setErrorBeans(errorBeans);

            //close file input stream
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lrv;
    }

    public LoanPayOffValidationBean readExcelDataLP(String fileName, String uploaderStr, String batchId, String companyId, String branchId,
            HelperUtility helperUTIL,
            MemberViewService memberService, LoanRequestService loanRequestService, ProductService productService,
            LoanRepaymentScheduleService loanRepaymentScheduleService) {
        List<LoanPayOffUploadBean> uploadedListAll = new ArrayList<LoanPayOffUploadBean>();
        List<LoanPayOffUploadBean> uploadedListSuccess = new ArrayList<LoanPayOffUploadBean>();
        List<LoanPayOffUploadBean> uploadedListFailed = new ArrayList<LoanPayOffUploadBean>();

        LoanPayOffValidationBean lpv = new LoanPayOffValidationBean();
        boolean SkipFirstRow = true;
        int rowCount = 0;

        //HelperUtility helper=new HelperUtility();
        try {
            //Create the input stream from the xlsx/xls file
            FileInputStream fis = new FileInputStream(fileName);

            //create Workbook instance for xlsx/xls file input stream
            Workbook workbook = null;

            if (fileName.toLowerCase().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (fileName.toLowerCase().endsWith("xls")) {
                workbook = new HSSFWorkbook(fis);
            }

            //Get the number of sheets in the xlsx file
            int numberOfSheets = workbook.getNumberOfSheets();
            numberOfSheets = 1;

            //loop through each of the sheets
            for (int i = 0; i < numberOfSheets; i++) {
                //Get the nth sheet from the workbook
                Sheet sheet = workbook.getSheetAt(i);

                //every sheet has rows, iterate over them
                Iterator<Row> rowIterator = sheet.iterator();

                while (rowIterator.hasNext()) {
                    String loanCaseIdStr = "";
                    String memberNoStr = "";
                    String amountStr = "";
                    String penaltyStr = "";
                    double amountDbl = 0.0;
                    double dblPenalty = 0.0;
                    Date uploadDate = null;
                    String uploadDateStr = "";
                    String uploader = uploaderStr;
                    String uploadError = "N";
                    String validationMsg = "";
                    int daysOverDue = 0;
                    double fineToPay = 0.0;
                    //int    scheduleIdInt=-1;

                    LoanPayOffBean pOff = null;
                    int COLUMN_INDEX = 0;

                    //Get the row object
                    Row row = rowIterator.next();

                    if (row.getRowNum() == 0) {
                        continue; //just skip the rows if row number is 0 
                    }

                    rowCount += 1;

                    //Every row has columns, get the column iterator and iterate over them
                    Iterator<Cell> cellIterator = row.cellIterator();

                    while (cellIterator.hasNext()) {
                        if (COLUMN_INDEX == 5) {
                            COLUMN_INDEX = 0;
                        }
                        COLUMN_INDEX += 1;
                        //get the cell object
                        Cell cell = cellIterator.next();

                        //if(loanCaseIdStr.equalsIgnoreCase("")){
                        if (COLUMN_INDEX == 1) {
                            try {
                                loanCaseIdStr = cell.getStringCellValue().trim();
                                System.out.println("loanCaseIdStr:" + loanCaseIdStr);

                                boolean valid = helperUTIL.LoanExist(loanCaseIdStr);
                                if (!valid) {
                                    validationMsg += "; Invalid Loan Id";
                                }

                                if (valid) {
                                    pOff = getLPBean(loanCaseIdStr, loanRequestService, productService, loanRepaymentScheduleService);
                                }

                                if (pOff != null) {
                                    //scheduleIdInt=nextSchedule.getId();                            			
                                    //System.out.println("scheduleIdInt:="+scheduleIdInt);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                validationMsg += "; Invalid Loan Id";
                            }
                            //}else if(memberNoStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 2) {
                            //2nd column
                            try {
                                memberNoStr = cell.getStringCellValue().trim();
                                MemberView mem = memberService.getMember(branchId, memberNoStr);

                                if (mem == null) {
                                    //member does not exist
                                    validationMsg += "; Invalid Member No";
                                } else {
                                    System.out.println("memberNoStr:" + memberNoStr);
                                }

                            } catch (Exception ex) {
                                ex.printStackTrace();
                                validationMsg += "; Invalid Member No";
                            }
                            //}else if(amountStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 3) {
                            //3rd column
                            try {
                                amountStr = cell.getStringCellValue().trim();
                            } catch (Exception ex) {
                                validationMsg += "; Invalid Upload Amount";
                            }
                            try {
                                amountDbl = Double.parseDouble(amountStr);
                            } catch (NumberFormatException ex) {
                                validationMsg += "; Invalid Upload Amount";
                            }

                            System.out.println("amountDbl:" + amountDbl);

                            if (pOff != null) {
                                double amountToPay = roundDouble(pOff.getRepayTotAmt(), 2);
                                amountDbl = this.roundDouble(amountDbl, 2);

                                System.out.println("amountToPay:" + amountToPay);

                                if (amountToPay > amountDbl) {
                                    validationMsg += "; Repayment Amount Not Enough: " + amountToPay + " vs " + amountDbl;
                                } else if (amountToPay < amountDbl) {
                                    validationMsg += "; Repayment Amount Too Much: " + amountToPay + " vs " + amountDbl;
                                }
                            }
                            //}else if(uploadDateStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 4) {
                            //4th Column
                            try {
                                uploadDateStr = cell.getStringCellValue().trim();
                            } catch (Exception ex) {
                                validationMsg += "; Invalid Upload Date";
                            }
                            try {
                                uploadDate = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(uploadDateStr);
                            } catch (Exception ex) {
                                validationMsg += "; Invalid Date Specified";
                            }

                            //System.out.println("uploadDateStr:"+ uploadDateStr);
                            //if(nextSchedule!=null){
                            //   daysOverDue=this.getDaysDiff(nextSchedule.getExpectedRepaymentDate(), uploadDate);
                            //}                            	
                            //}else if(penaltyStr.equalsIgnoreCase("")){
                        } else if (COLUMN_INDEX == 5) {
                            boolean validPenalty = true;

                            try {
                                penaltyStr = cell.getStringCellValue().trim();
                            } catch (Exception ex) {
                                validPenalty = false;
                                validationMsg += "; Invalid Value Specified for Penalty";
                            }
                            try {
                                dblPenalty = Double.parseDouble(penaltyStr);
                            } catch (Exception ex) {
                                validPenalty = false;
                                validationMsg += "; Invalid Value Specified for Penalty";
                            }

                            LoanRequest reqObj = loanRequestService.getLoanRequestByCaseId(loanCaseIdStr);
                            Product product = productService.getProductByTypeCode(reqObj.getLoanType(), reqObj.getCompanyId(), reqObj.getBranchId());

                            /**
                             * ************************************************************************************************************************************************
                             * try{penaltyStr =
                             * cell.getStringCellValue().trim();}catch(Exception
                             * ex){validPenalty=false; validationMsg+="; Invalid
                             * Value specified for penalty";}
                             * try{dblPenalty=Double.parseDouble(penaltyStr);}catch(Exception
                             * ex){validPenalty=false; validationMsg+="; Invalid
                             * Value Specified for Penalty";} LoanRequest
                             * reqObj=loanRequestService.getLoanRequestByCaseId(loanCaseIdStr);
                             * Product
                             * product=productService.getProductByTypeCode(reqObj.getLoanType(),
                             * reqObj.getCompanyId(), reqObj.getBranchId());
                             * ************************************************************************************************************************************************
                             */
                            if (pOff != null) {
                                List<LoanRepaymentSchedule> listX = loanRepaymentScheduleService.listPendingLoanRepaymentScheduleByLoanCaseId(reqObj.getLoanCaseId());
                                double calculatedPenalty = 0;

                                if (listX != null && listX.size() > 0) {
                                    for (LoanRepaymentSchedule itemX : listX) {
                                        int delayDays = getDaysDiff(itemX.getExpectedRepaymentDate(), currentDate());

                                        if (delayDays > 0) {
                                            calculatedPenalty += helperUTIL.getLoanPenalty(itemX, product, getDaysDiff(itemX.getActualRepaymentDate(), itemX.getExpectedRepaymentDate()));
                                        } else {
                                            calculatedPenalty += 0.0;
                                        }
                                    }
                                }

                                calculatedPenalty = this.roundDouble(calculatedPenalty, 2);
                                dblPenalty = this.roundDouble(dblPenalty, 2);

                                if (calculatedPenalty > 0.0) {
                                    if (validPenalty && dblPenalty < calculatedPenalty) {
                                        validationMsg += "; Penalty Amount is not sufficient: Expected: [" + calculatedPenalty + "]";
                                    }
                                }
                            }

                        }
                    }//end of cell iterator

                    if (validationMsg.length() > 0) {
                        uploadError = "Y";
                    }

                    LoanPayOffUploadBean c = new LoanPayOffUploadBean(loanCaseIdStr, memberNoStr, amountDbl, uploadDate, uploader, validationMsg, uploadError, daysOverDue, batchId, companyId, branchId, dblPenalty);
                    uploadedListAll.add(c);

                    if ("N".equals(uploadError)) {
                        uploadedListSuccess.add(c);
                    } else if ("Y".equals(uploadError)) {
                        uploadedListFailed.add(c);
                    }

                    uploadError = "N";
                    validationMsg = "";
                }//end of rows iterator               

            }//end of sheets for loop

            //List<LoanRepaymentScheduleBean>  payableList=   prepareRepayScheduleBean(uploadedListSuccess);
            //List<LoanPayOffUploadItemsBean>  payableBeans=  prepareRepaymentUploadItemScheduleBean(uploadedListSuccess);
            List<LoanPayOffUploadItems> payableModels = prepareLoanPayOffUploadItemModel(uploadedListSuccess);
            List<LoanPayOffUploadErrors> errorBeans = prepareLoanPayOffUploadErrorModel(uploadedListFailed);

            try {
                //create error file
                if (errorBeans != null && errorBeans.size() > 0) {
                    writeLoanPayOffUploadErrorsListToFile(batchId + "_LP_errors.xls", errorBeans);
                }

                //create success file
                if (payableModels != null && payableModels.size() > 0) {
                    writeLoanPayOffUploadSuccessListToFile(batchId + "_LP_success.xls", payableModels);
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            //lpv.setPayableList(payableList);
            lpv.setPayableModels(payableModels);
            //lpv.setPayableBeans(payableBeans);
            lpv.setUploadedListAll(uploadedListAll);
            lpv.setUploadedListSuccess(uploadedListSuccess);
            lpv.setUploadedListFailed(uploadedListFailed);
            lpv.setErrorBeans(errorBeans);

            //close file input stream
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lpv;
    }

    public List<LoanRepaymentScheduleBean> prepareRepayScheduleBean(List<LoanRepayUploadBean> uploadedListSuccess) {
        List<LoanRepaymentScheduleBean> resultList = null;

        for (LoanRepayUploadBean item : uploadedListSuccess) {
            if (resultList == null) {
                resultList = new ArrayList<LoanRepaymentScheduleBean>();
            }
            LoanRepaymentScheduleBean obj = new LoanRepaymentScheduleBean();

            obj.setActive("Y");
            obj.setActualRepaymentAmount(item.getRepayAmount());
            obj.setActualRepaymentDate(item.getPaymentDate());
            obj.setDeleted("N");
            obj.setLoanCaseId(item.getLoanCaseId());
            obj.setId(item.getScheduleId());
            obj.setPenaltyIncurred(item.getFineToPay());
            obj.setLastModificationDate(new java.util.Date());
            obj.setLastModifiedBy(item.getPaymentBy());

            resultList.add(obj);
        }

        return resultList;
    }

    public List<RepaymentUploadItemsBean> prepareRepaymentUploadItemScheduleBean(List<LoanRepayUploadBean> uploadedListSuccess) {
        List<RepaymentUploadItemsBean> resultList = null;

        for (LoanRepayUploadBean item : uploadedListSuccess) {
            if (resultList == null) {
                resultList = new ArrayList<RepaymentUploadItemsBean>();
            }
            RepaymentUploadItemsBean obj = new RepaymentUploadItemsBean();

            obj.setActive("Y");
            obj.setAmount(item.getRepayAmount());
            obj.setUploadedDate(item.getPaymentDate());
            obj.setLoanCaseId(item.getLoanCaseId());
            obj.setId(item.getScheduleId());
            obj.setPenalty(item.getFineToPay());
            obj.setBatchId(item.getBatchId());
            obj.setMemberNo(item.getMemberNo());
            obj.setUploadedBy(item.getPaymentBy());
            obj.setProcessedStatus("E");
            obj.setCompanyId(item.getCompanyId());
            obj.setBranchId(item.getBranchId());
            obj.setScheduleId(item.getScheduleId());

            resultList.add(obj);
        }

        return resultList;
    }

    public List<RepaymentUploadItems> prepareRepaymentUploadItemScheduleModel(List<LoanRepayUploadBean> uploadedListSuccess) {
        List<RepaymentUploadItems> resultList = null;

        for (LoanRepayUploadBean item : uploadedListSuccess) {
            if (resultList == null) {
                resultList = new ArrayList<RepaymentUploadItems>();
            }
            RepaymentUploadItems obj = new RepaymentUploadItems();

            obj.setActive("Y");
            obj.setAmount(item.getRepayAmount());
            obj.setUploadedDate(item.getPaymentDate());
            obj.setLoanCaseId(item.getLoanCaseId());
            obj.setId(item.getScheduleId());
            obj.setPenalty(item.getFineToPay());
            obj.setBatchId(item.getBatchId());
            obj.setMemberNo(item.getMemberNo());
            obj.setUploadedBy(item.getPaymentBy());
            obj.setProcessedStatus("N");
            obj.setCompanyId(item.getCompanyId());
            obj.setBranchId(item.getBranchId());
            obj.setScheduleId(item.getScheduleId());

            //obj.setLastModificationDate(new java.util.Date());
            //obj.setLastModifiedBy(item.getPaymentBy());
            resultList.add(obj);
        }

        return resultList;
    }

    public List<RepaymentUploadErrors> prepareRepaymentUploadErrorScheduleModel(List<LoanRepayUploadBean> uploadedListError) {
        List<RepaymentUploadErrors> resultList = null;

        for (LoanRepayUploadBean item : uploadedListError) {

            if (resultList == null) {
                resultList = new ArrayList<RepaymentUploadErrors>();
            }
            RepaymentUploadErrors obj = new RepaymentUploadErrors();

            obj.setActive("Y");
            obj.setAmount(item.getRepayAmount());
            obj.setUploadedDate(item.getPaymentDate());
            obj.setLoanCaseId(item.getLoanCaseId());
            obj.setId(item.getScheduleId());
            obj.setPenalty(item.getFineToPay());
            obj.setErrorMsg(item.getValidationMessage());
            obj.setUploadedBy(item.getPaymentBy());
            obj.setCompanyId(item.getCompanyId());
            obj.setBranchId(item.getBranchId());

            resultList.add(obj);
        }

        return resultList;
    }

    public List<LoanPayOffUploadItems> prepareLoanPayOffUploadItemModel(List<LoanPayOffUploadBean> uploadedListSuccess) {
        List<LoanPayOffUploadItems> resultList = null;

        for (LoanPayOffUploadBean item : uploadedListSuccess) {
            if (resultList == null) {
                resultList = new ArrayList<LoanPayOffUploadItems>();
            }
            LoanPayOffUploadItems obj = new LoanPayOffUploadItems();

            obj.setActive("Y");
            obj.setAmount(item.getRepayAmount());
            obj.setUploadedDate(item.getPaymentDate());
            obj.setLoanCaseId(item.getLoanCaseId());
            //obj.setId(item.getScheduleId());
            obj.setPenalty(item.getFineToPay());
            obj.setBatchId(item.getBatchId());
            obj.setMemberNo(item.getMemberNo());
            obj.setUploadedBy(item.getPaymentBy());
            obj.setProcessedStatus("N");
            obj.setCompanyId(item.getCompanyId());
            obj.setBranchId(item.getBranchId());
            //obj.setScheduleId(item.getScheduleId());

            //obj.setLastModificationDate(new java.util.Date());
            //obj.setLastModifiedBy(item.getPaymentBy());
            resultList.add(obj);
        }

        return resultList;
    }

    public List<LoanPayOffUploadErrors> prepareLoanPayOffUploadErrorModel(List<LoanPayOffUploadBean> uploadedListError) {
        List<LoanPayOffUploadErrors> resultList = null;

        for (LoanPayOffUploadBean item : uploadedListError) {

            if (resultList == null) {
                resultList = new ArrayList<LoanPayOffUploadErrors>();
            }
            LoanPayOffUploadErrors obj = new LoanPayOffUploadErrors();

            obj.setActive("Y");
            obj.setAmount(item.getRepayAmount());
            obj.setUploadedDate(item.getPaymentDate());
            obj.setLoanCaseId(item.getLoanCaseId());
            //obj.setId(item.getScheduleId());
            obj.setBatchId(item.getBatchId());
            obj.setMemberNo(item.getMemberNo());
            obj.setUploadedBy(item.getPaymentBy());
            obj.setPenalty(item.getFineToPay());
            obj.setErrorMsg(item.getValidationMessage());
            obj.setUploadedBy(item.getPaymentBy());
            obj.setCompanyId(item.getCompanyId());
            obj.setBranchId(item.getBranchId());

            resultList.add(obj);
        }

        return resultList;
    }

    public int getDaysDiff(Date date1, Date date2) {
        return Days.daysBetween(new DateTime(date1), new DateTime(date2)).getDays();
    }

    public void writeRepaymentUploadErrorsListToFile(String fileName, List<RepaymentUploadErrors> errList) throws Exception {
        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Upload Errors");

        Iterator<RepaymentUploadErrors> iterator = errList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("Loan Case Id");

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("Member No");

        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("Amount");

        Cell headercell3 = headerrow.createCell(columnIndex++);
        headercell3.setCellValue("Upload Date");

        Cell headercell4 = headerrow.createCell(columnIndex++);
        headercell4.setCellValue("Error Msg");
        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            RepaymentUploadErrors obj = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getLoanCaseId());

            Cell cell1 = row.createCell(columnIndex++);
            cell1.setCellValue(obj.getMemberNo());

            Cell cell2 = row.createCell(columnIndex++);
            cell2.setCellValue(obj.getAmount());

            Cell cell3 = row.createCell(columnIndex++);
            cell3.setCellValue(obj.getUploadedDate());

            Cell cell4 = row.createCell(columnIndex++);
            cell4.setCellValue(obj.getErrorMsg());
        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(this.getReportLocation() + fileName);
        workbook.write(fos);
        fos.close();
        //System.out.println(fileName + " written successfully");
    }

    public void writeRepaymentUploadSuccessListToFile(String fileName, List<RepaymentUploadItems> successList) throws Exception {
        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Upload Errors");

        Iterator<RepaymentUploadItems> iterator = successList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("Loan Case Id");

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("Member No");

        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("Amount");

        Cell headercell4 = headerrow.createCell(columnIndex++);
        headercell4.setCellValue("Penalty");

        Cell headercell3 = headerrow.createCell(columnIndex++);
        headercell3.setCellValue("Upload Date");

        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            RepaymentUploadItems obj = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getLoanCaseId());

            Cell cell1 = row.createCell(columnIndex++);
            cell1.setCellValue(obj.getMemberNo());

            Cell cell2 = row.createCell(columnIndex++);
            cell2.setCellValue(obj.getAmount());

            Cell cell4 = row.createCell(columnIndex++);
            cell4.setCellValue(obj.getPenalty());

            Cell cell3 = row.createCell(columnIndex++);
            cell3.setCellValue(obj.getUploadedDate());
        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(getReportLocation() + fileName);
        workbook.write(fos);
        fos.close();
        //System.out.println(fileName + " written successfully");
    }

    public void writeLoanPayOffUploadErrorsListToFile(String fileName, List<LoanPayOffUploadErrors> errList) throws Exception {
        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Upload Errors");
        Iterator<LoanPayOffUploadErrors> iterator = errList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("Loan Case Id");

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("Member No");

        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("Amount");

        Cell headercell2a = headerrow.createCell(columnIndex++);
        headercell2a.setCellValue("Penalty");

        Cell headercell3 = headerrow.createCell(columnIndex++);
        headercell3.setCellValue("Upload Date");

        Cell headercell4 = headerrow.createCell(columnIndex++);
        headercell4.setCellValue("Error Msg");

        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            LoanPayOffUploadErrors obj = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getLoanCaseId());

            Cell cell1 = row.createCell(columnIndex++);
            cell1.setCellValue(obj.getMemberNo());

            Cell cell2 = row.createCell(columnIndex++);
            cell2.setCellValue(obj.getAmount());

            Cell cell2a = row.createCell(columnIndex++);
            cell2a.setCellValue(obj.getPenalty());

            Cell cell3 = row.createCell(columnIndex++);
            cell3.setCellValue(obj.getUploadedDate());

            Cell cell4 = row.createCell(columnIndex++);
            cell4.setCellValue(obj.getErrorMsg());
        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(getReportLocation() + fileName);
        workbook.write(fos);
        fos.close();
        //System.out.println(fileName + " written successfully");
    }

    public void writeLoanPayOffUploadSuccessListToFile(String fileName, List<LoanPayOffUploadItems> errList) throws Exception {
        Workbook workbook = null;

        if (fileName.endsWith("xlsx")) {
            workbook = new XSSFWorkbook();
        } else if (fileName.endsWith("xls")) {
            workbook = new HSSFWorkbook();
        } else {
            throw new Exception("invalid file name, should be xls or xlsx");
        }

        Sheet sheet = workbook.createSheet("Upload Items");

        Iterator<LoanPayOffUploadItems> iterator = errList.iterator();

        int rowIndex = 0;
        int columnIndex = 0;

        Row headerrow = sheet.createRow(rowIndex);

        Cell headercell0 = headerrow.createCell(columnIndex++);
        headercell0.setCellValue("Loan Case Id");

        Cell headercell1 = headerrow.createCell(columnIndex++);
        headercell1.setCellValue("Member No");

        Cell headercell2 = headerrow.createCell(columnIndex++);
        headercell2.setCellValue("Amount");

        Cell headercell2a = headerrow.createCell(columnIndex++);
        headercell2a.setCellValue("Penalty");

        Cell headercell3 = headerrow.createCell(columnIndex++);
        headercell3.setCellValue("Upload Date");

        rowIndex = 1;

        while (iterator.hasNext()) {
            columnIndex = 0;

            LoanPayOffUploadItems obj = iterator.next();
            Row row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(columnIndex++);
            cell0.setCellValue(obj.getLoanCaseId());

            Cell cell1 = row.createCell(columnIndex++);
            cell1.setCellValue(obj.getMemberNo());

            Cell cell2 = row.createCell(columnIndex++);
            cell2.setCellValue(obj.getAmount());

            Cell cell2a = row.createCell(columnIndex++);
            cell2a.setCellValue(obj.getPenalty());

            Cell cell3 = row.createCell(columnIndex++);
            cell3.setCellValue(obj.getUploadedDate());

        }

        //lets write the excel data to file now
        FileOutputStream fos = new FileOutputStream(getReportLocation() + fileName);
        workbook.write(fos);
        fos.close();
        //System.out.println(fileName + " written successfully");
    }

    public Branch createBranchFromCompany(Company coy, String currencyCode) {
        Branch brn = new Branch();

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1; // Note: zero based!

        brn.setCompanyId(coy.getId().toString());
        brn.setCountryId(coy.getCountryId());
        brn.setBranchName("HEAD OFFICE BRANCH");
        brn.setBranchCode("001");
        brn.setBaseCurrency(currencyCode);
        brn.setCreatedBy(coy.getCreatedBy());
        brn.setCurrentPeriod(month);
        brn.setCurrentYear(year);
        brn.setEmail(coy.getEmail());
        brn.setPostDate(currentDate());
        brn.setSetupDate(currentDate());
        brn.setPhone1(coy.getPhone1());
        brn.setPhone2(coy.getPhone2());
        brn.setActive("Y");
        brn.setDeleted("N");
        brn.setConnectToEazyCoop(coy.getConnectToEazyCoop() == null ? "N" : coy.getConnectToEazyCoop());

        return brn;
    }

    public void sendMail(MailBean MB) {
        String userid = MB.getUserid();
        String mailsmtphost = MB.getMailsmtphost();
        String mailsmtpport = MB.getMailsmtpport();
        String sslortls = MB.getSslortls();
        String toemail = MB.getToemail();
        final String password = MB.getPassword();
        final String from = MB.getFrom();
        String subject = MB.getSubject();
        String mailBody = MB.getMailBody();
        String attachments[] = MB.getAttachments();
        boolean isloaded = attachments == null ? false : true;

        Properties props = new Properties();

        props.put("mail.smtp.host", mailsmtphost);
        props.put("mail.smtp.auth", authenabled);
        props.put("mail.smtp.port", mailsmtpport);

        if (sslortls.equalsIgnoreCase("SSL")) {
            props.put("mail.smtp.socketFactory.port", mailsmtpport);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        } else if (sslortls.equalsIgnoreCase("TLS")) {
            props.put("mail.smtp.starttls.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "false");
        }

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            //Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            //message.setFrom(new InternetAddress(from));
            message.setFrom(new InternetAddress(Definitions.DEFAULT_MAIL_SENDER));
            Address[] toUser = InternetAddress.parse(toemail);
            message.setRecipients(Message.RecipientType.TO, toUser);

            message.setSubject(subject);

            // Create the message part 
            //BodyPart messageBodyPart = new MimeBodyPart();
            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mailBody, "text/html");

            //Fill the message
            //messageBodyPart.setText(mailBody);
            //Create a multipar message
            Multipart multipart = new MimeMultipart();

            //Set text message part
            multipart.addBodyPart(messageBodyPart);

            //Part two is attachment
            if (isloaded == true) {
                for (String item : attachments) {
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(item);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(new File(item).getName());
                    multipart.addBodyPart(messageBodyPart);

                }
            }

            //Send the complete message parts
            message.setContent(multipart);
            //message.setContent(mailBody, "text/html");
            System.out.println("host " + props.get("mail.smtp.host") );
            System.out.println("auth " + props.get("mail.smtp.auth") );
            System.out.println("port " + props.get("mail.smtp.port") );
            System.out.println("starttls " + props.get("mail.smtp.starttls.enable") );
            System.out.println("default sender " + Definitions.DEFAULT_MAIL_SENDER );
            System.out.println("from  " + from );
            // Send message
            Transport.send(message);
            //System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            /*
            System.out.println("host " + props.get("mail.smtp.host") );
            System.out.println("auth " + props.get("mail.smtp.auth") );
            System.out.println("port " + props.get("mail.smtp.port") );
            System.out.println("starttls " + props.get("mail.smtp.starttls.enable") );
            System.out.println("default sender " + Definitions.DEFAULT_MAIL_SENDER );
            System.out.println("MB.getFrom()  " + MB.getFrom() );
            System.out.println("Sent message error...." + " " + mex.getMessage());
            
            */
            mex.printStackTrace();
        }
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * @return the taskExec
     */
    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public class sendMailTask implements Runnable {

        private MailBean MB;

        public sendMailTask(MailBean MB) {
            this.MB = MB;
        }

        public void run() {
            sendMail(MB);
        }
    }

    public void sendMailasync(MailBean MB) {
        taskExecutor.execute(new sendMailTask(MB));
    }

    public class readExcelDataTask implements Runnable {

        private String companyId;
        private String branchId;
        private BulkUploadService bulkUploadServicex;
        private HelperUtility helperUTILx;
        private MemberViewService memberServicex;
        private LoanRequestService loanRequestServicex;
        private ProductService productServicex;
        private LoanRepaymentScheduleService loanRepaymentScheduleServicex;
        private BulkUploadItemService bulkUploadItemServicex;
        private BulkUploadErrorService bulkUploadErrorServicex;
        private Configuration freemarkerConfigurationx;

        public readExcelDataTask(String companyId, String branchId, BulkUploadService bulkUploadService, HelperUtility helperUTIL,
                MemberViewService memberService, LoanRequestService loanRequestService, ProductService productService,
                LoanRepaymentScheduleService loanRepaymentScheduleService,
                BulkUploadItemService bulkUploadItemService, BulkUploadErrorService bulkUploadErrorService,
                Configuration freemarkerConfiguration) {
            this.companyId = companyId;
            this.branchId = branchId;
            this.bulkUploadServicex = bulkUploadService;
            this.helperUTILx = helperUTIL;
            this.memberServicex = memberService;
            this.loanRequestServicex = loanRequestService;
            this.productServicex = productService;
            this.loanRepaymentScheduleServicex = loanRepaymentScheduleService;
            this.bulkUploadItemServicex = bulkUploadItemService;
            this.bulkUploadErrorServicex = bulkUploadErrorService;
            this.freemarkerConfigurationx = freemarkerConfiguration;
        }

        public void run() {
            readExcelData(companyId, branchId, bulkUploadServicex, helperUTILx, memberServicex, loanRequestServicex,
                    productServicex, loanRepaymentScheduleServicex, bulkUploadItemServicex, bulkUploadErrorServicex,
                    freemarkerConfigurationx);

        }
    }

    public void readExcelDataasync(
            String companyId, String branchId, BulkUploadService bulkUploadServicex, HelperUtility helperUTILx,
            MemberViewService memberServicex, LoanRequestService loanRequestServicex, ProductService productServicex,
            LoanRepaymentScheduleService loanRepaymentScheduleServicex,
            BulkUploadItemService bulkUploadItemServicex,
            BulkUploadErrorService bulkUploadErrorServicex,
            Configuration freemarkerConfigurationx) {
        taskExecutor.execute(new readExcelDataTask(
                companyId, branchId, bulkUploadServicex, helperUTILx, memberServicex, loanRequestServicex,
                productServicex, loanRepaymentScheduleServicex, bulkUploadItemServicex,
                bulkUploadErrorServicex, freemarkerConfigurationx));
    }

    public class doBulkRepaymentTask implements Runnable {

        private String companyId;
        private String branchId;
        private BulkUploadService bulkUploadServicex;
        private HelperUtility helperUTILx;
        private LoanRequestService loanRequestServicex;
        private ProductService productServicex;
        private LoanRepaymentScheduleService loanRepaymentScheduleServicex;
        private BulkUploadItemService bulkUploadItemServicex;
        private LoanPayOffUploadItemService loanPayOffUploadItemServicex;
        private CompanyService companyServicex;
        private BranchService branchServicex;
        //private SessionFactory sessionFactoryx;

        public doBulkRepaymentTask(String companyId, String branchId, BulkUploadService bulkUploadService, HelperUtility helperUTIL,
                LoanRequestService loanRequestService, ProductService productService,
                LoanRepaymentScheduleService loanRepaymentScheduleService,
                BulkUploadItemService bulkUploadItemService,
                LoanPayOffUploadItemService loanPayOffUploadItemService,
                CompanyService companyService, BranchService branchService) {
            this.companyId = companyId;
            this.branchId = branchId;
            this.bulkUploadServicex = bulkUploadService;
            this.bulkUploadItemServicex = bulkUploadItemService;
            this.helperUTILx = helperUTIL;
            this.loanRequestServicex = loanRequestService;
            this.productServicex = productService;
            this.loanRepaymentScheduleServicex = loanRepaymentScheduleService;
            this.bulkUploadItemServicex = bulkUploadItemService;
            this.loanPayOffUploadItemServicex = loanPayOffUploadItemService;
            this.companyServicex = companyService;
            this.branchServicex = branchService;
            //this.sessionFactoryx = sessionFactory;
        }

        public void run() {
            //doBulkRepayment(companyId, branchId);
            doBulkRepayment(companyId, branchId, bulkUploadServicex, helperUTILx, loanRequestServicex,
                    productServicex, loanRepaymentScheduleServicex, bulkUploadItemServicex, loanPayOffUploadItemServicex,
                    companyServicex, branchServicex);
        }
    }

    public void doBulkRepaymentasync(String companyId, String branchId, BulkUploadService bulkUploadServicex, HelperUtility helperUTILx,
            LoanRequestService loanRequestServicex, ProductService productServicex,
            LoanRepaymentScheduleService loanRepaymentScheduleServicex,
            BulkUploadItemService bulkUploadItemServicex,
            LoanPayOffUploadItemService loanPayOffUploadItemServicex,
            CompanyService companyServicex, BranchService branchServicex) {

        taskExecutor.execute(new doBulkRepaymentTask(companyId, branchId, bulkUploadServicex, helperUTILx, loanRequestServicex,
                productServicex, loanRepaymentScheduleServicex, bulkUploadItemServicex, loanPayOffUploadItemServicex,
                companyServicex, branchServicex));
    }

    public void incrementSerial(SessionFactory sessionFactory, String coyId) {
        sessionFactory.getCurrentSession().createSQLQuery("update branch_serial set serial=serial+1 where company_id=" + coyId).executeUpdate();
    }

    public synchronized Integer getNextSerial(SessionFactory sessionFactory, String coyId) {
        Integer value = 0;
        String sql = "select serial+1 from branch_serial  where company_id=" + coyId;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        value = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
        return value;
    }

    public String getTimeZoneGivenCountry(String countryId) {
        String value = "";
        String sql = "select timez from countries where id=" + countryId;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        value = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        return value;
    }

    public String getTimeZoneGivenCountry(SessionFactory sessionFactory, String countryId) {
        String value = "";
        String sql = "select timez from countries where id=" + countryId;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        value = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        return value;
    }

    public String getTimeZoneGivenCompanyId(String companyId) {
        String value = "";
        String sql = "select timez from countries where id=(select country_id from company where id='" + companyId + "')";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        value = (query == null || query.list().isEmpty()) ? "" : query.list().get(0).toString();
        return value;
    }

    public Integer getCurrentSerial(SessionFactory sessionFactory, String coyId) {
        Integer value = 0;
        String sql = "select serial from branch_serial  where company_id=" + coyId;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        value = (query == null || query.list().isEmpty()) ? 0 : Integer.parseInt(query.list().get(0).toString());
        return value;
    }

    public void createRoleDefault(SessionFactory sessionFactory, String branchId, String coyId, int option) {
        if (option == 1) {
            /**
             * coop admin *
             */
            String query1 = " INSERT INTO `usergrpmdl`(`ID`, `usergroup`, `menu`, `branchid`,`companyid`,`menue`) "
                    + " VALUES (NULL, 'CAdmin', 'AC1', '" + branchId + "', '" + coyId + "', NULL),"
                    + "        (NULL, 'CAdmin', 'AL1', '" + branchId + "', '" + coyId + "', NULL),"
                    + "        (NULL, 'CAdmin', 'AC2', '" + branchId + "', '" + coyId + "', NULL)," + //USER SETUP
                    "        (NULL, 'CAdmin', 'AC4', '" + branchId + "', '" + coyId + "', NULL)," + //AUTH PERMIT
                    "        (NULL, 'CAdmin', 'AC5', '" + branchId + "', '" + coyId + "', NULL)," + //BRANCH ACTIVATION
                    "        (NULL, 'CAdmin', 'AC6', '" + branchId + "', '" + coyId + "', NULL)," + //BRANCH USER ACTIVATION
                    "        (NULL, 'CAdmin', 'AC7', '" + branchId + "', '" + coyId + "', NULL)," + //BRANCH USER DE-ACTIVATION
                    "        (NULL, 'CAdmin', 'RP1', '" + branchId + "', '" + coyId + "', NULL);";
            sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
        } else if (option == 2) {
            /**
             * branch admin *
             */
            String query1 = " INSERT INTO `usergrpmdl`(`ID`, `usergroup`, `menu`, `branchid`,`companyid`,`menue`) "
                    + " VALUES "
                    + "        (NULL, 'BAdmin', 'AD3', '" + branchId + "', '" + coyId + "', NULL)," + //
                    "        (NULL, 'BAdmin', 'AD4', '" + branchId + "', '" + coyId + "', NULL),"
                    + "        (NULL, 'BAdmin', 'AD6', '" + branchId + "', '" + coyId + "', NULL)," + //USER SETUP
                    "        (NULL, 'BAdmin', 'AD10','" + branchId + "', '" + coyId + "', NULL)," + //LOAN SETTINGS MAINTENANCE
                    "        (NULL, 'BAdmin', 'AD11','" + branchId + "', '" + coyId + "', NULL)," + //UNLOCK USER ACCOUNT
                    "        (NULL, 'BAdmin', 'AD12','" + branchId + "', '" + coyId + "', NULL),"
                    + "        (NULL, 'BAdmin', 'AD16','" + branchId + "', '" + coyId + "', NULL),"
                    + "        (NULL, 'BAdmin', 'AD17','" + branchId + "', '" + coyId + "', NULL)," + //MENU ASSIGNMENT
                    "        (NULL, 'BAdmin', 'AD18','" + branchId + "', '" + coyId + "', NULL)," + //TAX ITEMS ASSIGNMENT
                    "        (NULL, 'BAdmin', 'AD19','" + branchId + "', '" + coyId + "', NULL)," + //AUTH PERMIT
                    "        (NULL, 'BAdmin', 'AL1', '" + branchId + "', '" + coyId + "', NULL),"
                    + "        (NULL, 'BAdmin', 'RP1', '" + branchId + "', '" + coyId + "', NULL);";

            sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
        }/*else{*//*
         String query1=   " INSERT INTO `usergrpmdl`(`ID`, `usergroup`, `menu`, `branchid`,`companyid`,`menue`) " +
         " VALUES         (NULL, 'BAdmin', 'AL1', '"+branchId+"', '"+coyId+"', NULL)," +
         "				  (NULL, 'BAdmin', 'RP1', '"+branchId+"', '"+coyId+"', NULL);"; // +
		
         sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate(); 
         }*/


    }

    public void createBranchUserDefault(SessionFactory sessionFactory, String branchId, String coyId, String groupCode) {
        String query1 = " INSERT INTO `usergrpmdl`(`ID`, `usergroup`, `menu`, `branchid`,`companyid`,`menue`) "
                + " VALUES         (NULL, '" + groupCode + "', 'AL1', '" + branchId + "', '" + coyId + "', NULL),"
                + "				  (NULL, '" + groupCode + "', 'RP1', '" + branchId + "', '" + coyId + "', NULL);"; // +

        sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
    }

    public void createCoyDefaults(SessionFactory sessionFactory, String branchId, String coyId) {
        String query1 = " INSERT INTO `usergroups` (`ID`, `Code`, `Description`, `Branchid`, `companyid`, `ACTIVE`, `DELETED`, `AccessId`) "
                + " VALUES "
                + /**
                 * **************************************************************************************
                 * "	(NULL, 'Acct', 'Accountant', '"+branchId+"', '"+coyId+"',
                 * NULL, NULL, 0), " + " (NULL, 'Audit', 'AccountsDep',
                 * '"+branchId+"', '"+coyId+"', 'Y', 'N', '0'), " + "	(NULL,
                 * 'Admin', 'Administrator', '"+branchId+"', '"+coyId+"', 'Y',
                 * 'N', '0'), " +
                 * ***************************************************************************************
                 */
                "		(NULL, 'CAdmin', 'Coop Admin', '" + branchId + "', '" + coyId + "', 'Y', 'N', '0') ";
        //"		(NULL, 'BAdmin', 'Branch Admin', '"+branchId+"', '"+coyId+"', 'Y', 'N', '1');";

        String query2 = " INSERT INTO `settings_coop`(`Setting`,`Value`,`Companyid`,Display) SELECT setting_name,default_val ,'"  + coyId + "',display  from settings_coop_def where active=1";
        sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
        sessionFactory.getCurrentSession().createSQLQuery(query2).executeUpdate();
    }

    public void createBranchDefaults(SessionFactory sessionFactory, String branchId, String coyId) {
        String query1 = " INSERT INTO `usergroups` (`ID`, `Code`, `Description`, `Branchid`, `companyid`, `ACTIVE`, `DELETED`, `AccessId`) "
                + " VALUES "
                + "		(NULL, 'BAdmin', 'Branch Admin', '" + branchId + "', '" + coyId + "', 'Y', 'N', '1');";

        String query2 = "		INSERT INTO `app_config` (`ID`, `ACTIVE`, `BRANCH_ID`, `COMPANY_ID`, `CONFIG_MAX_VALUE`, `CONFIG_MIN_VALUE`, `CONFIG_NAME`, `CONFIG_TYPE`,"
                + "		`CREATED_BY`, `CREATION_DATE`, `DELETED`, `LAST_MODIFICATION_DATE`, `LAST_MODIFIED_BY`, `COMPUTATION_TYPE`, `FORMULA_VALUE`, `MULIPLIER`, `OPERAND`) VALUES "
                + " 		(NULL, 'Y', '" + branchId + "', '" + coyId + "', '" + Definitions.DEFAULT_MAX_CON_LOAN + "', '" + Definitions.DEFAULT_MAX_CON_LOAN + "', 'CONCURRENT LOAN', 'LOAN', 'admin', now(), 'N', now(), 'admin', 'STATIC', NULL, NULL, NULL), "
                + " 		(NULL, 'Y', '" + branchId + "', '" + coyId + "', '" + Definitions.DEFAULT_MAX_GUA_NUM + "', '" + Definitions.DEFAULT_MAX_GUA_NUM + "', 'LOAN GUARANTORS', 'LOAN', 'admin', now(), 'N', now(), 'admin', 'STATIC', NULL, NULL, NULL),  "
                + " 		(NULL, 'Y', '" + branchId + "', '" + coyId + "', '" + Definitions.DEFAULT_MAX_LOAN_SUM + "', '" + Definitions.DEFAULT_MAX_LOAN_SUM + "', 'RUNNING LOAN SUM', 'LOAN', 'admin', now(), 'N', now(), 'admin', 'STATIC', NULL, NULL, NULL), "
                + " 		(NULL, 'Y', '" + branchId + "', '" + coyId + "', '" + Definitions.DEFAULT_MAX_LEN_STAY + "', '" + Definitions.DEFAULT_MAX_LEN_STAY + "', 'LENGTH OF STAY', 'LOAN', 'admin', now(), 'N', now(), 'admin', 'STATIC', NULL, NULL, NULL); ";

        sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
        sessionFactory.getCurrentSession().createSQLQuery(query2).executeUpdate();

        /**
         * ****************************************** AUTO INSERTION SECTION
         * FOR FISCAL PERIOD **********************************
         */
        /**
         * ****************************************** AUTO INSERTION SECTION
         * FOR FISCAL PERIOD **********************************
         */
        /**
         * ****************************************** AUTO INSERTION SECTION
         * FOR FISCAL PERIOD **********************************
         */
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);

        String query3 = "INSERT INTO `fiscal_period` (`ID`, `COMPANY_ID`, `BRANCH_ID`, `DELETED`, `ACTIVE`, `CREATED_BY`, `CREATION_DATE`, `LAST_MODIFICATION_DATE`, `LAST_MODIFIED_BY`, `NO_OF_PERIODS`, `YEAR`, `FY_END`, `FY_NAME`, `FY_START`) VALUES "
                + "(NULL, " + coyId + ", " + branchId + ", 'N', 'Y', 'admin', now(), now(), 'admin', 12, " + year + ", '" + year + "-12-31', '" + year + "', '" + year + "-01-31');";

        int id = sessionFactory.getCurrentSession().createSQLQuery(query3).executeUpdate();

        if (id > 0) {
            Integer fperiodId = 0;

            Query queryX = sessionFactory.getCurrentSession().createQuery("select id from FiscalPeriod where companyId=" + coyId + " and branchId='" + branchId + "'");

            for (Iterator it = queryX.iterate(); it.hasNext();) {
                fperiodId = Integer.valueOf(String.valueOf(it.next()));
            }

            //confirm this side does not take care of leap year
            String febmonthscrp = "		(NULL, " + year + ", '" + year + "-02-01 00:00:00', '" + year + "-02-28 00:00:00', 2, " + fperiodId + ", 'Y'),";
            if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                febmonthscrp = "		(NULL, " + year + ", '" + year + "-02-01 00:00:00', '" + year + "-02-29 00:00:00', 2, " + fperiodId + ", 'Y'),";
            } else {
                //not leap yr - inialized above;
            }

            //
            String query4 = "	INSERT INTO `fiscal_period_items` (`id`, `Year`, `FP_START`, `FP_END`, `period_id`, `fiscal_period_id`, `active`) VALUES "
                    + "		(NULL, " + year + ", '" + year + "-01-01 00:00:00', '" + year + "-01-31 00:00:00', 1, " + fperiodId + ", 'Y'),"
                    + febmonthscrp
                    + "		(NULL, " + year + ", '" + year + "-03-01 00:00:00', '" + year + "-03-31 00:00:00', 3, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-04-01 00:00:00', '" + year + "-04-30 00:00:00', 4, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-05-01 00:00:00', '" + year + "-05-29 00:00:00', 5, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-06-01 00:00:00', '" + year + "-06-30 00:00:00', 6, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-07-01 00:00:00', '" + year + "-07-31 00:00:00', 7, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-08-03 00:00:00', '" + year + "-08-31 00:00:00', 8, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-09-01 00:00:00', '" + year + "-09-30 00:00:00', 9, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-10-01 00:00:00', '" + year + "-10-30 00:00:00', 10, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-11-02 00:00:00', '" + year + "-11-30 00:00:00', 11, " + fperiodId + ", 'Y'),"
                    + "		(NULL, " + year + ", '" + year + "-12-01 00:00:00', '" + year + "-12-31 00:00:00', 12, " + fperiodId + ", 'Y');";

            sessionFactory.getCurrentSession().createSQLQuery(query4).executeUpdate();
        }
    }

    public void createDefaults(SessionFactory sessionFactory, String branchId, String coyId, int option) {
        createRoleDefault(sessionFactory, branchId, coyId, option);

        if (option == 2) {//only branch required
            /**
             * ***************************************************************************************
             * String query1= " INSERT INTO `serials` (`Series`, `Serial`,
             * `SerialLength`, `Prefix`, `Branch`, `Companyid`) VALUES " + "
             * ('DRef', 0, 6, 'DR', '"+branchId+"', '"+coyId+"'), " + " ('JV',
             * 0, 6, 'GL', '"+branchId+"', '"+coyId+"'), " + " ('LIA', 0, 6,
             * 'GL', '"+branchId+"', '"+coyId+"'), " + " ('LDD', 0, 6, 'GL',
             * '"+branchId+"', '"+coyId+"'), " + " ('LOR', 0, 6, 'GL',
             * '"+branchId+"', '"+coyId+"'), " + " ('Savref', 0, 6, 'SV',
             * '"+branchId+"', '"+coyId+"');";
             * ***************************************************************************************
             */
            String query1 = " insert into serials (`Series`, `Serial`, `SerialLength`, `Prefix`, `Branch`, `Companyid`) select Series,Serial,SerialLength,Prefix ,'" + branchId + "','" + coyId + "'  from serials_def  where active=1 ";

            /**
             * ***************************************************************************************
             * String query2= " INSERT INTO
             * `settings`(`Setting`,`Value`,`Branch`,`Companyid`) VALUES" + "
             * ('ACCOUNTINGMETHOD', 'ACCRUAL', '"+branchId+"', '"+coyId+"')," +
             * " ('INTERESTBASEDAYS', '365', '"+branchId+"', '"+coyId+"')," + "
             * ('PROCESSINGMETHOD', 'MANUAL', '"+branchId+"', '"+coyId+"')," + "
             * ('REAccount', '00000000','"+branchId+"', '"+coyId+"');";
             * ***************************************************************************************
             */
            String query2 = " INSERT INTO `settings`(`Setting`,`Value`,`Branch`,`Companyid`,Display) SELECT setting_name,default_val ,'" + branchId + "','" + coyId + "',display  from settings_def where active=1";
            //String query3="INSERT INTO products(`id`,`code`,`is_deleted`, `name`,`company_id`,`initial_amount_max`, `initial_amount_min`, `interest_rate_min`, `interest_rate_max`, `interest_rate`, `has_interest`, `segment_code`, `branch_id`, `product_type_code`, `CURRENCY_ID`, `is_default`, `PRODUCT_TYPE_ID`, `is_taxable`, `tax_code1`, `tax_code2`, `tax_code3`) VALUES (NULL, '001', '0', 'Savings Product', '"+coyId+"', '0', '0', '0', '0', '0', '0', '0', '"+branchId+"', 'S', '2', '0', NULL, '0', NULL, NULL, NULL);";

            sessionFactory.getCurrentSession().createSQLQuery(query1).executeUpdate();
            sessionFactory.getCurrentSession().createSQLQuery(query2).executeUpdate();
            //sessionFactory.getCurrentSession().createSQLQuery(query3).executeUpdate(); 			 
            sessionFactory.getCurrentSession().createSQLQuery("insert into branch_serial(company_id,serial_len,serial) values('" + coyId + "',3,1)").executeUpdate();
        }
    }

    public Date getTimeByZone(String dtimezone) {
        TimeZone timeZone = TimeZone.getTimeZone(dtimezone);
        Calendar rightNow = Calendar.getInstance(timeZone);
        return rightNow.getTime();
    }

    public double FormatTo2DecimalD(double input) {
        DecimalFormat df = new DecimalFormat("###.##");
        return Math.round(input * 100.0) / 100.0;
    }

    public String FormatTo2DecimalS(double input) {
        DecimalFormat df = new DecimalFormat("###.##");
        return df.format(input);
    }

    public String JavaToMySqlDateStr(java.util.Date inDate) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(inDate);
    }

    public HelperUtility getHelperUTIL() {
        return helperUTIL;
    }

    public void setHelperUTIL(HelperUtility helperUTIL) {
        this.helperUTIL = helperUTIL;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /*	public JndiObjectFactoryBean getDatasource(){
     return datasource;
     }

     public void setDatasource(JndiObjectFactoryBean datasource){
     this.datasource = datasource;
     }*/
    public void getRoleparameters() {
        String name = "";
        String password = "";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        String mySQLString;
        mySQLString = " select * FROM tblwebserv a where a.app = 'internal'";
        ResultSet agRecSet;

        try {
            agRecSet = dbobj.retrieveRecset(mySQLString);

            while (agRecSet.next()) {
                name = agRecSet.getString("user");
                password = agRecSet.getString("pwd");
            }

            dname = name;
            dpwd = password;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (dbobj != null) {
                dbobj.closecon();
            }
        }
    }

    public boolean copyFile(File src, File detination) throws IOException {
        boolean success = false;

        try {
            FileUtils.copyFile(src, detination);
            success = detination.exists();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return success;
    }

    public boolean createAndDispatchLoanScheduleToMember() {
        boolean success = false;

        return success;
    }

    public LoanPayOffBean getLPBean(String loanCaseId, LoanRequestService loanRequestService, ProductService productService,
            LoanRepaymentScheduleService loanRepaymentScheduleService) {
        LoanPayOffBean loanPayOffBean = null;
        List<LoanRepaymentSchedule> listX = loanRepaymentScheduleService.listPendingLoanRepaymentScheduleByLoanCaseId(loanCaseId);
        LoanRequest loan = loanRequestService.getLoanRequestByCaseId(loanCaseId);
        Product p = productService.getProductByTypeCode(loan.getLoanType());

        double loanTotal = 0.0;
        double interestTotal = 0.0;
        double principalTotal = 0.0;
        double penaltyTotal = 0.0;
        int delayDays = 0;
        double penaltyIncurred = 0.0;
        double scheduleAmount = 0.0;

        if (listX != null && listX.size() > 0) {
            loanPayOffBean = new LoanPayOffBean();

            for (LoanRepaymentSchedule itemX : listX) {
                scheduleAmount = itemX.getAmountPrincipal();
                delayDays = this.getDaysDiff(itemX.getExpectedRepaymentDate(), this.currentDate());

                if (delayDays > 0) {
                    penaltyIncurred = this.calculatePenalty(scheduleAmount, delayDays, p.getPenalty());
                } else {
                    penaltyIncurred = 0.0;
                }

                loanTotal += itemX.getExpectedRepaymentAmount();
                interestTotal += itemX.getAmountInterest();
                principalTotal += itemX.getAmountPrincipal();
                penaltyTotal += penaltyIncurred;
            }

            loanPayOffBean.setRepayTotAmt(loanTotal);
            loanPayOffBean.setRepayTotInt(interestTotal);
            loanPayOffBean.setRepayTotPrl(principalTotal);
            loanPayOffBean.setRepayPenalty(penaltyTotal);
        }

        return loanPayOffBean;
    }

    public boolean generateLoanScheduleForDispatch(ArrayList<LoanRepaymentSchedule> schedules, LoanRequest loan, MemberView member, Company coy) {
        boolean mailSent = false;
        PDFUtility PDF = new PDFUtility();
        String uploadPath = this.getReportLocation() + "/";
        String pdfFilename = member.getCompanyId() + member.getBranchId() + System.currentTimeMillis() + ".pdf";
        boolean success = false;

        try {
            success = PDF.createLoanSchedulePDF(uploadPath, pdfFilename, schedules, loan, member, coy);
            //success=PDF.createPDF(uploadPath, pdfFilename, schedules, loan, member, coy);

            String filename = uploadPath + "/" + pdfFilename;

            if (success) {

                //send a copy of the schedule to customer
                try {
                    //mail bean setup
                    MailBean MB = new MailBean();
                    MB = getMailConfig();
                    MB.setToemail(member.getEmail());
                    MB.setSubject(Definitions.MAIL_SUBJECT_LOAN_APPROVAL_SUCCESS);

                    MB.setAttachments(new String[]{filename});
                    MB.setMailBody("");

                    String uri = "";

                    try {
                        uri = (String) new javax.naming.InitialContext().lookup("java:comp/env/app.uri");
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }

                    String mailBody = "	<style type=\"text/css\">"
                            + "	<!--"
                            + "	.style2 {"
                            + "		font-family: Verdana, Arial, Helvetica, sans-serif;"
                            + "		font-size: 14px;"
                            + "	}"
                            + "	-->"
                            + "	</style>"
                            + "	 <p class=\"style2\">Hello " + member.getFirstname() + ", </p>"
                            + "	<p class=\"style2\">Your Loan request has been approved see details below. <br>"
                            + "   </p>"
                            + "	<table width=\"550\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
                            + "	  <tr>"
                            + "	    <td width=\"50%\"><span class=\"style2\">Loan Ref No:</span></td>"
                            + "	    <td width=\"50%\"><span class=\"style2\">" + loan.getLoanCaseId() + "</span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Loan Amount:</span></td>"
                            + "	    <td><span class=\"style2\">" + loan.getApprovedAmount() + " </span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Rate Applied(P/A):</span></td>"
                            + "	    <td><span class=\"style2\">" + loan.getAppliedRate() + " </span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Total Interest Applied:</span></td>"
                            + "	    <td><span class=\"style2\">" + loan.getLoanIntTotal() + " </span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">No Of Installments:</span></td>"
                            + "	    <td><span class=\"style2\">" + loan.getNoOfInstallments() + " </span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Proposed Payment Start Date:</span></td>"
                            + "	    <td><span class=\"style2\">" + loan.getProposedCommencementDate() + " </span></td>"
                            + "	  </tr>"
                            + "	  <tr>"
                            + "	    <td><span class=\"style2\">Company Name:</span></td>"
                            + "	    <td><span class=\"style2\">" + coy.getName() + " </span></td>"
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                            + "     <tr>"
                            + "	    <td colspan=2><hr/></td>"
                            + "	    "
                            + "	  </tr>"
                            + "	</table>"
                            + "	<p class=\"style2\"><br>"
                            + "	  Thanks </p> ";

                    MB.setMailBody(mailBody);

                    //send email
                    this.sendMail(MB);

                    // send mail async
                    //sendMailasync(MB);
                    mailSent = true;
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        } catch (RuntimeException e) {
            //scheduleFile=null;
            e.printStackTrace();
        }

        return mailSent;
    }

    public double roundDouble(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public double roundFloat(float value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    public String getFileNameFromPathNew(String fullPath) {
        String filename = "";

        if (fullPath != null && fullPath.length() > 0) {
            int index = fullPath.lastIndexOf(File.pathSeparator);
            filename = fullPath.substring(index + 1);
        }

        return filename;
    }

    public String getFileNameFromPath(String fullPath) {
        String filename = "";

        if (fullPath != null && fullPath.length() > 0) {
            int index = fullPath.lastIndexOf("\\");
            filename = fullPath.substring(index + 1);
        }

        return filename;
    }

    // new addition for loan interest accrued
    public double getLoanAccruedInterest(String loanacctno, java.util.Date loancommencementdate, String branchId, String coyId, String intaccruedacct) {
        double accrint = 0.0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tempDate = formatter.format(loancommencementdate);
        String sql = "SELECT sum(debit) as totdebit FROM txns a inner join txnsheader b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.companyid = b.companyid and a.branchid = b.branchid where a.accountno = '" + intaccruedacct + "' and a.docref = '" + loanacctno + "' and a.txntype = 'LIAP' and b.postdate >= {d '" + tempDate + "'} and a.branchid = " + branchId + " and a.companyid = " + coyId;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);

        accrint = (query == null || query.list().isEmpty()) ? 0.0 : (Double) query.list().get(0);
        return accrint;
    }

    public double getLoanAccruedInterestpaid(String loanid, java.util.Date loancommencementdate, String branchId, String coyId, String intaccruedacct) {
        double accrint = 0.0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String tempDate = formatter.format(loancommencementdate);
        String sql = "SELECT sum(credit) as totamt FROM txns a inner join txnsheader b on a.txntype = b.txntype and a.txnserial = b.txnserial and a.companyid = b.companyid and a.branchid = b.branchid  where a.accountno = '" + intaccruedacct + "' and a.docref = '" + loanid + "' and a.txntype = 'LOR' and a.transactioncode = 'LIA' and b.postdate >= {d '" + tempDate + "'} and a.branchid = " + branchId + " and a.companyid = " + coyId;
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        accrint = (query == null || query.list().isEmpty()) ? 0.0 : (Double) query.list().get(0);
        return accrint;
    }
    
    public HashMap getCoopswthSMS() {
        HashMap coopwthsms;
        coopwthsms = new HashMap();
        String sql = "select distinct companyid FROM settings_coop a where a.setting = 'ENABLESMSNOTIF' and a.value = 'YES'";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        for(Iterator it=query.iterate();it.hasNext();){  
 	     coopwthsms.put(it.next(),it.next());  
        }
        return coopwthsms;
    }
}
