/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sift.votes.utility;

//import com.sun.jersey.core.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.codec.Base64;
//import org.apache.catalina.util.Base64;

/**
 *
 * @author kola
 */
public class Voterspasswordencoderlegacy implements PasswordEncoder{
    
   
    private String salt="mmm";
    
    
  @Override
  public String encode(CharSequence cs) {
    
      return OTPgenerator.generateSecurePassword(cs.toString(),salt);
   
  }

  @Override
  public boolean matches(CharSequence cs, String string) {
    return OTPgenerator.verifyUserPassword(cs.toString(), string,  salt);
    
  }
  
  
  public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
