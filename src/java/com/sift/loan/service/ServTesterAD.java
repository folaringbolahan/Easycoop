package com.sift.loan.service;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import com.sift.admin.bean.CurrencyBean;

public class ServTesterAD {
	public static void main(String args[]){
	    String BASE_URI="http://localhost:7070/EasycoopfinTEST3/services/";
	    
	    int id=1;
		WebClient client = WebClient.create(BASE_URI);		
		CurrencyBean bean=new CurrencyBean();
		bean.setId(id);
				
		try {
			System.out.println("uri=:" + client.getBaseURI() + "\n\r");
			System.out.println("value=:" + client.toString());
			
			client.path( "wscurrency/getcur/" + id).accept( "application/xml" ).get();
			Response resp=client.getResponse();
			System.out.println(resp.getStatus());	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}    

}
