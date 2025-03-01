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

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordMD5 {
    
     public static void main(String[] args) throws NoSuchAlgorithmException {

        String password = "FSS|103.74.181.20|500201923104024805|4000000000000002|Uco1234567890123";
        // 10156861A6735D44805E485E7097257A
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        System.out.println(sb.toString().toUpperCase());

    }
    
}
