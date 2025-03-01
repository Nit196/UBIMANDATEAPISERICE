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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;



public class CipherUtility {
    
    
    private  byte[] lickey = "Uco1234567890123".getBytes(); 	
    
   public  void main(String args[]){
	   
	String strToEncrypt = "FSS|103.74.181.20|500201923104024805|4000000000000002|Uco1234567890123|10156861A6735D44805E485E7097257A";
	System.out.println("Plain Data: "+strToEncrypt);
	String encData = encrypt(strToEncrypt);
	System.out.println("Encrypted Data: "+encData);
	
	String decData = "53344248306459355133725a6c6564494839306950354832766773384c73416a323534647a6a4139444a484578612b517462346948484a323539354b38516b754c48586b5470424d4232717133714b4e434c634b514661523947575a6c586848326e506a6f487a6e46756d695a4d64596d6534626a496a576f4f44394c77316f454f757178455163477a4132582b355371354d2b2b513d3d";
	String decryData = decrypt(decData);
	System.out.println("Decrypted Data: "+decryData);
	
   }
    
    
    private  String toHexString(byte[] data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length; ++i) {
			String s = Integer.toHexString(data[i] & 0XFF);
			buf.append((s.length() == 1) ? ("0" + s) : s);
		}
		return buf.toString();
	}
    
    private  byte [] toByteArray(String data) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < data.length()-1; i+=2) {
			int s = Integer.parseInt(data.substring(i, (i + 2)),16);
			buf.append((char)s);
		}
		return buf.toString().getBytes();
	}

    public  String encrypt(String strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            final SecretKeySpec secretKey = new SecretKeySpec(lickey, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            final String encryptedString = toHexString(Base64.encodeBase64(cipher.doFinal(strToEncrypt.getBytes())));
            return encryptedString;
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting "+e.getLocalizedMessage());
        }
        return null;

    }

    public  String decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            final SecretKeySpec secretKey = new SecretKeySpec(lickey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            final String decryptedString = new String(cipher.doFinal(Base64.decodeBase64(toByteArray(strToDecrypt))));
            return decryptedString;
        }
        catch (Exception e)
        {
        	System.out.println("Error while decrypting "+ e.getLocalizedMessage());
        }
        return null;
    }
    
    
    
}
