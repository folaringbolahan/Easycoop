package com.sift.loan.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.TimeZone;

import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.model.Txnsheader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class ServiceTester{
	private static WebResource webResource;
    private static Client client;
    private static final String BASE_URI = "http://localhost:7070/EasycoopfinTEST3/webserv";

    public static ClientResponse create_XML(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse create_JSON(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(ClientResponse.class, requestEntity);
    }
    
    public static void main(String[] args){
    	new ServiceTester().doTestService(1);
    }

    public void doTestService(int option){       
    	if(option==2){      
    	    					  //txnType,txnCode,accountNo, valueDate,   docref , headerdocref, narrative,    txnSerial, ccyAmount, amount,  rate, userId,    branchId,year, period,company) {
            Entry ent1 =new Entry("DPT01","TXCr","0003222r","2010-10-10","Ref0922r" ,"HRef0373r", "CASH DEPOSIT", "07774", -10000.08, -1000.00, 1.0, "Fredrick", 2,       2015, 4,     4);
            Entry ent2 =new Entry("DPT02","TXCr","0003222b","2010-10-10","Ref0922r" ,"HRef0373r", "CASH DEPOSIT", "07774",  10000.08,  1000.00, 1.0, "Fredrick", 2,       2015, 4,     4);
            
            java.util.LinkedList<Entry> dentlist;
            dentlist = new LinkedList<Entry>();
            dentlist.add(ent1);
            dentlist.add(ent2);
                        
            //NewSerialno vSerial = new NewSerialno();
            int varSerialint;
            String varSerial;
            //varSerialint =  vSerial.returnSerialno("Dref","02",7);
            varSerial = "05";//Integer.toString(varSerialint);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = "2015-05-14";
            String dateInString2 = "2015-05-14";
            Date coydate=null;
            Date postdate=null;
            Date entrydate=null;
            String dtimezone="Africa/Lagos";
            
            try{
	             coydate = formatter.parse(dateInString);
	             postdate = formatter.parse(dateInString2);
	             
	             TimeZone timeZone = TimeZone.getTimeZone(dtimezone);
	             Calendar rightNow = Calendar.getInstance(timeZone);
	             entrydate=rightNow.getTime();
	        }catch (ParseException e){
	             e.printStackTrace();
	        }
	                                          //txnSerial, txnType,year, period, txnDate, postdate, entrydate, docRef,    headernarrative,   source, userId,    branchId, companyid) {
	        Txnsheader txnhdr = new Txnsheader("1",        "JV",   2015, 4,      coydate, postdate, entrydate, varSerial, "Journal Posting:" ,"GL" ,"Fredrick", 3,        4,dtimezone);
	        Entrys ent = new Entrys();
	        ent.setTxnsheader(txnhdr);
	        ent.setEntrys(dentlist);     
           
            try{
            	postEntry(ent);
            }catch(Exception ex){
            	ex.printStackTrace();
            }
    	}else{   			
    		Accnowbs obj=new Accnowbs();
            
    		obj.setBranchcode("003");
    		obj.setBranchId(3);
    		obj.setCompany(4);
    		obj.setCompanycode("04");
    		obj.setProductcode("SAV1");
    		obj.setSubno("01");  
    		obj.setCustomerno("000001");
    		
    		try{
    			createProductAccount(obj);
    		}catch(Exception ex){
    			ex.printStackTrace();
    		}
    	}
    }  
   
    public boolean postEntry(Entrys ent){//Transaction Entry Object with headers & content
		boolean success=false;
		
        try{
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            webResource = client.resource(BASE_URI).path("glws");
               
            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class,ent);                
            System.out.println("Server response : \n" + response.getStatus());
            
            if(response.getStatus() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus() + ". Operation failed");
            }else{
            	success=true;
            }
            
            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        return success;
    }
    
    public String createProductAccount(Accnowbs obj){//Transaction Entry Object with headers & content
		boolean success=false;
		String str=null;
		
        try{
            com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
            client = Client.create(config);
            webResource = client.resource(BASE_URI).path("glwsprdacno");
               
            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, obj);                
            //System.out.println("Server response : \n" + response.getStatus());
            
            if(response.getStatus() != 201){
                //throw new RuntimeException("Failed : HTTP error code : "
                //+ response.getStatus() + ". Operation failed");
            }else{
            	success=true;
            }
            
            String output = response.getEntity(String.class);
            System.out.println("Server response : \n");
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return str;
    }
}
