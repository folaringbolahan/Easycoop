/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.cli;

import com.sift.gl.GendataService;
import com.sift.gl.model.Entry;
import com.sift.gl.model.Entrys;
import com.sift.gl.NewSerialno;
import com.sift.gl.model.Txnsheader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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
public class GLJerseyClient {
    private static WebResource webResource;
    private static Client client;
    private static final String BASE_URI = "http://localhost:8084/Easycoopfin/webserv";
    private String theerrormess = "";
    private String theref = "";
    private String DBASE_URI = "";
    private String dpwd;
    private String dname;
/*
    public NewJerseyClient() {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("glws");
    }
 */
    public static ClientResponse create_XML(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse create_JSON(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(ClientResponse.class, requestEntity);
    }
/*
    public void close() {
        client.destroy();
    }
  */  
    public String gettheerrmess(){
        return theerrormess;
    }
    public String getthereference(){
        return theref;
    }
    public int calldservice(Entrys ent) {
                int resp =0;
                theerrormess = "";
                try {
                   javax.naming.Context ctx = new javax.naming.InitialContext();
                   String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
                   DBASE_URI = uri;
                   //System.out.println("uri - " + DBASE_URI);
                  } catch (NamingException nx) {System.out.println("Error number exception" + nx.getMessage().toString());}
                
	        try {
                    com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
                    client = Client.create(config);
                    getRoleparameters();
                    client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
                    webResource = client.resource(DBASE_URI).path("glws");
                    /*
	            Entry ent1 = new Entry("WTDA1","TXCr","0003222r","2010-10-10","Ref0922r" ,"HRef0373r", "CASH WITHDRAWAL", "07774", -10893.08, -10764.54, 2.0, "User Waler", "02",2015,4,7);
                    Entry ent2 = new Entry("WTDA2","TXCr","0003222b","2010-10-10","Ref0922r" ,"HRef0373r", "CASH WITHDRAWAL", "07774", 10893.08, 10764.54, 2.0, "User Waler", "02",2015,4,7);
                    java.util.LinkedList<Entry> dentlist;
                    dentlist = new LinkedList<Entry>();
                    dentlist.add(ent1);
                    dentlist.add(ent2);
                    //
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
                    try {
                      coydate = formatter.parse(dateInString);
                      postdate = formatter.parse(dateInString2);
                      entrydate = formatter.parse(dateInString2);
	            } catch (ParseException e) {
		      e.printStackTrace();
	            }
                    Txnsheader txnhdr = new Txnsheader("1", "JV",2015,4,coydate, postdate, entrydate, varSerial, "Journal Posting:" ,"GL" ,"User Waler", "02",7);
                    Entrys ent = new Entrys();
                    ent.setTxnsheader(txnhdr);
                    ent.setEntrys(dentlist);
                    */
                    
                    //JAXBContext jaxbContext = JAXBContext.newInstance(Entrys.class);
	            //Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    //jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    //jaxbMarshaller.marshal( ent, new File( "c://testtrial//country.xml" ) );
                    //jaxbMarshaller.marshal( ent, System.out );
                    //ClientResponse response =  create_XML(ent);
	            ////
                    Client client = Client.create();
	            //WebResource webResource = client.resource("http://localhost:8080/JerseyXMLExample/rest/xmlServices/send");
	            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, ent);
                   // System.out.println("Server response : \n" + response.getStatus());
	            resp = response.getStatus();
                    String output = "";
                    if (response.getStatus() != 201) {
                        output = response.getEntity(String.class);
                        theerrormess = "Failed : HTTP error code : " + response.getStatus() + ". Operation failed;" + output;
	            }
                    else
                    {
                        theref = response.getEntity(String.class);
                    }    
                    System.out.println("Server response and ref : \n");
	            System.out.println(output + "-" + theref );
                } catch (Exception e) {
	            e.printStackTrace();
	        }
               return resp;
	    }
    
    public int calldunauthorizedservice(Entrys ent) {
                int resp =0;
                theerrormess = "";
                theref = "";
                try {
                   javax.naming.Context ctx = new javax.naming.InitialContext();
                   String uri = (String) ctx.lookup("java:comp/env/webservicebaseurl");
                   DBASE_URI = uri;
                   //System.out.println("uri - " + DBASE_URI);
                  } catch (NamingException nx) {System.out.println("Error number exception" + nx.getMessage().toString());}
                
	        try {
                    com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
                    client = Client.create(config);
                    getRoleparameters();
                    client.addFilter(new HTTPBasicAuthFilter(dname, dpwd));
                    webResource = client.resource(DBASE_URI).path("glwsua");
                    Client client = Client.create();
	            //WebResource webResource = client.resource("http://localhost:8080/JerseyXMLExample/rest/xmlServices/send");
	            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, ent);
                   // System.out.println("Server response : \n" + response.getStatus());
	            resp = response.getStatus();
                    String output = "";
                    if (response.getStatus() != 201) {
                        output = response.getEntity(String.class);
                        theerrormess = "Failed : HTTP error code : " + response.getStatus() + ". Operation failed;" + output;
	            }
                    else
                    {
                        theref = response.getEntity(String.class);
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
