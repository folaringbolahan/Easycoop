/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sift.webservice.controller;

import com.sift.webservice.model.LoanGuarantorWS;
import com.sift.webservice.model.LoanRequestWS;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author user
 */
public class DummyController {
    
    /*
    @RequestMapping(value = "/webserviceOLD3", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveGuarantor(HttpServletRequest request, LoanRequestWS loanRequestWS) throws Exception{       
       
        String a = request.getParameter("d");
        
        // first guarantor
        LoanGuarantorWS lg1 = new LoanGuarantorWS();
        lg1.setAcceptanceStatus("N");
        lg1.setBranchId(request.getParameter("branchid"));
        lg1.setCompanyId(request.getParameter("companyid"));
        lg1.setGuarantorNo(request.getParameter("guarantorno1"));
        lg1.setLoanCaseId(request.getParameter("loancaseid"));
        lg1.setMemberNo(request.getParameter("memberno"));
        lg1.setRequestDate(new java.util.Date());
        
        //Second Guarantor
        LoanGuarantorWS lg2 = new LoanGuarantorWS();
        lg2.setAcceptanceStatus("N");
        lg2.setBranchId(request.getParameter("branchid"));
        lg2.setCompanyId(request.getParameter("companyid"));
        lg2.setGuarantorNo(request.getParameter("guarantorno2"));
        lg2.setLoanCaseId(request.getParameter("loancaseid"));
        lg2.setMemberNo(request.getParameter("memberno"));
        lg2.setRequestDate(new java.util.Date());        
        
        LoanRequestWS lr = new LoanRequestWS();
        lr.setCompanyId(request.getParameter("companyid"));
        lr.setBranchId(request.getParameter("branchid"));
        lr.setMemberNo(request.getParameter("memberno"));
        lr.setLoanType(request.getParameter("loanType"));
        //lr.setRequestedAmount(request.getp);
        lr.setLastModificationDate(new java.util.Date());
        //int id = loanRequestWS.getId();
        String companyId = loanRequestWS.getCompanyId();
        String branchId = loanRequestWS.getBranchId();
        String memberNo = loanRequestWS.getMemberNo();
        String loanType = loanRequestWS.getLoanType();
        //Date requestDate = loanRequestWS.getRequestDate();
        double requestedAmount = loanRequestWS.getRequestedAmount();
        String lastModifiedBy = loanRequestWS.getLastModifiedBy();

         loanRequestServiceWS.saveLoan(loanRequestWS);
         loanGuarantorService.saveGuarantor(lg1);
         loanGuarantorService.saveGuarantor(lg2);
         
        System.out.println("companyId: " + companyId);
        System.out.println("branchId: " + branchId);
        System.out.println("memberNo: " + memberNo);
        System.out.println("loanType: " + loanType);
        //System.out.println("requestDate: " + requestDate);
        System.out.println("requestedAmount: " + requestedAmount);
        System.out.println("LastModifiedBy: " + lastModifiedBy);

        return "DONE : " + loanRequestWS;
    }
    
    
    @RequestMapping(value = "/webserviceOLD2", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveLoanRequestWSOLD2(@RequestBody LoanRequestWS loanRequestWS) {
       
        String companyId = loanRequestWS.getCompanyId();
        String branchId = loanRequestWS.getBranchId();
        String memberNo = loanRequestWS.getMemberNo();
        String loanType = loanRequestWS.getLoanType();
        //Date requestDate = loanRequestWS.getRequestDate();
        double requestedAmount = loanRequestWS.getRequestedAmount();
        String lastModifiedBy = loanRequestWS.getLastModifiedBy();
       
        loanRequestServiceWS.saveLoan(loanRequestWS);
        //System.out.println("ID: " + id);
        System.out.println("companyId: " + companyId);
        System.out.println("branchId: " + branchId);
        System.out.println("memberNo: " + memberNo);
        System.out.println("loanType: " + loanType);
        //System.out.println("requestDate: " + requestDate);
        System.out.println("requestedAmount: " + requestedAmount);
        System.out.println("LastModifiedBy: " + lastModifiedBy);

        return "DONE : " + loanRequestWS;
    }


    @RequestMapping(value = "/saveLoanRequest", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView saveLoanRequestWS2(@ModelAttribute(value = "loanrequestws") LoanRequestWS loanrequestws) {

        System.out.println("loanrequestws : " + loanrequestws);
        //loanRequestServiceImplWS.saveLoanRequest(id, companyId, branchId, memberNo, loanType,requestDate, requestedAmount, lastModifiedBy);
        loanRequestServiceWS.saveLoan(loanrequestws);
        
        return new ModelAndView("redirect:/loanrequest.htm");
    }

    */
    
    
    
