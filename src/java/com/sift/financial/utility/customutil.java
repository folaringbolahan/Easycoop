package com.sift.financial.utility;

import java.text.*;
import java.util.*;

import org.apache.oro.text.perl.Perl5Util;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import org.springframework.web.servlet.HandlerMapping;



public class customutil {
    
    private static final String PHONE_REGEXP = "/(\\({0,1})([0-9]{3})(\\){0,1})(\\s|-)*([0-9]{3})(\\s|-)*([0-9]{4})/";

    private static final String EMAIL_REGEXP = "/^[a-z0-9]+([_\\.-][a-z0-9]+)*@([a-z0-9]+([\\.-][a-z0-9]+)*)+\\.[a-z]{2,}$/i";
    
	/**
	 * @param thedate
	 * @param dateformat
	 * @return
	 */
	public static java.util.Date getjavadate (String thedate, String dateformat)
	{
		
		SimpleDateFormat formatter = new java.text.SimpleDateFormat(dateformat);
		java.util.Date mdate=null;
		try{mdate= formatter.parse(thedate);}
		catch(Exception e){e.printStackTrace();}
                return mdate;
    }
	
	/**
	 * @param thedate
	 * @return
	 */
	public static java.sql.Date getsqldate (java.util.Date thedate)
	{
	      java.sql.Date lsqlDate= new java.sql.Date(thedate.getTime());
	      return lsqlDate;
    }


 /**
 * @param thedate
 * @return
 */
public static String formatdatestring (String thedate)
	{
	      String mo = "";
	      String da = "";
	      String yr = "";

	      mo = thedate.substring(3,5);
	      da = thedate.substring(0,2);
	      yr = thedate.substring(6,10);


	      return mo + "/" + da + "/" + yr;

    }
 
 /**
 * @param thedate
 * @return
 */
public static String formatdatestringoracle  (String thedate)
	{
	      String mo = "";
	      String da = "";
	      String yr = "";

	      mo = thedate.substring(3,5);
	      da = thedate.substring(0,2);
	      yr = thedate.substring(6,10);


	      return da + "/" + mo + "/" + yr;

 }
  
  /**
 * @param str
 * @return
 */
public static final boolean stringToBoolean(String str)
	 {
	        if (str.equals("0")) {
	            return false;
	        }
	        if (str.equals("")) {
				return false;
	        }
	        str = str.toLowerCase();
	        if (str.equals("false")) {
	            return false;
	        }
	        if (str.equals("no")) {
	            return false;
	        }
	        //if it's non false, it's true by definition
	        return true;
	 }


  /**
 * @param str
 * @return
 */
public static final int stringToInt(String str)
	 {

	  int theint = 0;
	  if (str.equals(""))
	  {
	   return theint;
	  }
	  else
	  {
		  try
		  {
	 		 theint = Integer.valueOf(str).intValue();
	   	   }
	   	   catch(Exception ex)
	   	   {
			   theint = 0;
		   }
	  return theint;
  	  }

	 }

	/**
	 * @param str 
	 * @return
	 */
	public static final double stringToDouble(String str)
	 {
	  double thedouble = 0.0;
	  if (str.equals(""))
	  {
	   return thedouble;
	  }
	  else
	  {

		  	try
		  	{
	  		thedouble = Double.valueOf(str).doubleValue();
	 		}
			catch(Exception ex)
			{
			thedouble = 0.0;
		   	}

	 	return thedouble;
  	  }

	 }

	 public static final float stringToFloat(String str)
	 	{

	 	  float theFloat = 0;
	 	  if (str.equals(""))
	 	  {
	 	   return theFloat;
	 	  }
	 	  else

	 	  {
			try
		  	{
	 	  	theFloat = Float.valueOf(str).floatValue();
	 	  	}
			catch(Exception ex)
			{
			theFloat = 0;
		   	}
	 	  	return theFloat;
	   	  }

		}
	
	/**
	 * @param themail 
	 * @return
	 */
	public static boolean checkmail (String themail)
	{
		boolean truemail=false;
		//email pattern definition
		String thepattern = "\b[A-Z0-9._%-]+@(?:[A-Z0-9-]+\\.)+[A-Z]{2,4}\b";
		//the characters sequencs
		CharSequence theseq = themail;
		truemail = Pattern.matches(thepattern,theseq);	
		return truemail;	
	}	
	
