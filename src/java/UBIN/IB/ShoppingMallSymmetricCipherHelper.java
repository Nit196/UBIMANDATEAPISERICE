/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UBIN.IB;

/**
 *
 * @author puneet.jain
 */
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ShoppingMallSymmetricCipherHelper {

    private final String mHexKey = "29304E8758327892";
    private final String Algo_DES = "DES";
    private final String Algo_AES = "AES";
    private final String Algo_DESede = "DESede";
    private CipherInstanceThreadLocal cipherThreadLocal = new CipherInstanceThreadLocal();

    public ShoppingMallSymmetricCipherHelper() {
    }

    public int getKeyLength(String cipherName) throws Exception {
        if (cipherName.equalsIgnoreCase(Algo_DES)) {
            return 64;
        } else if (cipherName.equalsIgnoreCase(Algo_AES)) {
            return 128;
        } else if (cipherName.equalsIgnoreCase(Algo_DESede)) {
            return 192;
        }

        throw new Exception(
                "SymmetricCipher.getKeyLength() error - Symmetric cipher algorithm name "
                + cipherName
                + " is not recognized. Thus can not determine key length.");
    }

    public byte[] getKey(String randomId, String cipherName) throws Exception {

        byte[] retr = null;

        int keyBytes = getKeyLength(cipherName) / 8;
        int len = randomId.length();

        String key = randomId;
        if (len < keyBytes) {
            int repeatFactor = keyBytes / len;
            for (int i = 0; i < repeatFactor; i++) {
                key += randomId;
            }
        }

        ShoppingMallStringByteConverter _clsobj = new ShoppingMallStringByteConverter();
        retr = _clsobj.convertStringtoByteArray(key.substring(0, keyBytes));

        return retr;

//        return ShoppingMallStringByteConverter.convertStringtoByteArray(key.substring(0, keyBytes));
    }

    public String decrypt(String inputStr, String randomId, String symCipherAlgo) throws Exception {
        //inputStr= "gFpSl0e7p%2FhKz2Iz%2FJ1DtZoiy7uZeXU26jFuVIqfV0ezFEndJnmcO6o3oWXTjiD%2BOL3v6Gl3hcsljEx%2FN3X4mSlw1Oldg4seVSA%2Bl2wdFQGsXx93FtdBdGZz0W%2FYBDLd%0D%0AVhe5gbnxrEmvvquyEMk9nQEkW1aL8pjkUl98g4EUFGLSsppTZ7L%2BqHlriwVxxw2bCRqe%2FblVPSqe2xadPda13nhaAlkk5dJG7e0BTjp2A%2FjCAdmSSMSNpFfbhhNFzt4nHbB3%0D%0AGAhJe6%2BvRXdeetQb5ijiDxypPDEnP9BIQ%2BZtJHppRePSVbp8JAFiMqbuo%2FM4dK4lt9NSXgivNaMICIHqSdVy0ZMoWGUjr1DD7OS3X2hYWft1E0zDvodCxWVt%2B8KU8RFD2PUo%0D%0AqIb9%2FbC%2F6w1dW0Z8JApQKiVYSjXjZ%2BsFJzlN4XvuKf9eA%2FhSivtE35IYqYMBofJzhj2N370s3ORKAAsj4QG73fdpj9LsQnZNP3WmjTffkdpcO4p4QbXjB%2B9o";

        String DataValue = URLDecoder.decode(inputStr, "UTF-8");
        //DataValue=URLDecoder.decode( DataValue, "UTF-8" );

        ShoppingMallStringByteConverter _clsobj = new ShoppingMallStringByteConverter();

        ShoppingMallBase64EncoderDecoder _Encodecls = new ShoppingMallBase64EncoderDecoder();
        byte[] Message = _Encodecls.decode(DataValue);

        byte[] resultByt = null;

        byte[] randomByt = getKey(randomId, symCipherAlgo);
        SecretKeySpec skeySpec = new SecretKeySpec(randomByt, symCipherAlgo);

        HashMap<String, Cipher> cipherMap = getCipherMap();
        Cipher cipher = cipherMap.get(symCipherAlgo);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        resultByt = cipher.doFinal(Message);

        String dcryStrg = _clsobj.convertByteArrayToString(resultByt);

        return dcryStrg;
        //return ShoppingMallStringByteConverter.convertByteArrayToString(resultByt);
    }

    public String encrypt(String inputStr, String randomId, String symCipherAlgo) throws Exception {

        ShoppingMallStringByteConverter _clsobj = new ShoppingMallStringByteConverter();

        byte[] Message = _clsobj.convertStringtoByteArray(inputStr);

        byte[] resultByt = null;

        String B64EncryptedStr = null;

        byte[] randomByt = getKey(randomId, symCipherAlgo);

        SecretKeySpec skeySpec = new SecretKeySpec(randomByt, symCipherAlgo);

        HashMap<String, Cipher> cipherMap = getCipherMap();

        Cipher cipher = cipherMap.get(symCipherAlgo);

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        resultByt = cipher.doFinal(Message);

        ShoppingMallBase64EncoderDecoder _Encodecls = new ShoppingMallBase64EncoderDecoder();

        B64EncryptedStr = _Encodecls.encode(resultByt);
        System.out.println("DataValue in encrypt function:--->" + B64EncryptedStr);

        String DataValue = URLEncoder.encode(B64EncryptedStr, "UTF-8");
        DataValue = URLEncoder.encode(DataValue, "UTF-8");
        System.out.println("URLEncoder in encrypt value:--->" + DataValue);
        return (B64EncryptedStr);

    }

    public String decrypt(String inputStr, String symCipherAlgo) throws Exception {
        String DataValue = URLDecoder.decode(inputStr, "UTF-8");

        ShoppingMallBase64EncoderDecoder _Encodecls = new ShoppingMallBase64EncoderDecoder();

        byte[] Message = _Encodecls.decode(DataValue);

        // made the changes for the DES and DESede algorithm, the way of getting
        // default key,if key is not provided for decryption
        byte[] randomByt = getKey(mHexKey, symCipherAlgo);
        byte[] resultByt = null;

        SecretKeySpec skeySpec = new SecretKeySpec(randomByt, symCipherAlgo);
        HashMap<String, Cipher> cipherMap = getCipherMap();
        Cipher cipher = cipherMap.get(symCipherAlgo);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        resultByt = cipher.doFinal(Message);

        //return (new String(resultByt,FEBAConstants.CHARACTER_SET));
        ShoppingMallStringByteConverter _clsobj = new ShoppingMallStringByteConverter();
        String _dcrStrg = _clsobj.convertByteArrayToString(resultByt);
        return _dcrStrg;
        //return ShoppingMallStringByteConverter.convertByteArrayToString(resultByt);
    }

    public String encrypt(String inputStr, String symCipherAlgo) throws Exception {

        ShoppingMallStringByteConverter _clsobj = new ShoppingMallStringByteConverter();
        byte[] Message = _clsobj.convertStringtoByteArray(inputStr);
        //made the changes for the DES and DESede algorithm, the way of getting
        //default key,if key is not provided for encryption
        byte[] randomByt = getKey(mHexKey, symCipherAlgo);
        byte[] resultByt = null;
        String B64EncryptedStr = null;
        SecretKeySpec skeySpec = new SecretKeySpec(randomByt, symCipherAlgo);
        HashMap<String, Cipher> cipherMap = getCipherMap();
        Cipher cipher = cipherMap.get(symCipherAlgo);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        resultByt = cipher.doFinal(Message);
        ShoppingMallBase64EncoderDecoder _Encodecls = new ShoppingMallBase64EncoderDecoder();
        B64EncryptedStr = _Encodecls.encode(resultByt);
        return (B64EncryptedStr);
    }

    private class CipherInstanceThreadLocal extends ThreadLocal<HashMap<String, Cipher>> {

        protected HashMap<String, Cipher> initialValue() {
            HashMap<String, Cipher> cipherHash = new HashMap<String, Cipher>();
            try {
                cipherHash.put(Algo_DES, Cipher.getInstance(Algo_DES));
                cipherHash.put(Algo_AES, Cipher.getInstance(Algo_AES));
                cipherHash.put(Algo_DESede, Cipher.getInstance(Algo_DES));
            } catch (NoSuchAlgorithmException e) {
            } catch (NoSuchPaddingException e) {
            }
            return cipherHash;
        }
    }

    private HashMap<String, Cipher> getCipherMap() {
        return cipherThreadLocal.get();
    }

    public String SHA256CHKSUM(String message) throws Exception {
        try {
            byte[] hashseq = message.getBytes();
            StringBuffer hexString = new StringBuffer();
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");  
                md.reset();
                md.update(hashseq);
                byte messageDigest[] = md.digest();
                for (int i = 0; i < messageDigest.length; i++) {
                    String hex = Integer.toHexString(0xFF & messageDigest[i]);
                    if (hex.length() == 1) {
                        hexString.append("0");
                    }
                    hexString.append(hex);
                }
            } catch (Exception e) {
                return null;
            }

            return hexString.toString();

        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public String char2hex(byte x) {
        char arr[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char c[] = {arr[(x & 0xF0) >> 4], arr[x & 0x0F]};

        return (new String(c));
    }

    public static void main(String[] args) {
        try {

            String Key = "29304E87583278923487A64389764380763087426708234658764320F8764387645678023467843678657678243678934208346543763426342087364764378623487165321780324643278643287634807347867463247864326D43204376743286743290874784C3260409A876";
            String QS = "";
            String encryptedVal = null;
            String DataValue = "MD=P|AcctNumber=566802070000600|PID=0000002351|NAR=CC Avenue|PRN=CCAV0125879542|AMT=2.00|CRN=INR|RU=https://ccavenue/pgidsk/servlet/PGIBankResponseHandler?|CustName=Nilesh Patel|FrstColltnDt=2012-05-17|FnlColltnDt=2015-05-17|ColltnAmt=120.00|MaxAmt=120.00|FreeField1=NA|FreeField2=NA|FreeField3=NA |ChecksumKey=Un!ON@2019";

            ShoppingMallSymmetricCipherHelper _obj1 = new ShoppingMallSymmetricCipherHelper();

            String GenCheckSumVal = _obj1.SHA256CHKSUM(DataValue);

            System.out.println("Check Sum Hash Value : " + GenCheckSumVal);

            // Check Sum Hash Value : 566011955926cfa201d698455e21857da09e3990ce689e1d96dc11f8ddca1bc5
            // encryptedVal=_obj1.encrypt(DataValue, Key, "AES");
            System.out.println("Encrypted String  :  " + encryptedVal);

//                Encrypted String  :  gFpSl0e7p/hKz2Iz/J1Dtcs4VcDSD7fqEM6+Uv/OspS2JovzkWLCaT1dszi/N/q6mPqkbDUZgIK0
//RWrmG+rUyu/9doMEuK/hL6MQGg0sArH/21VX5I/dg3Oz8d2I40eDWAB01hG7bE9DhQoTHJrcNxxY2QPU
//ucFQ+7Boq2IhjXXFwh25M86k3GpfbD0j5Akn0wwLC1lBq6iwDN8XP7BqeBjSLpWfvjK8Wa9+Hef6Nw8Q
//Rsuqp7a2gx8BqmLkBvJYtGeacymC7PV/drhHfTqwvP6P7F78SPdG/wSQ5clzrJyo2d5IrfmXSCXbQeXj
//ynwP34C2+ZdQ4YGVmcqMya7VwnvhMno+vo3S6J6GuiYFL/a9yze4/j1MTtoRJ+//Aw6ulzfH4MVOw86M
//EnY33Lm+BH0jD45WUkBLDBUqpGJZutOPXHdLy6xNwOV0Y4s8cMG1
            encryptedVal = "qPen%2F90VkodqQ04eYV4Wbi%2BnPNSVn0uaSzZ2k9ZFNR%2BUpgedk1Jrw9BAdp6OvrbC1N5O2C3NZQmQby9RkWYeUy46KOetQcpvuFDb6eI445CxiUMyl9wJqmiOttt3ROVl%0D%0A6GMyYdFVzWAhLMOP7TyQE8ZM3kGUlDBo1iWU9DIqkw3tsXjFVP%2BVkCM48zuJPwpoCjUfhLB4%2BB4t2KhUXh6huuPkrcxk2lXF8SQ3LLCdbYNqbec%2BoQWcva2dGM%2FnIUskh1hy%0D%0AHpI6NOmYM71h5kygfT%2FdQtpdLK%2FmLKYEJ8f8HDFiQN6qUrMS%2BP63YU1biOjAisfPW7E6NO%2FRS70SJ15%2Fj8CUPUDJZmlravTdj%2BhHa7nGx8l%2FUTPfy5DtHo5kHYxGl5zyyycy%0D%0AGvo%2BKAzJa%2BqzH7COIo0qjGjyWYOKU3b1fNMHAUZFIP5EJnmWuI4mSO0y";
            DataValue = URLEncoder.encode(encryptedVal, "UTF-8");

            String value = "ByP0PnZZISAS8ZsTBh6cZa4gF5aJhCj/Ldi9dHqSYsMf7ZEbaQ19SYtVgjlC+PiLS/tzGg05j6Lv+Vf2Zkky+dAk3HwdVaoII8yfdW0kWT8USuex4lY6D6GK3cNVX+nQlLAUFHHXDlnIeNoTBxmum/qMN+ZOV+3Q33Oph7lDr+26JSYiS6VNIEDfvT9xji9tUy+0ujL4E2eRCFlNJowyK3Q1KDCUfzBmsagHW3+LK3ko4TwZuSum9K0DIrSjdV5HKieShqsG03digetki6NI9DfIrROZ84asqKAYXWyg6q9D4l+6WjD+WTCrdCHvW8Qg0Fyo3xI5/kBb8j72xZ421nqFwQ==";
            // 9d9e1fc5999a4b6ea72b9bd2aacff093  

            System.out.println("EnCoded String  :  " + DataValue);

            System.out.println();

            DataValue = URLEncoder.encode(DataValue, "UTF-8");

            System.out.println("EnCoded String  :  " + DataValue);

            //EnCoded String  :  gFpSl0e7p%2FhKz2Iz%2FJ1Dtcs4VcDSD7fqEM6%2BUv%2FOspS2JovzkWLCaT1dszi%2FN%2Fq6mPqkbDUZgIK0%0D%0ARWrmG%2BrUyu%2F9doMEuK%2FhL6MQGg0sArH%2F21VX5I%2Fdg3Oz8d2I40eDWAB01hG7bE9DhQoTHJrcNxxY2QPU%0D%0AucFQ%2B7Boq2IhjXXFwh25M86k3GpfbD0j5Akn0wwLC1lBq6iwDN8XP7BqeBjSLpWfvjK8Wa9%2BHef6Nw8Q%0D%0ARsuqp7a2gx8BqmLkBvJYtGeacymC7PV%2FdrhHfTqwvP6P7F78SPdG%2FwSQ5clzrJyo2d5IrfmXSCXbQeXj%0D%0AynwP34C2%2BZdQ4YGVmcqMya7VwnvhMno%2Bvo3S6J6GuiYFL%2Fa9yze4%2Fj1MTtoRJ%2B%2F%2FAw6ulzfH4MVOw86M%0D%0AEnY33Lm%2BBH0jD45WUkBLDBUqpGJZutOPXHdLy6xNwOV0Y4s8cMG1
            ShoppingMallSymmetricCipherHelper _obj = new ShoppingMallSymmetricCipherHelper();
            //DataValue="gFpSl0e7p%252FhKz2Iz%252FJ1Dtcs4VcDSD7fqEM6%252BUv%252FOspS2JovzkWLCaT1dszi%252FN%252Fq6mPqkbDUZgIK0%250D%250ARWrmG%252BrUyu%252F9doMEuK%252FhL6MQGg0sArH%252F21VX5I%252Fdg3Oz8d2I40eDWAB01hG7bE9DhQoTHJrcNxxY2QPU%250D%250AucFQ%252B7Boq2IhjXXFwh25M86k3GpfbD0j5Akn0wwLC1lBq6iwDN8XP7BqeBjSLpWfvjK8Wa9%252BHef6Nw8Q%250D%250ARsuqp7a2gx8BqmLkBvJYtGeacymC7PV%252FdrhHfTqwvP6P7F78SPdG%252FwSQ5clzrJyo2d5IrfmXSCXbQeXj%250D%250AynwP34C2%252BZdQ4YGVmcqMya7VwnvhMno%252Bvo3S6J6GuiYFL%252Fa9yze4%252Fj1MTtoRJ%252B%252F%252FAw6uoV3dB0Y9mFsP%250D%250ApsHgVE9Vwy0aO5xKt0Fw%252FU%252Bv8%252BQi1jiXWmKQLLwTEYWal7baocPkXCtdSUkada0LQpRd1nsUG7zUWepN%250D%250ABUh9lcXTVyT88nZuEuJedHdNfymqYM0fr%252Bog";
            //"gFpSl0e7p%252FhKz2Iz%252FJ1DtQ0wLsTa9Kk0fYbTzRVhGeXlyR1nOzbjbEHz8MJsVvvQqM5RZU8OZPnU%250D%250AraoTaEjB1YGKf8RnXx6NrMq5fghpwNiY7ixyHkkRAgO9KfthvP7F7OeUeSmhhvwDvt7DpEHsf0bY1Fn1%250D%250AYuKKlHe8UEVve38cxZrHY3WIjabDE%252FcjSoSGqVsnEItIxmnZJsYZayffH1%252BxJiAK31VslsF0uR049bTG%250D%250AWo4u1r0M1V%252Fnlrlg4%252BJ757%252BhO5ACixKzgTWp7vnkq2xLJL%252F2H%252FBR98eCLrXqWUxR%252FkvMp%252B5PwNnELHCZ%250D%250AYTLyITTTdDPaAugkNCFoYs2p%252FgmKJBmvNsoFMveFsLh8Lri%252FF1u4IlE4pUcxb2cFk2mncovM7kkwTCk0%250D%250AxvYzgAvZFhEAZMqyPejtNhKCaYOGbPX22HKgXvJHvu3yNz3DqN%252BRuCUH3F2JGmixqjcbX3yziUN8T0St%250D%250ADqe2mAdUaNSibRLxI4FFUSkdaGKBCDjL8fmnd%252B6Ft5vRE19sdpE9mnyaunkDhDKCSgAXEd9GW05qeNod%250D%250ABv1w7DJDgcIZamP1mJj4vvJ75XXTOteNRNjSJPKefw%253D%253D";
            //
            //"gFpSl0e7p%25252FhKz2Iz%25252FJ1DtdawX0rZROkfAjMej%25252FRZgU6zFEndJnmcO6o3oWXTjiD%25252BEPzupWanjbaB%25250D%25250AoRrjiXFMJ1leHoRu9BdEWvZJBzFdWzpSEq6dORS%25252ByaQ%25252BYpsl96Wog0aLEUF0SzzRBhSm%25252Bg57oOuOEOMt%25250D%25250AmJmo7xYU45SVsltngfIvK39FAJyYDRE3BAtExKGhhAtPWV1rA4gKuxTCKnn82%25252BXUAfat7RfFXf%25252B%25252Bjy2J%25250D%25250AUZOk2Lcuo%25252BqXkCybhixt%25252B5%25252BYrby%25252FHL7xZfkQJnh819VVFIJPmr78PLoOz8GS1rEXZViY2B0LPADJJI8z%25250D%25250AxJiK14Flw1lIVwNCrr5F7%25252BjxaoC%25252FymcOKk02ayU8P6hDPWNhW1PcweeWPkY3VOP0tphENejq6Ej3liJT%25250D%25250AFSDWJZyH473iVajUdhAuprBZForcqPkxWO49Tbub%25252FsMnAVYwXztXeImWF5ECToPzwal5lMtorIl4sssU%25250D%25250AP10uyc0R7KI17HIx6n%25252Fhe5qtizAqGbisO%25252FUM8yd1NjJFnlekKsY%25252BDUxsAub4mQ9gEppd7%25252FJDyTYvyj%25252Fs%25250D%25250Ay7XB%25252BGmVr7rpcahFHiEG0hA9qEYNCGHRVWppUXKG8g%25253D%25253D";
            //"gFpSl0e7p%252FhKz2Iz%252FJ1Dtcs4VcDSD7fqEM6%252BUv%252FOspS2JovzkWLCaT1dszi%252FN%252Fq6mPqkbDUZgIK0%250D%250ARWrmG%252BrUyu%252F9doMEuK%252FhL6MQGg0sArH%252F21VX5I%252Fdg3Oz8d2I40eDWAB01hG7bE9DhQoTHJrcNxxY2QPU%250D%250AucFQ%252B7Boq2IhjXXFwh25M86k3GpfbD0j5Akn0wwLC1lBq6iwDN8XP7BqeBjSLpWfvjK8Wa9%252BHef6Nw8Q%250D%250ARsuqp7a2gx8BqmLkBvJYtGeacymC7PV%252FdrhHfTqwvP6P7F78SPdG%252FwSQ5clzrJyo2d5IrfmXSCXbQeXj%250D%250AynwP34C2%252BZdQ4YGVmcqMya7VwnvhMno%252Bvo3S6J6GuiYFL%252Fa9yze4%252Fj1MTtoRJ%252B%252F%252FAw6ulzfH4MVOw86M%250D%250AEnY33Lm%252BBH0jD45WUkBLDBUqpGJZutOPXHdLy6xNwOV0Y4s8cMG1";
            //"gFpSl0e7p%2FhKz2Iz%2FJ1Dtcs4VcDSD7fqEM6%2BUv%2FOspS2JovzkWLCaT1dszi%2FN%2Fq6mPqkbDUZgIK0%0D%0ARWrmG%2BrUyu%2F9doMEuK%2FhL6MQGg0sArH%2F21VX5I%2Fdg3Oz8d2I40eDWAB01hG7bE9DhQoTHJrcNxxY2QPU%0D%0AucFQ%2B7Boq2IhjXXFwh25M86k3GpfbD0j5Akn0wwLC1lBq6iwDN8XP7BqeBjSLpWfvjK8Wa9%2BHef6Nw8Q%0D%0ARsuqp7a2gx8BqmLkBvJYtGeacymC7PV%2FdrhHfTqwvP6P7F78SPdG%2FwSQ5clzrJyo2d5IrfmXSCXbQeXj%0D%0AynwP34C2%2BZdQ4YGVmcqMya7VwnvhMno%2Bvo3S6J6GuiYFL%2Fa9yze4%2Fj1MTtoRJ%2B%2F%2FAw6ulzfH4MVOw86M%0D%0AEnY33Lm%2BBH0jD45WUkBLDBUqpGJZutOPXHdLy6xNwOV0Y4s8cMG1";
            encryptedVal = _obj.decrypt("JL9yURBWrL7FJPn4ut1dQh179sBxDoAt0d1QlI1zeHr2lGZ3m6vfLsTQkGSX3vIYBYO7O07MtW1naH7prXIhZM/8XlWdaJjcCRKY/m0I2u+d2W6eAR0nP9ioxpGXCNKNb7KlYRhe4GiSYzpkohl9h90XtGCUNwd7zmGkGVXDoc4=", "jrD@Mt6i#0n@chp$b", "AES"); // Here Encrypting

            System.out.println("Decrypt String --->" + encryptedVal);

            //Decrypt String --->MD=P|AcctNumber=566802070000600|PID=0000002351|NAR=CC Avenue|PRN=CCAV0125879542|AMT=2.00|CRN=INR|RU=https://ccavenue/pgidsk/servlet/PGIBankResponseHandler?|CustName=Nilesh Patel|FrstColltnDt=2012-05-17|FnlColltnDt=2015-05-17|ColltnAmt=120.00|MaxAmt=120.00|FreeField1=NA|FreeField2=NA|FreeField3=NA |ChecksumKey=Un!ON@2019
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
        }
    }

}
