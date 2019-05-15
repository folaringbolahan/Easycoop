/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.webservice.utility;


import com.sift.gl.GendataService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import org.json.simple.JSONObject;

/**
 *
 * @author Olakunle Awotunbo
 */
public class WebServiceUtility {

    private static WebResource webResource;
    private static Client client;
    private static String BASE_URI;
    private String dpwd;
    private String dname;

    // Web service call to easycoop create coop
    public String getEasyCoopURI() {
        String uri = "";

        try {
            javax.naming.Context ctx = new javax.naming.InitialContext();
            uri = (String) ctx.lookup("java:comp/env/easycoopbaseurl");
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

    
    public String createCoop(String compId, String branchId, String compname, String brname, String shtname) {
        /*
        
         if (obj.getConnectToEazyCoop().equalsIgnoreCase("Y")){
         String resource = "coop";

         webServiceUtility.webserviceClient(resource, webServiceUtility.createCoop(obj.getCompanyId(), 
         obj.getBranchCode(), obj.getCompanyName(), obj.getBranchName()));
         }
         if (obj.getConnectToEazyCoop().equalsIgnoreCase("Y")){
         String resource = "coop";

         webServiceUtility.webserviceClient(resource, webServiceUtility.createCoop(obj.getCompanyId(), 
         obj.getBranchCode(), obj.getCompanyName(), obj.getBranchName()));
         }
        
         */
        JSONObject obj = new JSONObject();

        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("coname", compname);
        obj.put("brname", brname);
        obj.put("shtname", shtname);
        return obj.toJSONString();
    }

    public String prodLoan(int productId, int compId, int branchId, String proname, String procode) {

        JSONObject obj = new JSONObject();

        obj.put("efid", productId);
        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("proname", proname);
        obj.put("procode", procode);

        return obj.toJSONString();
    }

    public String prodOffer(int productId, int compId, int branchId, String proname, String procode) {
        /*
         if(product.getProductTypeCode().equalsIgnoreCase("L")){
                String resource = "prodloan";
                System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());                
                webServiceUtility.webserviceClient(resource, webServiceUtility.prodLoan(product.getId(),
                        user.getCurruser().getCompanyid(), user.getCurruser().getBranch(), 
                         product.getName(), product.getCode()));
                }else if(product.getProductTypeCode().equalsIgnoreCase("P")){
                System.out.println("ProdcutTypeCode = " + product.getProductTypeCode());
                webServiceUtility.webserviceClient("prodoffer", webServiceUtility.prodOffer(product.getId(),
                        user.getCurruser().getCompanyid(), user.getCurruser().getBranch(), 
                         product.getName(), product.getCode()));
                }
         */
        JSONObject obj = new JSONObject();

        obj.put("efid", productId);
        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("proname", proname);
        obj.put("procode", procode);

        return obj.toJSONString();
    }

    public String createUser(String email, String name, String username, String password) throws IOException {
       
        JSONObject obj = new JSONObject();
            
        obj.put("password", password);
        obj.put("username", username);
        obj.put("name", name);
        obj.put("email", email);

        return obj.toJSONString();
    }

    // create member
     public String createMember(int compId, int branchId, String username, String regdate,
            String memberno, String phone, String name, String email,String address) throws IOException {
        
         /*
         http://localhost/phpweb/cmsdevbayo/index.php?option=com_api&app=siftmap&
         format=raw&resource=user&key=73423e4a4bd4d8b9a224c566e8079b85&sj=
         {"efid": "17","gcid": "31","gbid": "33","username": "Ade Adekunle Ojo",
         "regdate": "2010-09-09","memberno": "3300001","phone": "09080987655",
         "name": "ades","email": "weee@sifthoyx.com","address": "No 1 ade street, palmgrove"}	         
         */        
        /*
         // call to create member
         String resource = "user";
         webServiceUtility.webserviceClient(resource, webServiceUtility.createMember
        (compId, branchId, username, regdate, memberno, phone, name, email, address));
        
        */
         
        JSONObject obj = new JSONObject();        

        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("username", username);
        obj.put("regdate", regdate);                 
        obj.put("memberno", memberno);
        obj.put("phone", phone);
        obj.put("name", name);
        obj.put("email", email);
        obj.put("address", address);

        return obj.toJSONString();
    }
     
     // update member address
    public String updateMemberAddress(int compId, int branchId, String memberno, String address) throws IOException {
        
        /*
         // Return corresponding member address using member_id
        String address = loanGuarantorService.getMemberCorreAddress("45");
        
        System.out.println("Member Address  : " + address);
        */
        
        JSONObject obj = new JSONObject();        

        obj.put("gcid", compId);
        obj.put("gbid", branchId);                    
        obj.put("memberno", memberno);      
        obj.put("address", address);

        return obj.toJSONString();
    }
     
     
    // approve or reject loan from easycoop
    public String apprOrRejEasyCoopLoan(int easyCoopLoanId, 
            String rejected, String rejectReason, String approve){
      
        //sj={"reqid": "38","rej": "0","rejres": "Loan Approved","apr": "1"}
        JSONObject obj = new JSONObject();        
        //String resource=uploanreq;
        // To approve loan set apr = 1 and rej = 0
        //To reject loan set apr = 0 and rej = 1
        obj.put("reqid", easyCoopLoanId);
        obj.put("rej", rejected);
        obj.put("rejres", rejectReason);
        obj.put("apr", approve);

        return obj.toJSONString();
    }
    
    // create other savings type
    public String prodSav(int productId, int compId, int branchId, String proname, String procode) {
        
        JSONObject obj = new JSONObject();

        obj.put("efid", productId);
        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("proname", proname);
        obj.put("procode", procode);

        return obj.toJSONString();
    }
    
    // to delete product
    public String deleteProduct(String method, int productId, int compId, int branchId, String ptype, String procode) {
        
        JSONObject obj = new JSONObject();

        obj.put("efid", productId);
        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("ptype", ptype);
        obj.put("procode", procode);
        obj.put("method", method);

        return obj.toJSONString();
    }
    
    // create coop admin on easycoop
    public String createCoopAdmin(int compId, int branchId, String username, String regdate,
            String memberno, String phone, String name, String email,String address, String coopprf){
    
        JSONObject obj = new JSONObject();        

        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("username", username);
        obj.put("regdate", regdate);                 
        obj.put("memberno", memberno);
        obj.put("phone", phone);
        obj.put("name", name);
        obj.put("email", email);
        obj.put("address", address);
        obj.put("coopprf", coopprf);
       
        return obj.toJSONString();
    }
    
     // create member
     public String checkifrecexists(int compId, int branchId, String rectype,String reccode) throws IOException {
        
        
        JSONObject obj = new JSONObject();        

        obj.put("gcid", compId);
        obj.put("gbid", branchId);
        obj.put("rectype", rectype);
        obj.put("reccode", reccode);
        return obj.toJSONString();
    }
 
    // call to easycoop
    public String webserviceClient(String resource, String jsonstring) {

        String str = null;
        //System.out.println("jsonstring : " + jsonstring);
        try {
            BASE_URI = getEasyCoopURI();
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            getEasyCoopRoleparameters();
            client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
            //http://localhost/phpweb/cmsdevbayo/index.php?option=com_api&app=siftmap&format=raw&resource=prodoffer&key=263d3eb93a7f00b1e9ad32109ec2ee81&
            //sj={"efid": "17","gcid": "31","gbid": "33","proname": "Electronics Christmas Purchases","procode": "PUR001"}	
            webResource = client.resource(BASE_URI).queryParam("resource", resource).queryParam("key", dpwd).queryParam("sj", jsonstring);

            System.out.println("***WebResource: " + webResource);
           
            ClientResponse response = webResource.accept("application/json").post(ClientResponse.class);
            //System.out.println("Server response : " + response.getStatus());

            if (response.getStatus() != 200) {
           // System.out.println("**** ERROR ****  ");
            //System.out.println("Response Length : " + response.getLength());
           // System.out.println("Response Info : " + response.getStatusInfo());
            String output = response.getEntity(String.class);
            System.out.println("Server response : " + output);
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus() + ". Operation failed");

            } 
           // System.out.println("Response Length : " + response.getLength());
            //System.out.println("Response Info : " + response.getStatusInfo());
            String output = response.getEntity(String.class);
            System.out.println("Server response : " + output);
            str = output;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }
    public String useremailupdate(int companyid, int branchid, String memberno, String oldemail, String newemail ) {
      
        JSONObject obj = new JSONObject();

        obj.put("gcid",companyid);
        obj.put("gbid", branchid);
        obj.put("memberno", memberno);
         obj.put("oemail",  oldemail);
         obj.put("nemail",  newemail);
        return obj.toJSONString();
    }
}