	public static void main(String arg [])
	{
		/*if((customutil.stringToDouble("0103000000000") >=103000000000.00) && (customutil.stringToDouble("0103000000000") <= 103099999999.00))
		{
			System.out.println(customutil.stringToDouble("0103000000000"));
		}
		else
		{
			System.out.println("I no dey");
		}*/
		
		Date today = new Date();
		
		//System.out.println("today ::" + today);
		
		//System.out.println("today after truncation ::" + truncateDate(today));
		String testStr= "*&%#$#-345FDR0><]#++++***%%%##!!     ";
		System.out.println("new String  ::" + replaceSpecChars(testStr));	
		System.out.println("new String length  ::" + replaceSpecChars(testStr).length());
		
	}
	
	public static String padValue(String stringtopad, boolean sideindicator, String retunlength, String padelement)
	{
		String returnval = null;
		int length = 0;
		length = customutil.stringToInt(retunlength);
		String expectedlenth = "";
		int j=0;
		
		//get the exact length of the desired string with the element to pad
		while (j< length)
		{
		expectedlenth = expectedlenth + padelement;
		j=j+1;
		}
		//now pad stringtopad with the returned string checking whether left or right	
				if (!sideindicator)
				{//if left
					returnval = expectedlenth + stringtopad;
					//System.out.println("returnval b4 "+returnval);
					returnval = returnval.substring(returnval.length()-length);
					//System.out.println("returnval after "+returnval);
				}
				else
				{//if right
					returnval = stringtopad + expectedlenth;
					returnval = returnval.substring(0, length);
				}
		        //now do the substring of the returned element with to the length required 
				returnval = returnval.substring(0, length);
				
		return returnval;
	}
	
	public static boolean  isValidPhone(String phone) {
		Perl5Util util = new Perl5Util();

        if(phone==null || phone.trim().equals("")){

              return false;

        }

        Pattern p = Pattern.compile("^[a-zA-Z]");
      Matcher m = p.matcher(phone.substring(0,1));

      if (m.find()){
           return false;
      }

      String temp=phone.replace("-", "");
    if(temp.length()==9){
        return util.match(PHONE_REGEXP, "+234"+temp) ;
    }else{

           return util.match(PHONE_REGEXP, phone) ;
    }
  }


  public static boolean isValidEmail(String email) {
      Perl5Util util = new Perl5Util();
      return util.match(EMAIL_REGEXP, email) ;
  }
  
  
  public static Date truncateDate(Date date) 
  { 
	  Calendar cal = Calendar.getInstance();
	  
      cal.set(Calendar.ZONE_OFFSET, 0); // UTC 
      cal.set(Calendar.DST_OFFSET, 0); // We don't want DST to get in the way. 

      cal.setTime(date); 
      cal.set(Calendar.MILLISECOND, 0); 
      cal.set(Calendar.SECOND, 0); 
      cal.set(Calendar.MINUTE, 0); 
      cal.set(Calendar.HOUR, 0); 
      cal.set(Calendar.AM_PM, Calendar.AM); 

      return cal.getTime(); 
  } 


  public static String arrayToString(String[] a, String separator) {
	  if (a == null || separator == null) {
      return null;
	  	}
	  StringBuffer result = new StringBuffer();
	  if (a.length > 0) {
	      result.append(a[0]);
	      for (int i=1; i < a.length; i++) {
	          result.append(separator);
	          result.append(a[i]);
	      }
	  }
  return result.toString();
  }
  
  /**
  * This utility method returns a future date after number of days.
  * @param days
  * @return
  */
  public static Date getDateAfterDays(int days) {
	  long backDateMS = System.currentTimeMillis() + ((long)days) *24*60*60*1000;
	  Date backDate = new Date();
	  backDate.setTime(backDateMS);
  return backDate;
  }    
  
  /**
  * This utility method returns a past date before number of days.
  * @param days
  * @return
  */
  public static Date getDateBeforeDays(int days) {
	  long backDateMS = System.currentTimeMillis() - ((long)days) *24*60*60*1000;
	  Date backDate = new Date();
	  backDate.setTime(backDateMS);
  return backDate;
  }
  
  
 
