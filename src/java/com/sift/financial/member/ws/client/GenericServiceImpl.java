package com.sift.financial.member.ws.client;

import com.sift.financial.GenericConfigDAO;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import java.util.Map;
import javax.naming.NamingException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.client.RestTemplate;


@SuppressWarnings("unused")

public class GenericServiceImpl implements GenericServiceInterface  {
    
    private String dpwd;
    private String dname;
    
    @Autowired
    @Value("${DOAUTH}")
    private String doAuth;
    
    private GenericConfigDAO genericConfigDAO;
    
  
	@Override
	public String convertObjectToXML(Object obj, String className) throws Throwable
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(Class.forName(className));
		
		Marshaller marshaller = jaxbContext.createMarshaller();
		
		StringWriter sw = new StringWriter();
		
		marshaller.marshal(obj, sw);
                System.out.println(sw.toString());
		return sw.toString();
	}


	@Override
	public Object convertXMLToObject(String xml, String className) 	throws Throwable 
	{
		
		JAXBContext jaxbContext = JAXBContext.newInstance(Class.forName(className));
		
		Unmarshaller um =  jaxbContext .createUnmarshaller();
		
		return um.unmarshal(new StringReader(xml));
	}

	@Override
	public String convertObjectToJson(Object obj ) throws Throwable {
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		mapper.writeValue(sw, obj);
		 System.out.println(sw.toString());
		return sw.toString();
	}


	@Override
	public Object convertJsonToObject(String json, String className) throws Throwable {
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.readValue(json, Class.forName(className));
	}
	
	
	public void servPurpose() {
		
		
		/*
		 *  RestTemplate restTemplate = new RestTemplate();
		 restTemplate.put(url, request, urlVariables);*/
		 
		
	}
	

	/**
	 * @param inputStr xml or json string
	 * @param uri   service url
	 * @param InputType application/xml or application/json
	 * @return
	 */
	public String consumeServ(String inputStr, String uri, String InputType)
	{
		Client client = Client.create(); 
                
                if(doAuth.equalsIgnoreCase("YES"))
                {
                    client = getAuth();
                }
		//uri = "http://localhost:9090/EasycoopfinTEST3/webserv/glwsprdacno";
		
		WebResource resource = client.resource(uri); 
		
		ClientResponse response = resource.accept(InputType).type(InputType).post(ClientResponse.class, inputStr);
		
		//ClientResponse response = resource.accept("application/xml").post(ClientResponse.class, xmlInput);
		
		 if (response.getStatus() > 299) 
		 {
			 throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		 }
		    
                // System.out.println(" Serve return :: " + response.getEntity(String.class));
                 
		return response.getEntity(String.class);
	}
        
        
        private Client getAuth ()     
        {
      
             Client client = null; 
             Map<String, String> authResult = null;
             
            try 
            {
                 authResult = getGenericConfigDAO().getServiceAuth();
                 ClientConfig clientConfig=new DefaultClientConfig();
                 client=Client.create(clientConfig);
                 client.addFilter(new HTTPBasicAuthFilter(authResult.get("user"),authResult.get("pwd")));
            } 
            catch (Exception nx)
            {
              client= null;
              System.out.println("Error number exception" + nx.getMessage().toString());
            }
            
        return client;
        }

        
    public GenericConfigDAO getGenericConfigDAO() {
        return genericConfigDAO;
    }

    @Autowired
    public void setGenericConfigDAO(GenericConfigDAO genericConfigDAO) {
        this.genericConfigDAO = genericConfigDAO;
    }
   
}
