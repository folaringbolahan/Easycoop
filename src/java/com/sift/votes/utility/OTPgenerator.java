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
//import org.springframework.security.crypto.codec.Base64;
//import org.apache.catalina.util.Base64;

/**
 *
 * @author kola
 */
public class OTPgenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String SPECCHAR = "@&$#%*";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    
     public static String getSalt(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
     
     public static String generateOTP() {
         int length = 10;
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
     
    public static byte[] hash(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        //Arrays.fill(password, Character.MIN_VALUE);
        byte [] skb = null;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            skb = skf.generateSecret(spec).getEncoded();
            
            
            SecretKey sk = skf.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(sk.getEncoded(), "AES");
            
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            skb = cipher.doFinal(password.getBytes("UTF-8"));
            
            
        } catch (NoSuchPaddingException e) {
            System.out.println("Error while hashing a password 1: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (InvalidKeyException e) {
            System.out.println("Error while hashing a password 2: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            System.out.println("Error while hashing a password 3: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (BadPaddingException e) {
            System.out.println("Error while hashing a password 4: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error while hashing a password 5: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (InvalidParameterSpecException e) {
            System.out.println("Error while hashing a password 6: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error while hashing a password 7: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (InvalidKeySpecException e) {
            System.out.println("Error while hashing a password 8: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
        return skb;
    }
    public static String generateSecurePassword(String password, String salt) {
        String returnValue = null;
        byte[] securePassword = hash(password, salt.getBytes());
        //returnValue = Base64.getEncoder().encodeToString(securePassword);
        returnValue = Base64.encodeBase64String(securePassword);
        return returnValue;
    }
    
    public static boolean verifyUserPassword(String providedPassword,
    String securedPassword, String salt)
    {
        boolean returnValue = false;
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        return returnValue;
    }
    
    public static String decrptSecurePassword(String hashedpassword, String salt) {
        String returnValue = null;
        //byte[] securePassword = hash(hashedpassword.toCharArray(), salt.getBytes());
        //returnValue = Base64.getEncoder().encodeToString(securePassword);
        byte[] securePassword = Base64.decodeBase64(hashedpassword);
        returnValue = reversehash(new String(securePassword), salt.getBytes());
        return returnValue;
    }
    
    
    public static String reversehash(String password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
        //Arrays.fill(password, Character.MIN_VALUE);
        byte [] skb = null;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            skb = skf.generateSecret(spec).getEncoded();
            
            
            SecretKey sk = skf.generateSecret(spec);
            SecretKey secret = new SecretKeySpec(sk.getEncoded(), "AES");
            
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
            skb = cipher.doFinal(password.getBytes("UTF-8"));
            
            
        } catch (NoSuchPaddingException e) {
            System.out.println("Error while hashing a password 1: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (InvalidKeyException e) {
            System.out.println("Error while hashing a password 2: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (IllegalBlockSizeException e) {
            System.out.println("Error while hashing a password 3: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (BadPaddingException e) {
            System.out.println("Error while hashing a password 4: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error while hashing a password 5: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (InvalidParameterSpecException e) {
            System.out.println("Error while hashing a password 6: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error while hashing a password 7: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } catch (InvalidKeySpecException e) {
            System.out.println("Error while hashing a password 8: " + e.getMessage());
            //throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
        return new String(skb);
    }
}
