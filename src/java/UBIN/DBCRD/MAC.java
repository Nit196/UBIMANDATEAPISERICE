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


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Security;
import java.util.zip.CRC32;

import xjava.security.Cipher;
import xjava.security.Parameterized;
import cryptix.provider.Cryptix;
import cryptix.provider.key.RawKey;
import cryptix.provider.key.RawSecretKey;
import cryptix.util.core.Hex;
import java.security.NoSuchAlgorithmException;


public class MAC {
 
    
    Cipher des = null;

    RawSecretKey desKey = null;
    
   /* public MAC() {
		Security.addProvider(new Cryptix());
    }*/
    
    public String getTripleDesValue(final String pin, final String key1,
    	    final String key2, final String key3) throws Exception {
        	String decryptedKey= null;
    		try {
    			Security.addProvider(new Cryptix());
    			decryptedKey= getDexValue(pin, key1);
    			
    			decryptedKey = binary2hex(asciiChar2binary(decryptedKey)).toUpperCase();
    			System.out.println("Decryption After Key 1 :: " + decryptedKey +"::::key1 ::"+key1);

    			decryptedKey = getHexValue(decryptedKey, key2);
    			System.out.println("Decryption After Key 2 :: " + decryptedKey +"::::key2 ::"+key2);

    			decryptedKey = getDexValue(decryptedKey, key3);
    			
    			decryptedKey = binary2hex(asciiChar2binary(decryptedKey)).toUpperCase();
    			System.out.println("Decryption After Key 3 :: " + decryptedKey+"::::key3 ::"+key3);

    			
    		} catch (final Exception e) {
    			 e.printStackTrace();
    		}
    		return decryptedKey;
        }
    
    public String getDexValue(final String pin, final String key)
    		throws Exception {
        	 byte[] ciphertext = null;
        	 byte[] pinInByteArray = null;
    		try {
    			des = Cipher.getInstance("DES/ECB/NONE", "Cryptix");
    			desKey = new RawSecretKey("DES", Hex.fromString(key));

    			des.initDecrypt(desKey);
    			pinInByteArray = Hex.fromString(pin);
    			ciphertext = des.crypt(pinInByteArray);
    			
    		} catch (final Exception e) {
    			e.printStackTrace();
    		}
    		return toString(ciphertext);
        }
    
    public String toString(final byte[] temp) {
    	final char ch[] = new char[temp.length];
    	for (int i = 0; i < temp.length; i++) {
    	    ch[i] = (char) temp[i];
    	}
    	final String s = new String(ch);
    	return s;
        }
    
    public String asciiChar2binary(final String asciiString) {
		if (asciiString == null) {
			return null;
		}
		String binaryString = "";
		String temp = "";
		int intValue = 0;
		for (int i = 0; i < asciiString.length(); i++) {
			intValue = (int) asciiString.charAt(i);

			temp = "00000000" + Integer.toBinaryString(intValue);
			binaryString += temp.substring(temp.length() - 8);
		}
		return binaryString;

    }
    
    
    public String binary2hex(final String binaryString) {
  		if (binaryString == null) {
  			return null;
  		}
  		String hexString = "";
  		for (int i = 0; i < binaryString.length(); i += 8) {
  			String temp = binaryString.substring(i, i + 8);
  			// System.out.println(" byte :"+ temp);
  			int intValue = 0;
  			for (int k = 0, j = temp.length() - 1; j >= 0; j--, k++) {
  			intValue += Integer.parseInt("" + temp.charAt(j))
  			* Math.pow(2, k);

  			}
  			temp = "0" + Integer.toHexString(intValue);
  			hexString += temp.substring(temp.length() - 2);

  		}
  		return hexString;
      }
    
    
  
    
    
    public static String getHexValue(final String pin, final String key) throws Exception {
		try {
			Cipher des = null;
			RawSecretKey desKey = null;
			des = Cipher.getInstance("DES/ECB/NONE", "Cryptix");
			desKey = new RawSecretKey("DES", Hex.fromString(key));
			des.initEncrypt(desKey);
			final byte[] pinInByteArray = Hex.fromString(pin);
			final byte[] ciphertext = des.crypt(pinInByteArray);
			return (Hex.toString(ciphertext));
		} catch (final Exception e) {
			throw e;
		}
	}
    
