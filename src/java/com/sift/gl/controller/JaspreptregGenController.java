/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.gl.controller;

/**
 *
 * @author ABAYOMI AWOSUSI
 */
import com.sift.gl.*;
import com.sift.gl.dao.CurrentuserdisplayImpl;
import com.sift.gl.model.AllItempara;
import com.sift.votes.model.VotAgm;
import com.sift.votes.service.VotAgmService;
import com.sift.votes.service.VotCompanyService;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
// import com.aspose.cells.jasperreports. ACXlsExporter;
//import com.aspose.cells.jasperreports.ACXlsExporterParameter;

/** controller handles the jasper report engine for reporting
 *
 * @author yomi
 */
@Controller
@RequestMapping("reports/reg/")
public class JaspreptregGenController  implements Serializable,ServletContextAware {
   private String fileformatgroup; 
   private String printgroup;
   private String repid;
   String did="";
   Connection connection;
   HttpServletResponse response;
   InputStream reportStream;
   ServletOutputStream servletOutputStream;
   GendataService dbobj;
   HashMap parameters;
   JasperPrint jasperPrint;
   JRFileVirtualizer virtualizer;
  
   private CurrentuserdisplayImpl currentuserdisplayService; //user;
   @Autowired
    ServletContext context;
  
   @Autowired
   VotCompanyService votCompanyService;
   
   @Autowired
   VotAgmService votAgmService;
    /**
     *
     * @param repid
     */
    public void setRepid(String repid) {
        this.repid = repid;
    }
    /**
     *
     * @return
     */
    public String getRepid() {
        return this.repid;
    }
    /**
     *
     * @return
     */
    public String getFileformatgroup() {
        return this.fileformatgroup;
    }
    /**
     *
     * @param fileformatgroup
     */
    public void setFileformatgroup(String fileformatgroup) {
        this.fileformatgroup = fileformatgroup;
    } 
    /**
     *
     * @return
     */
    public String getPrintgroup() {
        return this.printgroup;
    }
    /**
     *
     * @param printgroup
     */
    public void setPrintgroup(String printgroup) {
        this.printgroup = printgroup;
    } 
  
    public void setCurrentuserdisplayService(CurrentuserdisplayImpl currentuserdisplayService) {
     this.currentuserdisplayService = currentuserdisplayService ;//(user.getCurruser()); //= user;
   }
    
    /**
     *
     * @param servletContext
     */
    public void setServletContext(ServletContext servletContext) {
     this.context = servletContext;
   } 
    
