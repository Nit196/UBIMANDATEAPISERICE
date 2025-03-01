/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UBIN.DBCRD;

/**
 *
 * @author puneet.jain
 */
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;


public class Crypt {
    
    public  String key = "Uco1234567890123";
   
    public  String encrypt(String input) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
    {     //  String input = "FSS|103.74.181.20|500201923104024805|4000000000000002|Uco1234567890123|10156861A6735D44805E485E7097257A";
    	 final String key = "Uco1234567890123";
    	 final javax.crypto.spec.SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
    	 final javax.crypto.Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
    	 cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    	 byte [] encryptedValue = cipher.doFinal(input.getBytes());
    	 return new String(org.apache.commons.codec.binary.Hex.encodeHex(encryptedValue));
    }

    public  void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {        
      

        String EncryptedString = encrypt("FSS|103.74.181.20|500201923104024805|4000000000000002|Uco1234567890123|10156861A6735D44805E485E7097257A");        
        
        System.out.println("[EncryptedString]:"+EncryptedString);
        
    }
    
}
