/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NPCI;

/**
 *
 * @author Nitinv
 */
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class Encodeco {

    private static SecretKeySpec secretKey;
    private static byte[] key;

    
    public static String encryptAES(String strToEncrypt, String secret) {
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return "Value not Encrypted.....";
    }

    public static String decryptAES(String strToDecrypt, String secret) 
    {
        try {
            
             while (strToDecrypt.length() % 4 != 0) {
            strToDecrypt += "=";  // Add padding
        }
            setKey(secret);
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));

         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDecrypt));
        
        return new String(decryptedBytes);
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return "Value not Decrypted.........";
    }

    
    
    public static void setKey(String myKey) throws UnsupportedEncodingException {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(myKey.getBytes("UTF-8"));
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
  
}