  public static String removeInvalidChars(String msg){

      char[] invalidchars={(char)145,(char)180,(char)35,(char)36,(char)38,(char)42,(char)94,(char)95,(char)33,(char)91,(char)92,(char)93,(char)39,(char)37,(char)64}; //all look like apostrophe but they are not
      char newChar=(char)45;  //apostrophe 
      int loopCount=invalidchars.length;
      
      for(int k=0; k<loopCount; k++){
    	  
            if(msg.indexOf(invalidchars[k])>0){ //if invlaid chars exists
                  msg=msg.replace(invalidchars[k], newChar);
            }
      }
      System.out.println("msg formatted =" + msg); 
      return msg;
    }
  
  
  public static String getType(String fileUrl) throws Exception 
  {
	    URL u = new URL(fileUrl);
	    URLConnection uc = u.openConnection();
	    String type = uc.getContentType();
	    return type;
	 }
  
  
    public static byte[] getBytesFromFile(File file) throws IOException {
	    InputStream is = new FileInputStream(file);

	    // Get the size of the file
	    long length = file.length();

	    // You cannot create an array using a long type.
	    // It needs to be an int type.
	    
	    // Before converting to an int type, check
	    // to ensure that file is not larger than Integer.MAX_VALUE.
	    if (length > Integer.MAX_VALUE) {
	        // File is too large
	    	throw new IOException("File too large "+ length);
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
	    // Close the input stream and return bytes
	    is.close();
	    
	    return bytes;
	}
    
    public static String replaceSpecChars (String str)
    {
    	return str = str.replaceAll("[^a-zA-Z 0-9]+","");
    }

  /* public static String replaceCharWithNew(char theChar, String  newChar, String msg){

         msg=msg.r(theChar, newChar);
         
      System.out.println("msg formatted =" + msg); 
      return msg;
    }*/
    
    
    public static Date getNewDate(Date curDate, int days)
    {
        Calendar calendar = Calendar.getInstance();
	calendar.setTime(curDate);
	calendar.add(Calendar.DATE, days);	
        return  calendar.getTime();
    }
    
    
     public static Date getNewDate(Date curDate, int days, String timeZone)
    {
         Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
	calendar.setTime(curDate);
	calendar.add(Calendar.DATE, days);	
        return  calendar.getTime();
    }
    
    
    public static Date getNewBusinessDate(Date curDate, int days, String timeZone)
    {
        //TimeZone timeZoneObj = TimeZonetimeZone);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        calendar.setTime(curDate);
        
        for(int i = 1; i <= days; i++)
        {
              boolean notABusinessDay= false;
        
                calendar.add(Calendar.DATE, i);

                int dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);

             
                if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
                {
                  notABusinessDay = true;
                }

                while(notABusinessDay)
                {
                   calendar.add(Calendar.DATE, 1);

                       dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);

                       if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY)
                       {
                           notABusinessDay = true;
                       }
                       else
                       {
                           notABusinessDay = false;
                       }
                }
        }
        
        return  calendar.getTime();
        
    }
    
   
    public boolean isDateValid(String dateToValidate, String dateFromat){
		
            if(dateToValidate == null){
                    return false;
            }

            SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
            sdf.setLenient(false);

            try {
                    //if not valid, it will throw ParseException
                    Date date = sdf.parse(dateToValidate);
                    System.out.println(date);

            } catch (ParseException e) {

                    e.printStackTrace();
                    return false;
            }

            return true;
	}
    
    
    public static String getActualPage(String pattern)
    {
        int pos1 = pattern.lastIndexOf("/");
        int pos2 = pattern.indexOf(".");
        String viewPage = pattern.substring(pos1+1, pos2);
        return viewPage;
    }
 
    
    public static java.util.Date toDate(java.sql.Timestamp timestamp) 
    {
    long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
    return new java.util.Date(milliseconds);
    }
    
    
    public static boolean isFutureDate(Date theDate, int days, String timeZone)
    {
        boolean returnVal = false;
        
        // Date to`day = new Date(timeZone);
        //today.compareTo(theDate)
        
        Calendar calendarCurrent = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        
        //TimeZone timeZoneObj = TimeZonetimeZone);
        Calendar calendarFuture = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
        calendarFuture.setTime(theDate);
        
        if (calendarFuture.after(calendarCurrent)) 
        {
                returnVal=true;
         }
    return returnVal;
    }
    
  // Check if given string is a number (digits only)
  public static boolean isNumber(String string) {
      return string.matches("^\\d+$");
  }

  // Check if given string is numeric (-+0..9(.)0...9)
  public static boolean isNumeric(String string) {
      return string.matches("^[-+]?\\d+(\\.\\d+)?$");
  }

  // Check if given string is number with dot separator and two decimals.
  public static boolean isNumberWith2Decimals(String string) {
    return string.matches("^\\d+\\.\\d{2}$");
  }
  
}
