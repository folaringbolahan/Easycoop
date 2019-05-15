/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.cli;

import com.sift.gl.GendataService;
import com.sift.gl.NewSerialno;
import com.sift.gl.model.Accnowbs;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * Jersey REST client generated for REST resource:TxnsdetRESTFacade [/glws]<br>
 * USAGE:
 * <pre>
 *        NewJerseyClient client = new NewJerseyClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author yomi-pc
 */
public class MembaccnoJerseyClient {
    private static WebResource webResource;
    private static Client client;
    //private static final String BASE_URI = "http://localhost:8084/Easycoopfin/webserv";
    private String theerrormess = "";
    private String DBASE_URI = "";
    private String dpwd;
    private String dname;
    private String daccno = "";

    public static ClientResponse create_XML(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse create_JSON(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(ClientResponse.class, requestEntity);
    }
 
    public String gettheerrmess(){
        return theerrormess;
    }
    public String getdaccno(){
        return daccno;
    }
    public int calldservice(Accnowbs ent) {
                int resp =0;
                theerrormess = "";
                try {
                   javax.naming.Context ctx = new javax.naming.InitialContext();
                   String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
                   DBASE_URI = uri;
                  } catch (NamingException nx) {System.out.println("Error number exception" + nx.getMessage().toString());}
                
	        try {
                    com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
                    client = Client.create(config);
		    getRoleparameters();
                    client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
                    webResource = client.resource(DBASE_URI).path("glwsprdacno");
                   /* System.out.println(DBASE_URI+"/glwsprdacno");
                    JAXBContext jaxbContext = JAXBContext.newInstance(Accnowbs.class);
	            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    jaxbMarshaller.marshal( ent, new File( "c://testtrial//memacno.xml" ) );
                    jaxbMarshaller.marshal( ent, System.out );
                    */
                    Client client = Client.create();
                    
	            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, ent);
                    System.out.println("Server response : \n" + response.getStatus());
	            resp = response.getStatus();
                    String output = response.getEntity(String.class);
                    if (response.getStatus() != 201) {
                        theerrormess = "Failed : HTTP error code : " + response.getStatus() + ". Operation failed";
	                //throw new RuntimeException("Failed : HTTP error code : "
	                //        + response.getStatus() + ". Operation failed");
                    }
                    else{
                        String drespstr=output;
                        if (output.indexOf("OK.")!=-1) {
                          drespstr= drespstr.trim().toUpperCase().replace("OK.","");
                         if(drespstr!=null)
                          {
 	                     drespstr=drespstr.trim();
                             daccno = drespstr;
                          }			
                        }         
                    }
	            
	            System.out.println("Server response : \n");
	            System.out.println(output);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
               return resp;
	    }
    
    
    public void getRoleparameters() {
        String name = "";
        String password = "";
        GendataService dbobj = new GendataService();
        dbobj.inimkcon();
        String mySQLString;
        mySQLString = "select * FROM tblwebserv a where a.app = 'internal'"  ;
        ResultSet agRecSet;
        try 
         { 
        agRecSet = dbobj.retrieveRecset(mySQLString);
         while (agRecSet.next()) {
            name = agRecSet.getString("user");
            password = agRecSet.getString("pwd");
          }
         dname = name;
         dpwd = password;
        }
       catch (SQLException ex) {
          System.out.println(ex.getMessage());
       } 
       finally {
           if (dbobj != null) {
            dbobj.closecon();
           }
        } 
    }
    
}