    public String hexToString(String txtInHex)  

    {  

         byte [] txtInByte = new byte [txtInHex.length() / 2];  

         int j = 0;  

         for (int i = 0; i < txtInHex.length(); i += 2)  

         {  

                txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);  

         }  

         return new String(txtInByte);  

     }
    
    public String getTripleHexValue(final String pin, final String key1,
    	    final String key2, final String key3) throws Exception {

    		try {
    			Security.addProvider(new Cryptix());
    			String encryptedKey = getHexValue(pin, key1);
    			encryptedKey = getDexValue(encryptedKey, key2);
    			encryptedKey = binary2hex(asciiChar2binary(encryptedKey)).toUpperCase();
    			encryptedKey = getHexValue(encryptedKey, key3);
    			return encryptedKey;
    		} catch (final Exception e) {
    			throw e;
    		}
        }
    
    
        public  String alpha2Hex(String data) {
	  		char[] alpha = data.toCharArray();
	  		StringBuffer sb = new StringBuffer();
	  		for (int i = 0; i < alpha.length; i++) {
	  			int count = Integer.toHexString(alpha[i]).toUpperCase().length();
	  			if (count <= 1) {
	  				sb.append("0").append(
	  						Integer.toHexString(alpha[i]).toUpperCase());
	  			} else {
	  				sb.append(Integer.toHexString(alpha[i]).toUpperCase());
	  			}
	  		}
	  		return sb.toString();
	  	}

          public  String rightPadZeros(String Str) {
	        if(null == Str){
	                return null;
	        }
	        String PadStr = new String (Str);
	      
	        for (int i = Str.length ();(i%8)!=0; i++) {
	            PadStr = PadStr + '^';
	        }
	        return PadStr;
	    }
    
    /**
     *  ################### End $$$$$$$$$$$$$$$$$$$$$$$$$
     */
    
     public static void main(String[] args) throws NoSuchAlgorithmException {
         
         
     }
     public String encryptText(String key, String valueToBeEncrypted) throws 	Exception {
        	String enc1="";
		String value="";
		String encadd="";
		String key1 = "";
		String key2 = "";
		String key3 = "";
		String checking=""; 
                    try {
                         key1 = alpha2Hex(key.substring(0,8));
                         key2 = alpha2Hex(key.substring(8,16));
                         key3 = alpha2Hex(key.substring(16,24));				if((valueToBeEncrypted.length()%8)!=0)
                        {
                         valueToBeEncrypted = rightPadZeros(valueToBeEncrypted);
                        }
                        for(int i=0;i<valueToBeEncrypted.length();i=i+8)
                          {			
                           value= valueToBeEncrypted.substring(i, i+8);
                           checking = checking +  alpha2Hex(value);
                           enc1 = getTripleHexValue(alpha2Hex(value),key1,key2,key3);
                            encadd=encadd+enc1;			
                           }			
                        return encadd;
                        } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                        }finally{
                         enc1 = null;
                        value = null;
                        encadd = null;
                        key1 = null;
                        key2 = null;
                        key3 = null;
                        }
        }
        public String decryptText(String key, String valueToBeDecrypted) throws Exception {		
        String key1 = "";
        String key2 = "";
        String key3 = "";
        try{
                key1 = alpha2Hex(key.substring(0, 8));
                key2 = alpha2Hex(key.substring(8, 16));
                key3 = alpha2Hex(key.substring(16, 24));			
        String decryptedStr= getTripleDesValue(valueToBeDecrypted, key3, key2, key1);
        decryptedStr=hexToString(decryptedStr);			
        if(decryptedStr.startsWith("<"))
        {
        decryptedStr= decryptedStr.substring(0 , decryptedStr.lastIndexOf('>')+1);
        }
        else
        {
        decryptedStr= decryptedStr.substring(0 , decryptedStr.lastIndexOf('&')+1);
        }
        return decryptedStr;				
        }catch (Exception e) {
        return null;
        }finally{
        key1 = null;
        key2 = null;
        key3 = null;
        }
        }    
    
    
    
}