    /*
    // web service call 
    
       private static WebResource webResource;
    private static Client client;
    private static String BASE_URI;
    private String dpwd;
    private String dname;
    
    // Web service call to easycoop
    public String getCreateCoopURI() {
        String uri = "";

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uri = (String) ctx.lookup("java:comp/env/easycoopwscreatecoop");
            //BASE_URI = uri;            
        } catch (NamingException nx) {
            System.out.println("Error number exception" + nx.getMessage().toString());
        }

        return uri;
    }
    
    // Get EasyCoop Web service key for Coop Creation
    public void getEasyCoopRoleparameters() {
        String name = "";
        String password = "";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        String mySQLString;
        mySQLString = "select * FROM tblwebserv a where a.app = 'easycoop'";
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
    
    // call to easycoop
    public String createCooperative(String jsonstring) {
        //Transaction Entry Object with headers & content
        String str = null;
        //String aa = "{\"password\":\"password123\",\"name\":\"kunletoks\",\"email\":\"kukusf31911@sifthoyx.com\",\"username\":\"kunleawotunbo\"}";
        //String teststring = "{\"email\": \"kukusf31211@sifthoyx.com\",\"name\": \"kukusf312\",\"username\": \"kukusf31211\",\"password\": \"olaola\"}";
        System.out.println("jsonstring : " + jsonstring);
        try {
            BASE_URI = getCreateCoopURI();
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getEasyCoopRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            // http://localhost/cmsdevbayo/index.php?option=com_api&app=users&format=raw&resource=users
            //String json= "&key= "+dpwd+"&";
            webResource = client.resource(BASE_URI).queryParam("key", dpwd).queryParam("jsonstring", jsonstring);
            
            System.out.println("***WebResource: " + webResource);
            //JsonNodeFactory 
            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
            System.out.println("Server response : " + response.getStatus());

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus() + ". Operation failed");
                
            }
            if (response.getEntity(String.class) != null){
            System.out.println("Response Length : " + response.getLength());
            System.out.println("Response Info : " + response.getStatusInfo());
            //String output = response.getEntity(String.class);
            System.out.println("Server response : " + response.getEntity(String.class));
            }else{
                System.out.println("Cooperative not created....");
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getEntity(String.class) + ". Operation failed");                
            }
            
            //System.out.println("Response Length : " + response.getLength());
            //System.out.println("Response Info : " + response.getStatusInfo());
            String output = response.getEntity(String.class);
            System.out.println("Server response : " + output);
            //System.out.println(output);
            str = output;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }
    
    
    public String toJson(String email, String name, String username, String password) throws IOException{
        // http://www.studytrails.com/java/json/jackson-create-json.jsp
        
        JSONObject obj = new JSONObject();        
        
        //obj.put("username", new Double(1000.21));        
        obj.put("password", password);
        obj.put("username", username);
        obj.put("name", name);
        obj.put("email", email);

        System.out.print(obj);
    
        
        return obj.toJSONString();
    }
    
    */
    
    
    /*
    
    <!-- url for cooperative creation on easycoop -->
     <env-entry>
    <description>Webservice URl Create Cooperative</description>
    <env-entry-name>easycoopwscreatecoop</env-entry-name>
    <env-entry-type>java.lang.String</env-entry-type>
    <env-entry-value>http://localhost/cmsdevbayo/index.php?option=com_api&amp;app=users&amp;format=raw&amp;resource=users</env-entry-value>
    </env-entry>
    
    */
    
    
    /*
        WebServiceUtility webServiceUtility = new WebServiceUtility();
      //call to web service
                 String email = "kunleawotungffgbo@gmail.com";
                 String name = "KunleAwotfgfungfgboCoop";
                 String username = "kunleawotfggunbo";
                 String password = "passworffgd123";
             try {
                 webServiceUtility.createCooperative(webServiceUtility.toJson(companyBean.getEmail(), companyBean.getName(), companyBean.getShortName(), companyBean.getRegNo()));
             } catch (IOException ex) {
                 Logger.getLogger(CompanyController.class.getName()).log(Level.SEVERE, null, ex);
             }
             
    
    */
    
    /*
    if (good) {
            try {
                int coopid = 0;
                try {
                    coopid = Integer.parseInt(obj.getCompanyId());
                } catch (NumberFormatException nuex) {

                }
                Company comp = companyService.getCompany(coopid);
                if (obj.getConnectToEazyCoop().equalsIgnoreCase("Y") ) {
                    String resource = "coop";

                    webServiceUtility.webserviceClient(resource, webServiceUtility.createCoop(obj.getCompanyId(),
                            obj.getId().toString(), comp.getName(), obj.getBranchName()));
                }
                eazyCoopUTIL.LogUserAction(activity);

            } catch (Exception ex) {
            }
        }
    */
    
    // http://www.journaldev.com/2562/java-readwrite-excel-file-using-apache-poi-api
    
    // http://www.mkyong.com/spring-mvc/spring-mvc-export-data-to-excel-file-via-abstractexcelview/
    
    // http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
    
    // http://www.shaunabram.com/springmvc-file-download/
}
