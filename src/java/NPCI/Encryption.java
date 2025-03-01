/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NPCI;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.Socket;
import java.security.InvalidKeyException;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import java.util.Base64;

import static java.util.Collections.singletonList;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import static javax.xml.crypto.dsig.CanonicalizationMethod.EXCLUSIVE;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import static javax.xml.crypto.dsig.Transform.ENVELOPED;
import javax.xml.crypto.dsig.XMLSignature;

import java.io.FileInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.w3c.dom.NodeList;

import java.security.Provider;
import java.security.Security;
import java.util.Collections;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;

import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Encryption {

    private String outputFile = null;
    private String NPCICrtPath = null;
    private String BankcrtPath = null;
    private String BankCrtPwd = null;
    private String APIMCrtPath = null;
    private String propsFile = null;
    
    
    public static String  getPropConnection(String path) throws IOException
   {
	         InputStream input=null;
		 Properties properties = new Properties();
		 String dir = System.getProperty("user.dir");
		 String prpptah = dir +"\\"+"propertites"+"\\" + "Emandateproperties.properties";
                 File file=new File(prpptah);
		 input = new FileInputStream(file);
		 properties.load(input);
                return  properties.getProperty(path);
 }

    public void value(String _outputFile) throws FileNotFoundException, IOException {
      
      NPCICrtPath=getPropConnection("NPCICRT");
      BankcrtPath=getPropConnection("BANKPFX");
      BankCrtPwd=getPropConnection("password");
      APIMCrtPath=getPropConnection("APIMCrtPath");

    }

    //Encryption Running code
    //private static Logger logger = Logger.getLogger (Encryption.class); 
    public String encry(String value) throws FileNotFoundException, CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, KeyStoreException {
        value("");
        FileInputStream fin = new FileInputStream((NPCICrtPath));//"C:\\Users\\puneet.jain\\Desktop\\Emandate Outward\\EmandateAPI\\Punjab & Sind Bank\\PSBAPIEmandate\\PSBAPIEmandate\\Resources\\onmag_cert.cer"));

        CertificateFactory f = CertificateFactory.getInstance("X.509");

        //              KeyStore keystore = KeyStore.getInstance("PKCS12");
        //                   keystore.load(fin, "psb@1234".toCharArray());
        //                   
        //                   String alias = (String) keystore.aliases().nextElement();
        //X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias);
        X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);

        PublicKey publicKey = certificate.getPublicKey();

          //Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
         // Cipher cipher = Cipher.getInstance("RSA");
           Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            //Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");//psb
              cipher.init(Cipher.ENCRYPT_MODE, publicKey);
              byte[] cipherData = cipher.doFinal(value.getBytes());

        String encodedData = Base64.getEncoder().encodeToString(cipherData);

        return encodedData;
    }
    
    
    public String signXmlData(String docs) throws Exception {        
        value("");
        String xmlsigneddata = null;
        java.io.FileInputStream fin = new java.io.FileInputStream(BankcrtPath);
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(fin, BankCrtPwd.toCharArray());
        String alias = (String) keystore.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, BankCrtPwd.toCharArray());
        X509Certificate cert = (X509Certificate) keystore.getCertificate(alias);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xmlDocument = builder.parse(new InputSource(new StringReader(docs)));
        String providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", (Provider) Class.forName(providerName).newInstance());
        DigestMethod digestMethod = fac.newDigestMethod(DigestMethod.SHA256, null);
        Transform transform = fac.newTransform(ENVELOPED, (TransformParameterSpec) null);
        Reference reference = fac.newReference("", digestMethod, singletonList(transform), null, null);
        SignatureMethod signatureMethod = fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", null);
        CanonicalizationMethod canonicalizationMethod = fac.newCanonicalizationMethod(EXCLUSIVE, (C14NMethodParameterSpec) null);
        SignedInfo si = fac.newSignedInfo(canonicalizationMethod, signatureMethod, singletonList(reference));
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(cert.getPublicKey());
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
        DOMSignContext dsc = new DOMSignContext(privateKey, xmlDocument.getDocumentElement());
        XMLSignature signature = fac.newXMLSignature(si, ki);
        signature.sign(dsc);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
        xmlsigneddata = writer.toString();
        System.out.println("XML document signed successfully: " + xmlsigneddata);
        return xmlsigneddata;    
    }
    
        
    public String verifyXmlData_old(String signedXml) throws Exception {
        value("");
        boolean isValid = false;
        String originalXmlData = null;
        
        // Load the certificate containing the public key for verification
        FileInputStream fin = new FileInputStream(NPCICrtPath);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(fin);
        System.out.println("Public key is:------"+cert.getPublicKey());

        // Prepare the XML document to be verified
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(signedXml)));

        // Find the Signature element
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Cannot find Signature element");
        }

        // Create a DOMValidateContext using the public key from the certificate
        DOMValidateContext valContext = new DOMValidateContext(cert.getPublicKey(), nl.item(0));

        // Unmarshal the XMLSignature
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        // Validate the XMLSignature
        isValid = signature.validate(valContext);

        if (isValid) 
        {
            System.out.println("XML document verification succeeded.");
            // Extract the original XML data from the signed document
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            originalXmlData = writer.toString();
            return originalXmlData;
        } else {
            System.out.println("XML document verification failed.");
        }

        return originalXmlData;
    }
    
    
    public String verifyXmlData(String xmlFilePath) throws SAXException, IOException, ParserConfigurationException, CertificateException, MarshalException, XMLSignatureException
       {
        value("");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);  // Ensure namespace awareness
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlFilePath)));

        // Load public key from file
        FileInputStream fis = new FileInputStream(NPCICrtPath);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);

        // Find Signature element
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new RuntimeException("Cannot find Signature element");
        }

        DOMValidateContext valContext = new DOMValidateContext(cert.getPublicKey(), nl.item(0));
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        
         doc.getDocumentElement().normalize();

            // Locate the Signature element
            NodeList signatureList = doc.getElementsByTagName("Signature");
            
            // Since there should be only one Signature element, assuming it exists
            if (signatureList.getLength() > 0) {
                Node signatureNode = signatureList.item(0); // Assuming only one Signature element
                Node parentNode = signatureNode.getParentNode();
                
                // Remove the Signature element
                parentNode.removeChild(signatureNode);
            }

            // Convert Document object back to XML string
            String modifiedXml = convertDocumentToString(doc);
            
  
        boolean isValid = signature.validate(valContext);
        if (isValid) {
           System.out.println(modifiedXml);
           return modifiedXml;
        } else {
            System.out.println("XML signature verification failed");
         return "Verification Failed..";   
        }
      
    }
    
    
    private static String convertDocumentToString(Document doc) {
        try {
            // Use a transformer or manual approach to convert Document to String
            javax.xml.transform.TransformerFactory tf = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");
            java.io.StringWriter sw = new java.io.StringWriter();
            javax.xml.transform.stream.StreamResult sr = new javax.xml.transform.stream.StreamResult(sw);
            transformer.transform(new javax.xml.transform.dom.DOMSource(doc), sr);
            return sw.toString();
        } catch (javax.xml.transform.TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
     
    // Working Decryption Code  
    public String Decry(String value) throws FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        value("");
        java.io.FileInputStream fin = new java.io.FileInputStream(BankcrtPath);
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(fin, BankCrtPwd.toCharArray());
        String alias = (String) keystore.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, BankCrtPwd.toCharArray());
        X509Certificate rivateKey = (X509Certificate) keystore.getCertificate(alias);
       // Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding", "SunJCE");
       // Cipher cipher = Cipher.getInstance("RSA");//  union
         Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");//psb
         cipher.init(Cipher.DECRYPT_MODE, privateKey);
         System.out.println("Value received in Decry Function:--->"+value);

        return new String(cipher.doFinal(Base64.getDecoder().decode(value)), "UTF-8");

    }

    
    public String Bankencry(String value) throws FileNotFoundException, CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, KeyStoreException {
        value("");
        FileInputStream fin = new FileInputStream((APIMCrtPath));//"C:\\Users\\puneet.jain\\Desktop\\Emandate Outward\\EmandateAPI\\Punjab & Sind Bank\\PSBAPIEmandate\\PSBAPIEmandate\\Resources\\onmag_cert.cer"));
        CertificateFactory f = CertificateFactory.getInstance("X.509");

        //              KeyStore keystore = KeyStore.getInstance("PKCS12");
        //                   keystore.load(fin, "psb@1234".toCharArray());
        //                   
        //                   String alias = (String) keystore.aliases().nextElement();
        //X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias);
        X509Certificate certificate = (X509Certificate) f.generateCertificate(fin);

        PublicKey publicKey = certificate.getPublicKey();

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherData = cipher.doFinal(value.getBytes());
        String encodedData = Base64.getEncoder().encodeToString(cipherData);
        return encodedData;

    }

    
    public String decryptData(String encryptedData) throws Exception {
        value("");
        // byte[] encryptedBytes=null;
        // byte[] decryptedBytes=null;
        byte[] ivBytes = new byte[16];
        java.io.FileInputStream fin = new java.io.FileInputStream(BankcrtPath);//"D:\\OFFICE WORK\\UNION BANK APPLICATION UAT\\Union Application LIVE WITHHOUT DB CARD\\PSIBEMANDATE\\Resources\\BANKCRT.pfx");
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        keystore.load(fin, BankCrtPwd.toCharArray());
        String alias = (String) keystore.aliases().nextElement();
        PrivateKey privateKeys = (PrivateKey) keystore.getKey(alias, BankCrtPwd.toCharArray());

        //System.out.println("====" + privateKeys);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKeys);

        // Decrypt the data
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

        // Convert the decrypted bytes to a string
        String decryptedData = new String(decryptedBytes);
        //  Cipher cipher = Cipher.getInstance("RSA/NONE/PKCS1Padding", "BC");
        // Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        //cipher.init(Cipher.DECRYPT_MODE, privateKeys);
        //try{ encryptedBytes = Base64.getDecoder().decode(encryptedData);
        // decryptedBytes = cipher.doFinal(encryptedBytes);}catch(Exception e){System.out.println(e);}
        return new String(decryptedBytes);

        // Example: new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // Encrypted data (base64 encoded)
        // Initialize the cipher for decryption
    }

    
    public String SocketCode(String data, String port, String ip) {
        String value = null;
        System.out.println("Value is    " + data);
        try {
            int num = Integer.parseInt(port);
            Socket socket = new Socket(ip, num);
            System.out.println("Connected to the server");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stringData", data);
            String jsonData = jsonObject.toString();

            String isoMessage = String.format("ISO_MESSAGE_LENGTH=%04d", jsonData.length()) + ",JSON_DATA=" + jsonData;

            // Send the ISO message to the server
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println(isoMessage);
            System.out.println("Sent message to the server");

            // Receive the response from the server
            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            value = in.readLine();
            System.out.println("Received response from the server: " + value);

            // Close the socket
            socket.close();
            return value;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, MarshalException, XMLSignatureException, FileNotFoundException, NoSuchAlgorithmException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, Exception 
    {      
        
     String xmldata="<?xml version=\"1.0\"  encoding=\"UTF-8\"?><Document xmlns=\"http://npci.org/onmags/schema\"><MndtAuthReq><GrpHdr><MsgId>A8wE7RdHfjjg56ScRLE6Wyqu8Z4vjPUZEao</MsgId><CreDtTm>2024-11-21T11:19:34</CreDtTm><ReqInitPty><Info><Id>MAHB00077000027458</Id><CatCode>L001</CatCode><UtilCode>MAHB00077000027458</UtilCode><CatDesc>Loan</CatDesc><Name>MAHABANK</Name><Spn_Bnk_Nm>MAHABANK</Spn_Bnk_Nm></Info></ReqInitPty></GrpHdr><Mndt><MndtReqId>A8wE7RdHfjjg56ScRLE6Wyqu8Z4vjPUZEao</MndtReqId><Mndt_Type>DEBIT</Mndt_Type><Schm_Nm></Schm_Nm><Ocrncs><SeqTp>RCUR</SeqTp><Frqcy>MNTH</Frqcy><FrstColltnDt>NGuIoaRuTSZYf1+GAPX4jgokSV+XlG8mqjrCUhdvGqUAmgL5gIAt8v4DJGTmbMhzoJK9mqGEzSAqBoszgaDquc61/jnZqetNj6aS0I4vJqBJdbiinDF05ew5B6l2WEWbt4NITZAFsbxi4y+nvqePoNC4j2MCKtO5dILzXF1+umdfQzn5LomvHfJSvt08iwIRF1gemjvW+ASsuIluAn17H0sQWN1DsKg2em9sqYGzgq2RjeZ+9yzDuTwyaGtxMUB9s76xcp68ZSDHKiLurcf9pvtLl99lIH+jJrawNzJpbsNT5iA/kk+TFSr+3OTsLgglwXnPoL4LMjRnu+9ktkrM7w==</FrstColltnDt><FnlColltnDt>oBAEXrO5BhoXGm26ugXqnEoW20083B4bndauo5+GFLc6nTL5VOsUB89/3qxyKos1eTc5v55GYR5T04w+qtqwdQZgfcUkya/r3WKmM0jwVNzgH58pFptk9wg4/c6w2hurixVsdETqrRBnnSWYBRB6bIgZB5KB0ReTax+En5e8Dv9SEZsM5iVixRfWxtv7LWV6TzjG3Saj59InvkfLcEU6Wd2fq1T3csPrRnhEXz1STMayJ/04leuZ7MFyj+VeMUOwm+CJO8abiBKZY6Uxq1P9/8KAJpAWMIkNtPZBlGPBoxSJ/utSL/IIiLTRAS47awGV29Kzg2XfScfrsS8EYrzBDQ==</FnlColltnDt></Ocrncs><ColltnAmt Ccy=\"INR\">ezmnzbW8CnALjW0vPxXgkVBHqYlEJmIHY5O3xtFvq07YhQKkqc1391lFVXtyipjtCAgNHj4/Q/jzGT2fFpmrCZnzQIsCf49Oen2bhYGzUWzuGvZbnjCVj1HW9ANIwvelJyMgKDikxPhS6bNQF8EFVQxU5m2fVjdtMDkRGcnEWCoHhRhuoxCePskic6cXGyCb02rp9nJLQX5Jl94S97x2bb6EcIUPuz+ibEkqGrCcr5b/A0102k2KKCRpjO9fYAbJyUQTK/vJ8CtAZ9xgacP+I1yR+xc/xfgU0mNWg5tjEIp+6W60fHV24HW2xWvcF1Bdu6Sq9M5tBEiLyGP+GBOzMA==</ColltnAmt><Dbtr><Nm>AJIT</Nm><AccNo>eMYhywUQKng6x0GC6LtGfkllPtGA3qwYenKUoS6FdaP8CMLsKOcYdCeEArFPeT2MNTwZOk7AWLndl/wcnhpIL4ccOtKZeklSXd8JgDzYox3uQuJi7Gub3/wo86Q8tya/7jza2yObpdxDPZpJW3IGGLi536ShStWu9Sxb9L2SmLXDr2vWoRD/hQ9c3C6ysaplhM88zh/iVgnG2kQh/sbW7L0CcSbKJLqwOajWeikViVxvb+l0tE8RoA08CZn42brM+QkGUZ+yNSvmN2G1vue2dPygIo4TWjQwrDEOp0l4jdWOCIIYx5Fuc99PYXWOYL8ec4Ewai2VWnvt/3aRZS0HoA==</AccNo><Acct_Type>SAVINGS</Acct_Type><Cons_Ref_No>654656546546546</Cons_Ref_No><Mobile>gnoDjxk+YHgh3i2s0vJgSmm7yFiz1slafeQ7dgtd68yp442wa6ReZq3/baEbqZtaHUoEse1Q4wIEl0CO3Qu7ckghWQf0D20WOv+GXCAPQQSL2YXNy0B1K4ae7SYHFjwwCu8VdomrCpSK/4L6XRqtOZlrmzOHR+gs/CewI356rpYCz6+qQ9ywgKJej4G6+uXe0z5Vt5O9Sl1OQs0ly7268Sc+VhI8A2EvjRaJXJ62yIXlV7LhRzNDygX3DCN7ht4E2uNoFwEd4BofiaEOrab82VBow7+m0mq7iIK4KvuzhchqMl2Vgx+4K96humNTIvvUWsPPyhlh2vj05xGCiYWm3w==</Mobile><Email>SQo8Z/BCEVRwpe9bIEw6MYI/V8AZrRCx5DZJ/iQL0uorjzlAS9H8LRKgXJ5EfPph/DX34jNJyQFPZgGO3H+oOX8SAwpRnl5pJAjGMGcvrP9hOafpqLqiRtqDOMxaDRgHgE31m560EUig61nRkaRXcx36rAxumi6Myb/GulnIPPM4l+05uEXm0YWsfjnb6edQ+a9YV6u6jg1kQqhNo/dneSzIJ7azSimFlYEIRd119RqRVfr5AvImm+2Fq+Wtof4tCp/iburtjgP5GqXH4nfiBwSpI19LDjzg8WWF3zvJ1Q//WrCGLlDzw5HEsjPDuZp07jnd/OGaQctm5pFSwht7iA==</Email><Pan>WZOLb6mLZ3CRaEQX3rcALY86s6uysboU4Gl/gKBuvXc9wjNrCD1gueka1cA6LK65yd41iSzdo2+NBajgqnLcmDdw6X14++TFr5uX5+YOuPgEmgMKYXfCguqvXOyv9LXMVtAaTJgrRT2ge1SpKfEOrxPTsNA6aM3bVBwpM47I0mGhKQ9OW+I4rPOCtJXsptcb9UaCIS0Vh6zIEIoshUUWRKSHH3u56/rOWcIJ3lqtajjqrNH5TOs2lC9GSjmpK4LcZBWECNNj/B7kWuMKFj/1HMz1EbZsrkJ4oTsOlJptvcRgXWbC3Cn0BN11ttTkKGSN4Mk7yT+v/tsefBy/LKC95A==</Pan></Dbtr><CrAccDtl><Nm>MAHABANK</Nm><AccNo>MAHB00077000027458</AccNo><MmbId>MAHB0003007</MmbId></CrAccDtl></Mndt></MndtAuthReq></Document>";
     Encryption emo= new Encryption();
     String signxml=emo.signXmlData(xmldata);
     

     
     String verfiyxml=emo.verifyXmlData(signxml);
     System.out.println("verfiyxml:----------->"+verfiyxml);
    }
    
    
}
