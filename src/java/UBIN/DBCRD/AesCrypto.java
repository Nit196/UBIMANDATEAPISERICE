/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UBIN.DBCRD;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class AesCrypto {
    
    
    public  String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    
    
    
  

    public   int CIPHER_KEY_LEN = 16; //128 bits

  /**
   * Encrypt data using AES Cipher (CBC) with 128 bit key
   *
   * @param key  - key to use should be 16 bytes long (128 bits)
   * @param iv   - initialization vector
   * @param data - data to encrypt
   * @return encryptedData data in base64 encoding with iv attached at end after a :
   */
  
  public  String encrypt(String key, String iv, String data) {

    try {
      IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
      SecretKeySpec secretKey = new SecretKeySpec(fixKey(key).getBytes("UTF-8"), "AES");

      String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
      
        Cipher cipher = Cipher.getInstance(CIPHER_NAME); 
      
      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

      byte[] encryptedData = cipher.doFinal((data.getBytes()));

      String encryptedDataInBase64 = Base64.getEncoder().encodeToString(encryptedData);
      String ivInBase64 = Base64.getEncoder().encodeToString(iv.getBytes("UTF-8"));

      return encryptedDataInBase64 + ":" + ivInBase64;

    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private  String fixKey(String key) {

      
      String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    if (key.length() < CIPHER_KEY_LEN) {
      int numPad = CIPHER_KEY_LEN - key.length();

      for (int i = 0; i < numPad; i++) {
        key += "0"; //0 pad to len 16 bytes
      }

      return key;

    }

    if (key.length() > CIPHER_KEY_LEN) {
      return key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
    }

    return key;
  }

  /**
   * Decrypt data using AES Cipher (CBC) with 128 bit key
   *
   * @param key  - key to use should be 16 bytes long (128 bits)
   * @param data - encrypted data with iv at the end separate by :
   * @return decrypted data string
   */

  public  String decrypt(String key, String data) {

    try {
      String[] parts = data.split(":");

      IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]));
      SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

      Cipher cipher = Cipher.getInstance(CIPHER_NAME);
      cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

      byte[] decodedEncryptedData = Base64.getDecoder().decode(parts[0]);

      byte[] original = cipher.doFinal(decodedEncryptedData);

      return new String(original);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  public  void main(String[] args) {

    String key = "0123456789abcdef"; // 128 bit key
    String initVector = "abcdef9876543210"; // 16 bytes IV, it is recommended to use a different random IV for every message!

    String plain_text = "plain text";
    String encrypted = encrypt(key, initVector, plain_text);
    System.out.println(encrypted);

    String decrypt = decrypt(key, encrypted);
    System.out.println(decrypt);
  }
    
    
}
