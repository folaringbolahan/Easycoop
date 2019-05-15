package com.sift.financial.utility;

import java.io.UnsupportedEncodingException; 
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 

public class AeSimpleMD5 {
	
 private static String convertToHex(byte[] data) 
 { 
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) { 
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do { 
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    } 
	 
    public static String MD5(String text)   throws NoSuchAlgorithmException, UnsupportedEncodingException  
    { 
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }
    
    
    public static String SaltedMD5(String text, String theSalt)   throws NoSuchAlgorithmException, UnsupportedEncodingException  
    { 
        return MD5(text + theSalt);
    } 
    
   
    public static void main(String[] args) { 
    	
    	
    	//System.out.println(xMethod(1, 1l));
     
        String rawString = "1234567890";
 
        try { 
            System.out.println("MD5 hash of string: " + AeSimpleMD5.MD5(rawString));
        } catch (NoSuchAlgorithmException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) { 
            // TODO Auto-generated catch block 
            e.printStackTrace();
        } 
    } 
    
    
    public static String formatString(String string, String mask)   throws java.text.ParseException {
      javax.swing.text.MaskFormatter mf =     new javax.swing.text.MaskFormatter(mask);
      mf.setValueContainsLiteralCharacters(false);
      return mf.valueToString(string);
  }
    
    public static long xMethod(int n, long l) {
        //System.out.println("int, long");
        return n;
        }


}
