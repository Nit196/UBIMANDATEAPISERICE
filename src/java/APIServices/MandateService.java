/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */


package APIServices;

import NPCI.DocumentWrapper;
import NPCI.Encodeco;
import NPCI.Encryption;
import NPCI.JSON_JWS;
import UBIN.DBCRD.DBAPI.Cls_EncryDecry;
import UBIN.DBCRD.EncryptDecrypt;
import UBIN.IB.ShoppingMallSymmetricCipherHelper;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlElement;


/**
 *
 * @author puneet.jain
 */
@WebService(serviceName = "MandateService")
public class MandateService {

     @XmlElement(required = true)
    private DocumentWrapper arg0;
  
     
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
   
    @WebMethod(operationName = "Encrypt")
    public String Encryption(String Val)
    {//throws CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, FileNotFoundException, KeyStoreException {
         try{
                String encry= "";
                
                Encryption _ClsObj=new Encryption();
                
                encry= _ClsObj.encry(Val);
                
                return  encry.trim() ;
         }
         catch(Exception ex)
         {
             return "Exception : "+ ex.getMessage() ;
           }
        
    }
    
    @WebMethod(operationName = "JWSSign")
    public String Get_JWS(String Val) {
        // throws CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, FileNotFoundException, KeyStoreException {
         
       try{
                String Decry= "";
                        JSON_JWS _ClsObj=new JSON_JWS();
                        Decry= _ClsObj.getJWS(Val);
                return  Decry.trim();
         }
         catch(Exception ex)
                {
                       return "Exception:"+ ex.getMessage() ;
                }
    }
    
    
    
     @WebMethod(operationName = "JWSSignVerification")
    public String Verify_JWS(String Val) {
        // throws CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, FileNotFoundException, KeyStoreException {
         
       try{
                String Decry= "";
                        JSON_JWS _ClsObj=new JSON_JWS();
                        Decry= _ClsObj.verify_JWS(Val);
                return  Decry.trim();
         }
         catch(Exception ex)
                {
                       return "Exception:"+ ex.getMessage() ;
                }
    }
    
    
    
    
    
            // Working Code for Decryption
    @WebMethod(operationName = "Decrypt")
    public String Decryption(String Val) {
        // throws CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, FileNotFoundException, KeyStoreException {
         
       try{
                String Decry= "";
                System.out.println("Reeived data in Request:---->"+Val);
                        Encryption _ClsObj=new Encryption();
                        try
                        {
                        Decry= _ClsObj.Decry(Val);
                        }
                        catch(Exception ex)
                        {
                            System.out.println("EXception while Decryption :;::"+ex);
                        }
                System.out.println("After Decryption  data is:---->"+Decry); 
                return  Decry.trim();
         }
         catch(Exception ex)
                {
                       return "Exception:"+ ex.getMessage() ;
                }
    }
    @WebMethod(operationName = "NBEcryption")
    public String NBEcryption(String DataValue,String key) {
        //TODO write your implementation code here:
        try{
//               String Key = "29304E87583278923487A64389764380763087426708234658764320F8764387645678023467843678657678243678934208346543763426342087364764378623487165321780324643278643287634807347867463247864326D43204376743286743290874784C3260409A876";			
               // String QS = "ShoppingMallTranFG.TRAN_CRN~INR|ShoppingMallTranFG.TXN_AMT~1.00|ShoppingMallTranFG.PID~000000036060|ShoppingMallTranFG.PRN~2018070633461631|ShoppingMallTranFG.ITC~MbPT";
                System.out.println("DateValue:--?"+DataValue);
                //System.out.println("key:--?"+key);
//                String encryptedVal=null;
               // ShoppingMallSymmetricCipherHelper _obj =new ShoppingMallSymmetricCipherHelper();
               // String encryptedVal=_obj.encrypt(DataValue, key, "AES");  //UBI
              Encodeco _obj=new Encodeco(); // PSB
               String encryptedVal=_obj.encryptAES(DataValue, key);   // PSB
                
                System.out.println("encryptedVal value:-->"+encryptedVal);
                return encryptedVal;
        }
        catch(Exception ex)
        {
            System.out.println("Custom Exception :-->"+ex);
            ex.printStackTrace();
            return "Exception : " +ex.getMessage();
        }
        
    }
    @WebMethod(operationName = "NBDecrypt")
    public String NBDecrypt(String DataValue,String key) {
        //TODO write your implementation code here:
        
        try{
            //String Key = "29304E87583278923487A64389764380763087426708234658764320F8764387645678023467843678657678243678934208346543763426342087364764378623487165321780324643278643287634807347867463247864326D43204376743286743290874784C3260409A876";			
            String encryptedVal=null;
           
           // ShoppingMallSymmetricCipherHelper _obj =new ShoppingMallSymmetricCipherHelper(); // FOR UBI
           Encodeco _obj=new Encodeco();   // For PSB
           
           // encryptedVal=_obj.decrypt(DataValue, key, "AES");// Here Encrypting
             encryptedVal=_obj.decryptAES(DataValue,key);
            
	  // System.out.println("Decrypt String --->"+ encryptedVal);
            return encryptedVal;
        }
        catch(Exception ex)
        {
            return "Exception : " + ex.getMessage();
        }
        
        
        
    }
    @WebMethod(operationName = "CHKS256")
    public String GetCHKSUM (String DataValue) {
        //TODO write your implementation code here:
        
        try{
            
          
             ShoppingMallSymmetricCipherHelper _obj =new ShoppingMallSymmetricCipherHelper();

             String encryptedVal="";
             encryptedVal=_obj.SHA256CHKSUM(DataValue); // Here Encrypting
	      //System.out.println("Decrypt String --->"+ encryptedVal);



                        return encryptedVal;
        }
        catch(Exception ex)
        {
            return "Exception : " + ex.getMessage();
        }
        
        
        
    }
    @WebMethod(operationName = "DBREQ")
    public String DBGetreq (String Key ,String DataValue){
        
        String res ="";
        try{
            
            EncryptDecrypt _ClsObj=new EncryptDecrypt();
            
            res =_ClsObj.encryptText(Key, DataValue);
            return res;
        }
        catch(Exception err)
        {
            res = "Exception :" +err.getMessage();
            return res;
        }
    }
    
    
    @WebMethod(operationName = "XmlSigning")
    public String XmlSignings(String data)
    {
        
         String res ="";
        try{
          System.out.println("Data For Singing   :---->"+data);
          Encryption _ClsObj=new Encryption();
            
            res =_ClsObj.signXmlData(data);
            return res;
        }
        catch(Exception err)
        {
            res = "Exception :" +err.getMessage();
            return res;
        }
       
        
    }
    
