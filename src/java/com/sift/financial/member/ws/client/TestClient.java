/**
 * 
 */
package com.sift.financial.member.ws.client;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * @author baydel200
 *
 */
public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		GenericServiceImpl genServ = new GenericServiceImpl();
		/**
                 * Accnowbs test = new Accnowbs();
		
		test.setBranchcode("0003");
		test.setBranchId("3");
		test.setCompany("4");
		test.setCompanycode("0004");
		test.setCustomerno("000003");
		test.setProductcode("CON");
		test.setSubno("01");
	
		
		
		TestClient testClient = new TestClient();
		*/
		String accountStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><products><branchId>3</branchId><code>011</code><companyId>4</companyId><controlAccount>09801234</controlAccount><currencyId>16</currencyId><hasInterest>0</hasInterest><hasPenalty>0</hasPenalty><initialAmountMax>0.0</initialAmountMax><initialAmountMin>0.0</initialAmountMin><interestRate>0.0</interestRate><interestRateMax>0.0</interestRateMax><interestRateMin>0.0</interestRateMin><isDefault>0</isDefault><isDeleted>0</isDeleted><isTaxable>0</isTaxable><name>MEMB STOCK</name><penalty>0.0</penalty><productId>0</productId><productTypeCode>T</productTypeCode><segmentCode>009</segmentCode></products>";
		
		try
		{
			//accountStr = genServ.convertObjectToXML(test, "com.sift.financial.member.ws.client.Accnowbs");
                        
                       Object prodObj = genServ.convertXMLToObject(accountStr, "com.sift.financial.member.ws.client.Products");
                       
                       if(prodObj instanceof Products)
                       {
                          System.out.println("Yes .....am instance of products");
                       }
                       else
                       {
                         System.out.println("No .....am not instance of products");
                       }
			
		  } catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//testClient.usingDirect(accountStr,"http://localhost:9090/EasycoopfinTEST3/webserv/glwsprdacno");
		
		//genServ.consumeServ(accountStr, "http://localhost:8080/easycoopfin/webserv/glwsprdacno", "application/xml");
		
		/*try {
			System.out.println(genServ.convertObjectToJson(test));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void usingJaxRs () throws Throwable
	{
	
		String uri = "http://localhost:8080/CustomerService/rest/customers/1"; 
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");   
		JAXBContext jc = JAXBContext.newInstance(Accnowbs.class); 
		InputStream xml = connection.getInputStream(); 
		Accnowbs customer = (Accnowbs) jc.createUnmarshaller().unmarshal(xml);   
		connection.disconnect();
	}
	
	
	public void usingDirect(String xmlInput, String uri)
	{
		
		Client client = Client.create(); 
		uri = "http://localhost:9090/EasycoopfinTEST3/webserv/glwsprdacno"; 
		WebResource resource = client.resource(uri); 
		
		ClientResponse response = resource.accept("application/xml").type("application/xml").post(ClientResponse.class, xmlInput);
		
		 if (response.getStatus() != 200) 
		 {
			 throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
		 }
		 
		//ClientResponse response = resource.accept("application/xml").get(ClientResponse.class);     
		System.out.println(response.getEntity(String.class));

	}
	
	
	
	
}
