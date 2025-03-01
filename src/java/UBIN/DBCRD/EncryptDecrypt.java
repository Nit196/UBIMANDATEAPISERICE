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

import java.security.Security;

import xjava.security.Cipher;
import cryptix.provider.Cryptix;
import cryptix.provider.key.RawSecretKey;
import cryptix.util.core.Hex;




public class EncryptDecrypt {

        public static void main(String[] args) {
            
		String a ="paymentid=141202021009389835&result=NOT+CAPTURED&auth=000000&avr=N&ref=021011000004&tranid=202021009435497&postdate=0728&trackid=EMD28142020120745682&udf1=566802070000108&udf2=Test1&udf3=Test2&udf4=Test3&udf5=&amt=59.00&authRespCode=05&";
                        //"id=16700001&password=password@123&action=1&amt=59.00&currencycode=356&trackid=EMD26432020120554854&langid=USA&responseURL=http://localhost:37458/PSBM/DebitCardResponse&errorURL=http://localhost:37458/PSBM/DebitCarderrResponse&udf1=382060372a954cca9ac28131b19bd9ca&udf2=test1&udf3=test2&udf4=test3&udf5=test4&"; 
                String b= "id=tran333&password=test@123&action=1&langid=USA&currencycode=356&amt=1.00&responseURL=https://qasecure.ccavenue.com/receive/27/M_/servlet/BankRespReceive&errorURL=https://qasecure.ccavenue.com/receive/27/M_/servlet/BankRespReceive&trackid=1234564&udf1=test1&udf2=test2&udf3=test3&udf4=test4&udf5=test5&";
		String key = "014829916327014829916329";
                //"005694645664005694645667";
		//String key = "926238426584926238426587";
		try {
                    EncryptDecrypt _obj=new EncryptDecrypt();
			
			String encData = _obj.encryptText(key, a);
			System.out.println("encData:"+encData);
			
                        encData="75B57DAB0B32301D9BB02495D45929E8635603A45B8FA138AC1CEF782522A30E0062A7EADCA13BB10102154E430F74416287150E0C159DA6052B219D76F1311457D3F455890F87E8012BE5809E74DFD5AF0DD46F7C3F629BF35A404CF22E4DF135983A74060C7F3DC7A174EDEB1348C8326A27B36B111B044D077C300C0B3F1AB9F6DECD1FE6A47A82E6AF2CD462D23C2DB57B673853944B2360999EFC838DE81A0298607BE7539F479B67096F066D0154C4A84C63C87DAE85FD3FDF27E900999A8359BEE97F5D5015EBB94001EDCFE52A011D48D00DDF977F60D50290F579B0DAA88AB4E65C1CE6A28D4DFEFF1F1BD2CBCB0F61C564C7D815FE780EE6EB2FA171339CDFBACAA259034929BD7F643489C916FAC9EFA5DF513C0FA99DEECAA83ED9D1EAA0DC3DD628149925B5774A30CCE18BB1BF73F43FDE04C082731D91EF92308BF363A9A0B0EBAB53DD3F53D9B6CE607C21CAC3E44269371E51E6B9048849618BD9358E854467813A4A07A8F21127C9FD5688EEC2D9984A869FE7BCBC70DCB874BC89B3B74510DC44D27CD392DD46";
			String decData = _obj.decryptText(key,encData);// "1C1A967D16C877543E0A1DD90D2933853F05BB0AA2E6B47BB77D27EEAF32EF02CF6A0A5643F31C78340913929D90879615DFA9CDCCBB03761B9AE87CD76FE8633E0A1DD90D29338545DA582B0F3500BA9375313637690531C6D7F8F7489C7CD8B73F9EC7E1C622DDD06B0809A709C2CB2A6EFD72F36FEB044B73810204E69FC577300F9AAF38E044F0A34694348506778257040533E4FD48A9C61DD83E906AB93110CE0E57A1C548FA589B8F8566FC9F");
                                //"1CD85AA807B4AC1AB211D68077A6C622686943E4827A21D35A385583CC3DA7D5DFB75AD94E88A57FA4C6B663EDD8D7AFF0A65BB45AEA1A2E72F4FDDCACD4B36E7272C1A8E71B44852C4DACF23E2B2CA6B312F025BF1E7BFB23F239083CA6D6C2DECD11882F5AEC0D30A916AD39E32E289ED8A54FE78DBD9DD72665394FBCD543A1C3C14E949C1D8B501D576DD805424750D99E268C44456786925C91E16C5E01E0D7D9342AF6FDF0827736E5592E593516834F0F96650AFC613400AF405E7C2849E2E0DD6B755162B7F844DDA34A1C26F186262289D3414E2A0AB8ABBC7CEB9051566EF6E74AF76B55B6FC63F1DE33EA3628340BB68710AF83F24BE4F6648505CE8FF05CDB52D4B1B3CC765357EDA7874D040795B6B6EF1C6A59539421B69A2A2BA7B9E2B8CCF3B1BC434511233DA931814F22652E9DEDA47AFFABD29A693DC2BA8FC4198D0F18949AA0CE83316144BC2D411CF2E7354F0BF54B0ADC74143BFCE8D1B76296DD6215CDE25834B7805EA116CC42CEC8F679336F77A77D4E26DB0265506497F10B658C591ABB51C14B94372D3AC340784ECE59A74AE0E0716E5D07CEB9B70257DB1D0AE8D04014C774DECB428340EEBDD093B1DBC9BB21E03BCF0608A60D8A1BB09585D0FB5A7E9AB54FC05AC7AA526E2615A79F502484B41C3CA8B48B901EE4743BBFC57441598E2BB675BA5D9C12198A5885");
			System.out.println("decData:"+encData);
			
			//String decData = decryptText(key, "A57A0975F01571F8CA3A6EAE4B33C02BFD58820E72F31DC4E4D7E9CEDD4833BAA0222F58B79EF6E28885256D6C222DBAEB67A97220F9685E065C649229EC111613487CA435A8D53C0D600B1E6BC4C753EECC0D29E3387E9DF04E3251A307ABE32E85E816E049B9BE7D25BB4682699550766A2F6E29BF6ACC67877E045439E69E999942D9EA5C73D9129AB8F626BB4049EB94B27D383CF2C534DF46A2B9FC000E0AD8C5985ADAD54B79A5B61B3C466794D07DE2CCF75FD6C90998F83FF86312FC300D0259D1DF121F96E1584E79629689B797592F949E273A8EDADED5B20E646E25257AA90F8EFECA5F9983FF57D4B1A910DEBA0C5454F9925054B43D5CBF0A1FD36800154B9D5E440597456276681047FC9D80EA95DC483D9A92BEF018D6CC42E17D9F0211AB7452842A5BC745B630CB");
			//System.out.println("decData:"+decData);
			System.out.println("decData:"+decData);
                        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	 public  String encryptText(String key, String valueToBeEncrypted) throws Exception {
	    	
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
                                key3 = alpha2Hex(key.substring(16,24));						
				
                                
                                System.out.println("key1:"+key1);
                                System.out.println("key2:"+key2);
                                System.out.println("key3:"+key3);
                                
				if((valueToBeEncrypted.length()%8)!=0)
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
				//mac = null;
				enc1 = null;
				value = null;
				encadd = null;
				key1 = null;
				key2 = null;
				key3 = null;
			}
	    	
		}
	
	 
	 public  String decryptText(String key, String valueToBeDecrypted) throws Exception {
	    	MAC mac = new MAC();
	    	String key1 = "";
			String key2 = "";
			String key3 = "";
	    	try{
	    		
				key1 = alpha2Hex(key.substring(0, 8));
				key2 = alpha2Hex(key.substring(8, 16));
				key3 = alpha2Hex(key.substring(16, 24));			

				String decryptedStr= mac.getTripleDesValue(valueToBeDecrypted,key3,key2,key1);			
				decryptedStr=mac.hexToString(decryptedStr);			
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
				mac = null;
				key1 = null;
				key2 = null;
				key3 = null;
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
	  
	  public  String getTripleHexValue( String pin,  String key1,
	    	     String key2,  String key3) throws Exception {

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
	  
	  public  String getHexValue(final String pin, final String key) throws Exception {
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

	  
	  
	  public  String getDexValue(final String pin, final String key)
	    		throws Exception {
	        	 byte[] ciphertext = null;
	        	 byte[] pinInByteArray = null;
	        	 Cipher des = null;
	        	    RawSecretKey desKey = null;
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
	  
	   
	    public  String toString(final byte[] temp) {
	    	final char ch[] = new char[temp.length];
	    	for (int i = 0; i < temp.length; i++) {
	    	    ch[i] = (char) temp[i];
	    	}
	    	final String s = new String(ch);
	    	return s;
	        }
	    
	    public  String asciiChar2binary(final String asciiString) {
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
	    
	    public  String binary2hex(final String binaryString) {
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
    
    
}
