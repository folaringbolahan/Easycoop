package com.sift.loan.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import com.sift.admin.bean.SMSConfig;
import com.sift.admin.interfaces.Definitions;
import com.sift.gl.model.SMSBean;

public class SMSUtility {
		private final String USER_AGENT = "Mozilla/5.0";

		public static void main(String[] args) throws Exception {
			SMSUtility http = new SMSUtility();
			/**************
			System.out.println("Testing 1 - Send Http GET request");
			http.sendGet();

			System.out.println("\nTesting 2 - Send Http POST request");
			http.sendPost();
			************************/
			
			try{
		    	 String message="Your Loan Repayment processing XXXXXXXXX was successful. Your outstanding Loan Balance is: NGN 10,000.00";
		    	 SMSBean sms=new SMSBean();
		    	 
		    	 sms.setMessage(message);
		    	 sms.setSender(Definitions.SMS_DEFAULT_SENDER);
		    	 //sms.setSendto("07039412005");
		    	 sms.setSendto("08059979029");
		    	 sms.setMsgtype(Definitions.SMS_DEFAULT_MESSAGE_TYPE);
		    	 sms.setSendtime(null);	 
		    	 
		    	 http.sendSMSPostNew(sms);
		     }catch(Exception ex){
		    	 ex.printStackTrace();
		     }

		}

		// HTTP GET request
		private void login() throws Exception {

			String url = "http://www.google.com/search?q=developer";

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);

			// add request header
			request.addHeader("User-Agent", USER_AGENT);

