package com.sift.loan.utility;
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

			client.path( "wscurrency/getcur/" + id).accept( "text/xml" ).get();
			Response resp=client.getResponse();
			System.out.println(resp.ok(CurrencyBean.class).build());	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}    

}