    @WebMethod(operationName = "XmlVerification")
    public String XmlVerifications(String data)
    {
        
        String res ="";
        try{
            
           Encryption _ClsObj=new Encryption();
           System.out.println(" Data For Verification :---->"+data);
            res =_ClsObj.verifyXmlData(data);
            return res;
        }
        catch(Exception err)
        {
            res = "Exception :" +err;
            return res;
        }
       
        
    }
    
    
    
    
    
    
@WebMethod(operationName = "DBRES")
public String DBGetres(String DataValue)
{
    String res = null; 
   try 
   {
        Encryption encp = new Encryption();
        System.out.println("String xml data is ----"+DataValue);
        res = encp.signXmlData(DataValue); 
    } catch (Exception err) 
    {
        System.out.println("Error in DBGetres:"+err.getMessage());
    }
    return res;
}
    
    
    
    
    
    
    
    
     /*---------------------------------------------------------- Direct Debit Card Authentication -------------------------------------------------------------*/
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "DirectDebtiCardReqEncry")
    public String DirectDebtiCardReqEncry(String plainTxt) {
        Cls_EncryDecry _clsobj =new Cls_EncryDecry();
        try
        {
            String res=_clsobj.encrymessage(plainTxt, "9d9e1fc5999a4b6ea72b9bd2aacff093");
            System.out.println("Encrypt Text : "+ res);
            return res.trim();
        }
        catch(Exception err)
        {
            System.out.println("Exception : "+ err.getMessage());
            return ("Exception :"+ err.getMessage()).trim();
        }
    }

  
    @WebMethod(operationName = "DirectDebtiCardReqDecry")
    public String DirectDebtiCardReqDecry(String encryTxt) {
        //TODO write your implementation code here:
        Cls_EncryDecry _clsobj =new Cls_EncryDecry();
        try
        {
            String res=_clsobj.dcrymessage(encryTxt, "9d9e1fc5999a4b6ea72b9bd2aacff093");
            System.out.println("Decrypt Text : "+ res);
            return res.trim();
        }
        catch(Exception err)
        {
            System.out.println("Exception : "+ err.getMessage());
            return ("Exception :"+ err.getMessage()).trim();
        }
        
    }
    
    @WebMethod(operationName = "DirectDebitCardAPIMEncrypt")
    public String DirectDebitCardAPIMEncrypt(String Val) {
        //throws CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, FileNotFoundException, KeyStoreException {
         try
         {
                String encry= "";
                Encryption _clsobj=new Encryption();
                encry= _clsobj.Bankencry(Val);
                return  encry.trim() ;
         }
         catch(Exception ex)
                {
                       return "Exception : "+ ex.getMessage() ;
                }
        
    }
    
    
   
    
    
    
    
}
