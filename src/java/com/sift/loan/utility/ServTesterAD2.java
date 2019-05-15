package com.sift.loan.utility;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sift.admin.bean.CurrencyBean;

public class ServTesterAD2{
	static String BASE_URIX="http://localhost:7070/EasycoopfinTEST3/services/wscurrency/";
	final static URI BASE_URI = UriBuilder.fromUri(BASE_URIX).build();
	
	public static void main(String args[]){   
		//getAllCurrencies();
		getACurrency(1);
		
		CurrencyBean cur=new CurrencyBean();
		cur.setActive("X");
		cur.setCreatedBy("WS");
		cur.setCreationDate(new java.util.Date());
		cur.setCurrencyCode("OXN");
		cur.setCurrencyName("OXEN");
		cur.setDeleted("N");
		cur.setIsBase("N");
		
		//newCurrency(cur);
		getAllCurrencies();
	}
	
	public static void getAllCurrencies(){
	    Client clientgetAll = ClientBuilder.newClient();
	    WebTarget webTargetgetAll = clientgetAll.target(BASE_URI);
	    webTargetgetAll = webTargetgetAll.path("/gcurs/");
	    
	    Invocation.Builder builderGetAll = webTargetgetAll.request();	    	    
	    String output = builderGetAll.get(String.class);	    
	    System.out.println("Output : " + output);
	}
	
	public static void getACurrency(int id){
	    Client clientgetAll = ClientBuilder.newClient();
	    WebTarget webTargetgetAll = clientgetAll.target(BASE_URI);
	    webTargetgetAll = webTargetgetAll.path("/getcur/" + id);
	    
	    Invocation.Builder builderGetAll = webTargetgetAll.request();	    	    
	    String output = builderGetAll.get(String.class);	    
	    System.out.println("Output : " + output);
	}
	
	public static void newCurrency(CurrencyBean cur){
	    Client clientPutCurrency = ClientBuilder.newClient();
	    WebTarget webTargetPutCurrency = clientPutCurrency.target(BASE_URI);
	    webTargetPutCurrency = webTargetPutCurrency.path("/addcur/");
	    
	    Invocation.Builder builderPutCurrency = webTargetPutCurrency.request();	    	    
	    
	    String putStatus = builderPutCurrency.put(Entity.entity(cur,MediaType.APPLICATION_XML),String.class);
	    System.out.println("Put new currency : "+putStatus);
	    
	    //http://java4developers.com/2012/rest-web-services-with-apache-cxf-and-spring/
	    //http://codeoncloud.blogspot.com/2015/02/build-simple-java-jax-rs-restful-web.html
	}	    
}