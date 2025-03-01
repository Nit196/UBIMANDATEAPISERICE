/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NPCI;

/**
 *
 * @author puneet.jain
 */


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.AlgorithmConstraints;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.Properties;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.VerificationJwkSelector;
import org.jose4j.jws.AlgorithmIdentifiers;



//import org.bouncycastle.openpgp.PGPException;
//import org.bouncycastle.openpgp.PGPPrivateKey;
//import org.bouncycastle.openpgp.PGPPublicKey;
//import org.bouncycastle.openpgp.operator.jcajce.JcaPGPKeyConverter;


public class JSON_JWS {
    
     public PrivateKey privateKey=null;
     public PublicKey pk=null;
     
    private  String outputFile			=	null;
    private  String NPCICrtPath			=	null;
    private  String BankcrtPath			=	null;
    private  String BankCrtPwd			=	null;
    private  String APIMCrtPath			=	null;
    private  String BANKPUBPath			=	null;
    
     
     
      private  String propsFile="C:\\APIEmandate\\Emandateproperties.properties";
              //"C:\\Users\\puneet.jain\\Documents\\EmandAPIService\\Emandateproperties.properties";
    
    
    
    
     public  void value (String _outputFile) throws FileNotFoundException, IOException {
		outputFile	=	_outputFile;
		Properties properties = new Properties();
               // String currentUsersHomeDir =   Encryption.class.getProtectionDomain().getCodeSource();
 
               // propsFile=currentUsersHomeDir+"//Emandateproperties.properties";
		properties.load(new FileInputStream(propsFile));
		NPCICrtPath = properties.getProperty("NPCICRT");
		BankcrtPath = properties.getProperty("BANKPFX");
                BankCrtPwd = properties.getProperty("password");
                APIMCrtPath=properties.getProperty("APIMCrtPath");
                BANKPUBPath=properties.getProperty("BANKPUBPath");
                  System.out.println("JWS Class Path : " +"NPCICrtPath |"+NPCICrtPath+"|BankcrtPath|"+BankcrtPath+"|BANKPUBPath|"+BANKPUBPath);
                
	}
    
      public String  verify_JWS(String sgnature)
      {
           try{
               value(propsFile);
               
                // Create a new JsonWebSignature object
                org.jose4j.jws.JsonWebSignature jws= new org.jose4j.jws.JsonWebSignature();
                org.jose4j.jws.JsonWebSignature jws1= new org.jose4j.jws.JsonWebSignature();
                getPGPPublicKey();
                String verid="";
                jws1.setKey(pk);
                jws1.setAlgorithmHeaderValue(org.jose4j.jws.AlgorithmIdentifiers.RSA_USING_SHA512);
                 jws1.setCompactSerialization(sgnature);
               // jws.setKey(privateKey);
                final boolean verified = jws1.verifySignature();            
              System.out.println("JWSSignature Verify : " +verified);
              return ("JWSSignature Verify : " +verified);
            }
           
          catch(Exception er)
        {
            System.out.println("Exception in JWS : "+er.getMessage());
            return "Exception :" +er;
        }
      }
     
   public String  getJWS(String payload)
    {
        
        String JWSSignature="";
        try
        {
            value(propsFile);
                org.jose4j.jws.JsonWebSignature jws= new org.jose4j.jws.JsonWebSignature();
                
                getPGPPrivateKey( );
                
                jws.setPayload(payload);//"Hello world");
                jws.setKey(privateKey);
                jws.setAlgorithmHeaderValue(org.jose4j.jws.AlgorithmIdentifiers.RSA_USING_SHA512);
                JWSSignature=jws.getCompactSerialization();
                System.out.println(JWSSignature ); 
                getPGPPublicKey();
                String verid="";
                jws.setKey(pk);
               
              
              return JWSSignature;
        }
        catch(Exception er)
        {
            System.out.println("Exception in JWS : "+er.getMessage());
            return "Exception :" +er;
        }
    }
    
      public void getPGPPublicKey( ) throws  Exception {
        try {
            
            final FileInputStream fin = new FileInputStream(BANKPUBPath);
            final CertificateFactory f = CertificateFactory.getInstance("X.509");
            final Certificate certificate = f.generateCertificate(fin);
            this.pk = certificate.getPublicKey();
            final String algo = this.pk.getAlgorithm();
            
        }
        catch (Exception err) {
            throw err;
        }
        
    }
    
    
    
     public void getPGPPrivateKey( ) throws  Exception {
        try {
            
            
            KeyStore ks = null;
            ks = KeyStore.getInstance("pkcs12");
            ks.load(new FileInputStream(BankcrtPath), BankCrtPwd.toCharArray());
            final String alias = ks.aliases().nextElement();
            this.privateKey = (PrivateKey)ks.getKey(alias, BankCrtPwd.toCharArray());
            
        }
        catch (Exception err) {
            throw err;
        }
        
    }
     
     public static void  main(String[] args)
     {
         try
         {
             JSON_JWS _cls=new JSON_JWS();
             
            String Data="";
            Data=_cls.getJWS("Hello");
            
            Data=_cls.verify_JWS(Data);
             
               System.out.println("JWSSignature Verify : " );
         }
         catch(Exception er)
         {
               System.out.println("JWSSignature Verify : " +er.getMessage());
         }
   }
    
    
}