    /** pass generic parameters to reports
     *
     * @param request
     * @param response
     * @param rid
     * @param parameterobject
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws JRException
     */
    public void init(HttpServletRequest request,HttpServletResponse response,String rid,AllItempara parameterobject)  throws ClassNotFoundException, SQLException, IOException,JRException{
      String reppath ="";
      String repidx;
      repidx = rid;
      repid = repidx;
      fileformatgroup = "html";
      printgroup = "html";
      
      
      reportStream = context.getResourceAsStream("/reports/reg/" + repid + ".jasper");
      servletOutputStream = response.getOutputStream();
      reppath = context.getRealPath("/reports/reg/");
      //////
      virtualizer =  new JRFileVirtualizer(2, reppath + "\\virtmp\\");
      dbobj = new GendataService();
      dbobj.inimkcon();
      connection = dbobj.getConnection();
      parameters = new HashMap();
      parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
      //parameters.put("companyname", currentuserdisplayService.getCurrusercompany().getName());
      //parameters.put("companyid", currentuserdisplayService.getCurruser().getCompanyid());
      //parameters.put("postdate", currentuserdisplayService.getCurrusercompany().getPostDate());
      parameters.put("SUBREPORT_DIR", reppath + "\\");
      
      String reqatt = "N";
     
      if ((parameterobject!=null)&&(parameterobject.getParatype()!=null))
      {
          reqatt = parameterobject.getParatype();
          //System.out.println("just checking" + parameterobject.toString());
      }
      //System.out.println("just checking" + reqatt);
      if (reqatt.equalsIgnoreCase("P")) {
        parameters.put("year", parameterobject.getYear());
        parameters.put("period", parameterobject.getPeriodId());
       // System.out.println("period and year " + parameterobject.getPeriodId() + " " + parameterobject.getYear());
      }
      if ((reqatt.equalsIgnoreCase("AD"))) {
        //  System.out.println("date " + parameterobject.getStartdatestr() + " " + parameterobject.getEnddatestr());
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date startdate=null;
        java.util.Date enddate=null;
       try {
         startdate = formatter1.parse(parameterobject.getStartdatestr());
         enddate = formatter1.parse(parameterobject.getEnddatestr());
        } catch(ParseException pEx ) 
        {  
           System.out.println("Wrong date format in report parameters");
        }  
        parameters.put("startdate", startdate);
        parameters.put("enddate", enddate);
        //System.out.println("called ad");
     }
      if ((reqatt.equalsIgnoreCase("AG"))||(reqatt.equalsIgnoreCase("AD"))) {
        parameters.put("agmid", parameterobject.getAgmid());
        //pass parameter agm description
        //pass parameter agm details - start date and time - enddate and time
        VotAgm va = votAgmService.getAgmdetails(parameterobject.getAgmid());
        parameters.put("agmdescription", va.getDescription());
        parameters.put("datedetails", "Starts - " + va.getStartdate() + " : " + va.getStarttime() + " - Ends - " + va.getEnddate() + " : " + va.getEndtime());
        parameters.put("companyname", votCompanyService.getVotCompany(parameterobject.getAgmid()).getCompanyname());
        parameters.put("companyid", va.getCompanyid());
        System.out.println("called ag"  + votCompanyService.getVotCompany(parameterobject.getAgmid()).getCompanyname());
      }
     
      
   } 
   
   
    /** generate pdf version
     *
     * @param request
     * @param response
     * @param repid
     * @param mapping1FormObject
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws JRException
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "{repid}/pdf", method = {RequestMethod.GET,RequestMethod.POST})
   @ResponseBody 
   public void generateReportpdf(HttpServletRequest request,HttpServletResponse response, @PathVariable String repid,@ModelAttribute("reportdet") AllItempara mapping1FormObject) throws ClassNotFoundException, SQLException, IOException,JRException,ServletRequestBindingException
    {
      String drep = repid; //ServletRequestUtils.getRequiredStringParameter(request, "repid");
     init(request,response,drep,mapping1FormObject);
      jasperPrint = JasperFillManager.fillReport(reportStream, parameters, connection);
      //////
      virtualizer.setReadOnly(true);
       servletOutputStream=response.getOutputStream();
         
      JRPdfExporter exporter = new JRPdfExporter();
      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
      exporter.exportReport();
      connection.close();
      dbobj.closecon();
      servletOutputStream.flush();
      servletOutputStream.close();
    }
   
    /** generate html version
     *
     * @param request
     * @param response
     * @param repid
     * @param mapping1FormObject
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws JRException
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "/{repid}/html", method = {RequestMethod.GET,RequestMethod.POST})
   @ResponseBody 
   public void generateReportHTML(HttpServletRequest request,HttpServletResponse response, @PathVariable String repid,@ModelAttribute("reportdet") AllItempara mapping1FormObject) throws ClassNotFoundException, SQLException, IOException,JRException,ServletRequestBindingException
    {
      String drep = repid; //ServletRequestUtils.getRequiredStringParameter(request, "repid");
      //FiscalPeriodItempara dreq = (FiscalPeriodItempara) mapping1FormObject;
      init(request,response,drep,mapping1FormObject);
      jasperPrint = JasperFillManager.fillReport(reportStream, parameters, connection);
      request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
      virtualizer.setReadOnly(true);
     
        HtmlExporter exporterHTML = new HtmlExporter();
        SimpleExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
        exporterHTML.setExporterInput(exporterInput);

        SimpleHtmlExporterOutput exporterOutput;
        
            exporterOutput = new SimpleHtmlExporterOutput(response.getOutputStream());
            exporterOutput.setImageHandler(new WebHtmlResourceHandler("../image?image={0}"));
            exporterHTML.setExporterOutput(exporterOutput);

            SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
            reportExportConfiguration.setWhitePageBackground(false);
            ///reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            exporterHTML.setConfiguration(reportExportConfiguration);
            exporterHTML.exportReport();
            connection.close();
            dbobj.closecon();
    }
    /** generate xls version
     *
     * @param request
     * @param response
     * @param repid
     * @param mapping1FormObject
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws JRException
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "/{repid}/xls", method = {RequestMethod.GET,RequestMethod.POST})
   @ResponseBody 
    
    
   public void generateReportExcel(HttpServletRequest request,HttpServletResponse response, @PathVariable String repid,@ModelAttribute("reportdet") AllItempara mapping1FormObject) throws ClassNotFoundException, SQLException, IOException,JRException,ServletRequestBindingException
    {
      String drep = repid; //ServletRequestUtils.getRequiredStringParameter(request, "repid");
      init(request,response,drep,mapping1FormObject);
      jasperPrint = JasperFillManager.fillReport(reportStream, parameters, connection);
      virtualizer.setReadOnly(true);
      //servletOutputStream=response.getOutputStream();
      response.setContentType("application/vnd.ms-excel");
      response.addHeader("Content-disposition","attachment; filename=" + repid + ".xls");
      servletOutputStream=response.getOutputStream();
      
      JRXlsExporter exporter = new JRXlsExporter();
      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
      
      /////
      SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
     configuration.setOnePagePerSheet(false);
       configuration.setIgnoreGraphics(false);
      configuration.setCollapseRowSpan(true);
      configuration.setDetectCellType(false);
      configuration.setIgnoreCellBackground(true);
      configuration.setWhitePageBackground(false);
      configuration.setRemoveEmptySpaceBetweenColumns(true);
      configuration.setRemoveEmptySpaceBetweenRows(true);
      //configuration.
      //configuration.setShowGridLines(true);
      exporter.setConfiguration(configuration);
      exporter.exportReport();
     
      connection.close();
      dbobj.closecon();
      servletOutputStream.flush();
      servletOutputStream.close();  
    }
   
    
    /** generate doc version
     *
     * @param request
     * @param response
     * @param repid
     * @param mapping1FormObject
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws JRException
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "/{repid}/doc", method = {RequestMethod.GET,RequestMethod.POST})
   @ResponseBody 
   public void generateReportDOC(HttpServletRequest request,HttpServletResponse response, @PathVariable String repid,@ModelAttribute("reportdet") AllItempara mapping1FormObject) throws ClassNotFoundException, SQLException, IOException,JRException,ServletRequestBindingException
    {
      String drep = repid; //ServletRequestUtils.getRequiredStringParameter(request, "repid");
      init(request,response,drep,mapping1FormObject);
      jasperPrint = JasperFillManager.fillReport(reportStream, parameters, connection);
      virtualizer.setReadOnly(true);
      response.addHeader("Content-disposition","attachment; filename=" + repid + ".docx");
      servletOutputStream=response.getOutputStream();
      JRDocxExporter exporter = new JRDocxExporter();
      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(servletOutputStream));
      exporter.exportReport();
      connection.close();
      dbobj.closecon();
      servletOutputStream.flush();
      servletOutputStream.close();
    } 
    
    /** generate csv version 
     *
     * @param request
     * @param response
     * @param repid
     * @param mapping1FormObject
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     * @throws JRException
     * @throws ServletRequestBindingException
     */
    @RequestMapping(value = "/{repid}/csv", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody 
    public void generateReportCSV(HttpServletRequest request,HttpServletResponse response, @PathVariable String repid,@ModelAttribute("reportdet") AllItempara mapping1FormObject) throws ClassNotFoundException, SQLException, IOException,JRException,ServletRequestBindingException
    {
      String drep = repid; //ServletRequestUtils.getRequiredStringParameter(request, "repid");
      init(request,response,drep,mapping1FormObject);
      jasperPrint = JasperFillManager.fillReport(reportStream, parameters, connection);
      virtualizer.setReadOnly(true);
      response.addHeader("Content-disposition","attachment; filename=" + repid + ".csv");
      servletOutputStream=response.getOutputStream();
      JRCsvExporter exporter = new JRCsvExporter();
      exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
      exporter.setExporterOutput(new SimpleWriterExporterOutput(servletOutputStream));
      exporter.exportReport();
      connection.close();
      dbobj.closecon();
      servletOutputStream.flush();
      servletOutputStream.close();
    } 
     
   
}