			HttpResponse response = client.execute(request);

			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + 
	                       response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                       new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		}
		
		private void sendGet() throws Exception {
			String url = "http://www.google.com/search?q=developer";

			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);

			// add request header
			request.addHeader("User-Agent", USER_AGENT);

			HttpResponse response = client.execute(request);

			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + 
	                       response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                       new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());

		}

		// HTTP POST request
		private void sendPost() throws Exception {
			String url = "https://selfsolve.apple.com/wcResults.do";

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			// add header
			post.setHeader("User-Agent", USER_AGENT);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("cmd", "C02G8416DRJM"));
			urlParameters.add(new BasicNameValuePair("cn", ""));
			urlParameters.add(new BasicNameValuePair("locale", ""));
			urlParameters.add(new BasicNameValuePair("caller", ""));
			urlParameters.add(new BasicNameValuePair("num", "12345"));

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + 
	                                    response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			System.out.println(result.toString());
		}
		
		private void sendPost(Map<String,String> iMap) throws Exception{
			String url = "http://www.smslive247.com/http/index.aspx";
			
			/****************************************************************************************************************************************************
			*
               http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=xxx&subacct=xxx &subacctpwd=xxx
			   http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=xxx&message=xxx &sender=xxx&sendto=xxx&msgtype=0
               http://www.smslive247.com/http/index.aspx?cmd=sendquickmsg&owneremail=xxx&subacct=xxx&subacctpwd=xxx&message=xxx&sender=xxx&sendto=xxx&msgtype=0
               http://www.smslive247.com/http/index.aspx?cmd=querybalance&sessionid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querymsgcharge&sessionid=xxx &messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querymsgstaus&sessionid=xxx &messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querycoverage&sessionid=xxx&msisdn=xxx
               
               http://www.smslive247.com/http/index.aspx?cmd=recharge&sessionid=xxx&rcode=xxx
               http://www.smslive247.com/http/index.aspx?cmd=stopmsg&sessionid=xxx&messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=getsentmsgs&sessionid=xxx&pagesize=xxx&pagenumber=xxx&begindate=xxx&enddate=xxx&sender=xxx&contains=xxx
             
			   EXAMPLES
			   --------			   
				SendQuickMsg command including authentication and Sender ID:
				-----------------------------------------------------------
				http://www.smslive247.com/http/index.aspx?cmd=sendquickmsg&owneremail=you@demo.com&subacct=family&subacctpwd=secret&message=my+first+message&sender=ME&sendto=08057071234&msgtype=0
				
				Initial authentication:
				----------------------
				http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=me@demo.com&subacct=family&subacctpwd=secret
				
				All further commands will use a sessionID generated using Login command above:
				------------------------------------------------------------------------------
				Send a message:
				---------------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=080202222222&msgtype=0
				
				Send Bulk message:
				------------------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=http://yourserver.com/bulk/myfriends.text&msgtype=0
				
				Flash SMS:
				----------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=080202222222&msgtype=1
				
				Account balance:
				----------------
				http://www.smslive247.com/http/index.aspx?cmd=querybalance&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9
				
				Query message status:
				--------------------
				http://www.smslive247.com/http/index.aspx?cmd=querymsgstatus&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9
            *
            *
            *
            *
            *
			*****************************************************************************************************************************************************/
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			//Add header
			post.setHeader("User-Agent", USER_AGENT);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			
			String keyNames[]={ 
					            "cmd","sessionid","owneremail","subacct","subacctpwd",
					            "message","sender","sendto","msgtype","sendtime"
			                  };
				
			for(String item: keyNames){
				if(iMap.containsKey(item)){urlParameters.add(new BasicNameValuePair(item, iMap.get(item)));}
			}

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : " + 
	                                    response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(
	                        new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			
			while ((line = rd.readLine()) != null){
				result.append(line);
			}

			System.out.println(result.toString());
		}
		
		public String sendSMSPost(SMSBean obj) throws Exception{
			String url = "http://www.smslive247.com/http/index.aspx";
			Map<String,String> iMap=new java.util.HashMap<String,String>();	
			String resp=null;
			SMSConfig config=getSMSConfig();
			url=config.getUri();
			
			String sessionId=null;
			
			try{
				sessionId=doSMSAPILogon();
				sessionId=sessionId.replace("OK:","").trim();
			}catch(RuntimeException e1){
				e1.printStackTrace();
			}
			
			/****************************************************************************************************************************************************
			*
               http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=xxx&subacct=xxx &subacctpwd=xxx
			   http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=xxx&message=xxx &sender=xxx&sendto=xxx&msgtype=0
               http://www.smslive247.com/http/index.aspx?cmd=sendquickmsg&owneremail=xxx&subacct=xxx&subacctpwd=xxx&message=xxx&sender=xxx&sendto=xxx&msgtype=0
               http://www.smslive247.com/http/index.aspx?cmd=querybalance&sessionid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querymsgcharge&sessionid=xxx &messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querymsgstaus&sessionid=xxx &messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querycoverage&sessionid=xxx&msisdn=xxx
               
               http://www.smslive247.com/http/index.aspx?cmd=recharge&sessionid=xxx&rcode=xxx
               http://www.smslive247.com/http/index.aspx?cmd=stopmsg&sessionid=xxx&messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=getsentmsgs&sessionid=xxx&pagesize=xxx&pagenumber=xxx&begindate=xxx&enddate=xxx&sender=xxx&contains=xxx
             
			   EXAMPLES
			   --------			   
				SendQuickMsg command including authentication and Sender ID:
				-----------------------------------------------------------
				http://www.smslive247.com/http/index.aspx?cmd=sendquickmsg&owneremail=you@demo.com&subacct=family&subacctpwd=secret&message=my+first+message&sender=ME&sendto=08057071234&msgtype=0
				
				Initial authentication:
				----------------------
				http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=me@demo.com&subacct=family&subacctpwd=secret
				
				All further commands will use a sessionID generated using Login command above:
				------------------------------------------------------------------------------
				Send a message:
				---------------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=080202222222&msgtype=0
				
				Send Bulk message:
				------------------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=http://yourserver.com/bulk/myfriends.text&msgtype=0
				
				Flash SMS:
				----------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=080202222222&msgtype=1
				
				Account balance:
				----------------
				http://www.smslive247.com/http/index.aspx?cmd=querybalance&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9
				
				Query message status:
				--------------------
				http://www.smslive247.com/http/index.aspx?cmd=querymsgstatus&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9
            *
            *
			*****************************************************************************************************************************************************/
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			//Add header
			post.setHeader("User-Agent", USER_AGENT);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			
			String keyNames[]={ 
					            "cmd","sessionid","owneremail","subacct","subacctpwd",
					            "message","sender","sendto","msgtype","sendtime"
			                  };
			
			//put values into Map
			iMap.put("cmd", "sendmsg");
			iMap.put("sessionid", sessionId);
			iMap.put("subacct", config.getUser());
			iMap.put("subacctpwd", config.getPass());
			iMap.put("sender", config.getSender());
			iMap.put("owneremail", config.getOwnerEmail());

			if(obj.getMessage()!=null  && !"".equals(obj.getMessage())){iMap.put("message", obj.getMessage());}			
			if(obj.getSendto()!=null  && !"".equals(obj.getSendto())){iMap.put("sendto", obj.getSendto());}
			if(obj.getMsgtype()!=null  && !"".equals(obj.getMsgtype())){iMap.put("msgtype", obj.getMsgtype());}
			if(obj.getSendtime()!=null  && !"".equals(obj.getSendtime())){iMap.put("sendtime", obj.getSendtime());}
			
			for(String item: keyNames){
				if(iMap.containsKey(item)){urlParameters.add(new BasicNameValuePair(item, iMap.get(item)));}
			}

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			try{
				HttpResponse response = client.execute(post);
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + post.getEntity());
				System.out.println("Response Code : " + 
				                            response.getStatusLine().getStatusCode());

				BufferedReader rd = new BufferedReader(
				                new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				
				while ((line = rd.readLine()) != null){
					result.append(line);
				}
				
				System.out.println(result.toString());
				resp=result.toString();
			} catch(RuntimeException e) {
				// TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}catch(Exception e){
				//TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}
			
			return resp;
		}
		
		public String sendSMSPostNew(SMSBean obj) throws Exception{
			String url = "http://www.smslive247.com/http/index.aspx";
			Map<String,String> iMap=new java.util.HashMap<String,String>();	
			String resp=null;
			SMSConfig config=getSMSConfigNew();
			url=config.getUri();
			
			String sessionId=null;
			
			try{
				sessionId=doSMSAPILogonNew();
				sessionId=sessionId.replace("OK:","").trim();
			}catch(RuntimeException e1){
				e1.printStackTrace();
			}
			
			/****************************************************************************************************************************************************
			*
               http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=xxx&subacct=xxx &subacctpwd=xxx
			   http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=xxx&message=xxx &sender=xxx&sendto=xxx&msgtype=0
               http://www.smslive247.com/http/index.aspx?cmd=sendquickmsg&owneremail=xxx&subacct=xxx&subacctpwd=xxx&message=xxx&sender=xxx&sendto=xxx&msgtype=0
               http://www.smslive247.com/http/index.aspx?cmd=querybalance&sessionid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querymsgcharge&sessionid=xxx &messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querymsgstaus&sessionid=xxx &messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=querycoverage&sessionid=xxx&msisdn=xxx
               
               http://www.smslive247.com/http/index.aspx?cmd=recharge&sessionid=xxx&rcode=xxx
               http://www.smslive247.com/http/index.aspx?cmd=stopmsg&sessionid=xxx&messageid=xxx
               http://www.smslive247.com/http/index.aspx?cmd=getsentmsgs&sessionid=xxx&pagesize=xxx&pagenumber=xxx&begindate=xxx&enddate=xxx&sender=xxx&contains=xxx
             
			   EXAMPLES
			   --------			   
				SendQuickMsg command including authentication and Sender ID:
				-----------------------------------------------------------
				http://www.smslive247.com/http/index.aspx?cmd=sendquickmsg&owneremail=you@demo.com&subacct=family&subacctpwd=secret&message=my+first+message&sender=ME&sendto=08057071234&msgtype=0
				
				Initial authentication:
				----------------------
				http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=me@demo.com&subacct=family&subacctpwd=secret
				
				All further commands will use a sessionID generated using Login command above:
				------------------------------------------------------------------------------
				Send a message:
				---------------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=080202222222&msgtype=0
				
				Send Bulk message:
				------------------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=http://yourserver.com/bulk/myfriends.text&msgtype=0
				
				Flash SMS:
				----------
				http://www.smslive247.com/http/index.aspx?cmd=sendmsg&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9&message=my+first+message&sender=ME&sendto=080202222222&msgtype=1
				
				Account balance:
				----------------
				http://www.smslive247.com/http/index.aspx?cmd=querybalance&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9
				
				Query message status:
				--------------------
				http://www.smslive247.com/http/index.aspx?cmd=querymsgstatus&sessionid=e74dee1bbed22ee3a39f9aeab606ccf9
            *
            *
			*****************************************************************************************************************************************************/
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			//Add header
			post.setHeader("User-Agent", USER_AGENT);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			
			String keyNames[]={ 
					            "cmd","sessionid","owneremail","subacct","subacctpwd",
					            "message","sender","sendto","msgtype","sendtime"
			                  };
			
			System.out.println("config.getUser():" + config.getUser());
			System.out.println("config.getPass():" + config.getPass());

			//put values into Map
			iMap.put("cmd", "sendmsg");
			iMap.put("sessionid", sessionId);
			iMap.put("subacct", config.getUser());
			iMap.put("subacctpwd", config.getPass());
			iMap.put("sender", config.getSender());
			iMap.put("owneremail", config.getOwnerEmail());

			if(obj.getMessage()!=null  && !"".equals(obj.getMessage())){iMap.put("message", obj.getMessage());}			
			if(obj.getSendto()!=null  && !"".equals(obj.getSendto())){iMap.put("sendto", obj.getSendto());}
			if(obj.getMsgtype()!=null  && !"".equals(obj.getMsgtype())){iMap.put("msgtype", obj.getMsgtype());}
			if(obj.getSendtime()!=null  && !"".equals(obj.getSendtime())){iMap.put("sendtime", obj.getSendtime());}
			
			for(String item: keyNames){
				if(iMap.containsKey(item)){urlParameters.add(new BasicNameValuePair(item, iMap.get(item)));}
			}

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			try{
				HttpResponse response = client.execute(post);
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + post.getEntity());
				System.out.println("Response Code : " + 
				                            response.getStatusLine().getStatusCode());

				BufferedReader rd = new BufferedReader(
				                new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				
				while ((line = rd.readLine()) != null){
					result.append(line);
				}
				
				System.out.println(result.toString());
				resp=result.toString();
			} catch(RuntimeException e) {
				// TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}catch(Exception e){
				//TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}
			
			return resp;
		}
		
		private String doSMSAPILogon() throws Exception{
			String url = "http://www.smslive247.com/http/index.aspx";
			Map<String,String> iMap=new java.util.HashMap<String,String>();	
			String resp=null;
			SMSConfig config=getSMSConfig();
			url=config.getUri();
			
			/****************************************************************************************************************************************************
			*
			*
               http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=xxx&subacct=xxx &subacctpwd=xxx

			   EXAMPLES
			   --------			   
			   Initial authentication:
			   ----------------------
			   http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=me@demo.com&subacct=family&subacctpwd=secret
            *
            *
			*****************************************************************************************************************************************************/

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			//Add header
			post.setHeader("User-Agent", USER_AGENT);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			
			String keyNames[]={ 
					            "cmd","owneremail","subacct","subacctpwd"
			                  };
			
			//put values into Map
			iMap.put("cmd", "login");
			iMap.put("owneremail", config.getOwnerEmail());
			iMap.put("subacct", config.getUser());
			iMap.put("subacctpwd", config.getPass());
			
			for(String item: keyNames){
				if(iMap.containsKey(item)){urlParameters.add(new BasicNameValuePair(item, iMap.get(item)));}
			}

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			try {
				HttpResponse response = client.execute(post);
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + post.getEntity());
				System.out.println("Response Code : " + 
				                            response.getStatusLine().getStatusCode());

				BufferedReader rd = new BufferedReader(
				                new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				
				while((line = rd.readLine()) != null){
					result.append(line);
				}
				
				System.out.println(result.toString());
				resp=result.toString();
			} catch(RuntimeException e) {
				//TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}catch(Exception e){
				// TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}
			
			return resp;
		}
		
		private String doSMSAPILogonNew() throws Exception{
			String url = "http://www.smslive247.com/http/index.aspx";
			Map<String,String> iMap=new java.util.HashMap<String,String>();	
			String resp=null;
			SMSConfig config=getSMSConfigNew();
			url=config.getUri();
			
			/****************************************************************************************************************************************************
			*
			*
               http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=xxx&subacct=xxx &subacctpwd=xxx

			   EXAMPLES
			   --------			   
			   Initial authentication:
			   ----------------------
			   http://www.smslive247.com/http/index.aspx?cmd=login&owneremail=me@demo.com&subacct=family&subacctpwd=secret
            *
            *
			*****************************************************************************************************************************************************/

			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			//Add header
			post.setHeader("User-Agent", USER_AGENT);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			
			String keyNames[]={ 
					            "cmd","owneremail","subacct","subacctpwd"
			                  };
			
			//put values into Map
			iMap.put("cmd", "login");
			iMap.put("owneremail", config.getOwnerEmail());
			iMap.put("subacct", config.getUser());
			iMap.put("subacctpwd", config.getPass());
			
			for(String item: keyNames){
				if(iMap.containsKey(item)){urlParameters.add(new BasicNameValuePair(item, iMap.get(item)));}
			}

			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			try {
				HttpResponse response = client.execute(post);
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + post.getEntity());
				System.out.println("Response Code : " + 
				                            response.getStatusLine().getStatusCode());

				BufferedReader rd = new BufferedReader(
				                new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result = new StringBuffer();
				String line = "";
				
				while((line = rd.readLine()) != null){
					result.append(line);
				}
				
				System.out.println(result.toString());
				resp=result.toString();
			} catch(RuntimeException e) {
				//TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}catch(Exception e){
				// TODO Auto-generated catch block
				resp=null;
				e.printStackTrace();
			}
			
			return resp;
		}
		
		
		public SMSConfig getSMSConfig(){
	    	String uri ="";
	    	String port ="";
	    	String user ="";
	    	String pass ="";
	    	String sender ="";
	    	String owneremail ="";
	    	
	    	SMSConfig MB=new SMSConfig();
	    	
	        try{
	            javax.naming.Context ctx = new javax.naming.InitialContext();
	            
	            uri = (String) ctx.lookup("java:comp/env/sms.uri");
	            port = (String) ctx.lookup("java:comp/env/sms.port");
	            user = (String) ctx.lookup("java:comp/env/sms.user");
	            pass = (String) ctx.lookup("java:comp/env/sms.pass");
	            sender = (String) ctx.lookup("java:comp/env/sms.sender");
	            owneremail = (String) ctx.lookup("java:comp/env/sms.owneremail");
	            
	            MB.setUri(uri);
	            MB.setPort(port);
	            MB.setUser(user);
	            MB.setPass(pass);
	            MB.setOwnerEmail(owneremail);
	            MB.setSender(sender);
	            
	        } catch (Exception nx) {
	        	MB=null;
	            nx.printStackTrace();
	        }
	           
	        return MB;
	    }
		
		public SMSConfig getSMSConfigNew(){
	    	String uri ="";
	    	String port ="";
	    	String user ="";
	    	String pass ="";
	    	String sender ="";
	    	String owneremail ="";
	    	
	    	SMSConfig MB=new SMSConfig();
	    	
	        try{
	            
	            uri = Definitions.SMS_URI;	            
	            user = Definitions.SMS_USER;
	            pass = Definitions.SMS_PASS;
	            sender = Definitions.SMS_SENDER;
	            owneremail = Definitions.SMS_OWNEREMAIL;
	            
	            MB.setUri(uri);
	            MB.setPort(port);
	            MB.setUser(user);
	            MB.setPass(pass);
	            MB.setOwnerEmail(owneremail);
	            MB.setSender(sender);
	            
	        } catch (Exception nx) {
	        	MB=null;
	            nx.printStackTrace();
	        }
	           
	        return MB;
	    }
	}