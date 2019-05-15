/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.cli;

import com.sift.gl.NewSerialno;
import com.sift.gl.model.Accnowbs;
import com.sift.gl.model.Activitylog;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import java.io.File;
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
public class AuditlogJerseyClient {
    private static WebResource webResource;
    private static Client client;
    private String theerrormess = "";
    private String DBASE_URI = "";

    public static ClientResponse create_XML(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse create_JSON(Object requestEntity) throws UniformInterfaceException {
        return webResource.type(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(ClientResponse.class, requestEntity);
    }
 
    public String gettheerrmess(){
        return theerrormess;
    }
    
    public int calldservice(Activitylog ent) {
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
                    webResource = client.resource(DBASE_URI).path("glwsaudlog");
                    Client client = Client.create();
	            ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, ent);
                    System.out.println("Server response : \n" + response.getStatus());
	            resp = response.getStatus();
                    if (response.getStatus() != 201) {
                        theerrormess = "Failed : HTTP error code : " + response.getStatus() + ". Operation failed";
	                throw new RuntimeException("Failed : HTTP error code : "
	                        + response.getStatus() + ". Operation failed");
                    }
                    
	            String output = response.getEntity(String.class);
	            System.out.println("Server response : \n");
	            System.out.println(output);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
               return resp;
	    }
}
