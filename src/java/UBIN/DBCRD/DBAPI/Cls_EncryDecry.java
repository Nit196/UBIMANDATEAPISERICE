/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UBIN.DBCRD.DBAPI;

/**
 *
 * @author puneet.jain
 */

import com.infrasoft.kiya.security.EncryptionDecryptionImpl;

public class Cls_EncryDecry {
    
    
    
    public String  encrymessage (String txtToEncrypt, String passphrase)
    {
        try{
             EncryptionDecryptionImpl obj=new EncryptionDecryptionImpl();
            String encryText="";
            passphrase="8a3a7e7ef1de428099f2ea25b005bc3d";//"9d9e1fc5999a4b6ea72b9bd2aacff093";
                encryText=obj.encryptMessage(txtToEncrypt, passphrase);
                System.out.println(encryText);
               return encryText;
        
        }
        catch(Exception er)
        {
            System.out.println("DebitCard Encryption API Exception : " + er.getMessage());
            return ("DebitCard Encryption API Exception : " + er.getMessage());
        }
    }
    
    public String  dcrymessage (String str, String myKey)
    {
        try{
             EncryptionDecryptionImpl obj=new EncryptionDecryptionImpl();
            String encryText="";
            myKey="8a3a7e7ef1de428099f2ea25b005bc3d";//9d9e1fc5999a4b6ea72b9bd2aacff093";
                encryText=obj.decryptMessage(str, myKey);
                System.out.println(encryText);
               return encryText;
        
        }
        catch(Exception er)
        {
            System.out.println("DebitCard Decryption API Exception : " + er.getMessage());
            return ("DebitCard Decryption API Exception : " + er.getMessage());
        }
    }
    
    public static void main(String[] args)
    {
        Cls_EncryDecry _clsobj =new Cls_EncryDecry();
        try
        {
            
            String rt=_clsobj.encrymessage("6425","");//
            String iu="55b72f61a56bac21ba59a6e86b9caa8c 5c4c806bf6fd22603cecee15686162a7 +slOZVr75spSFeqmTmTa8bwILKXeCJAv6R2JGVhRCPvCnipmzMo1MiUrPIzL3pFYpEPJn8vHT2zOeGTUgitvTDIHWNRimTKNuLOGCoeYyA0=";
//"fd4605af2d8276ca4a0d6ab8f9f8c747 920b8d6bb10bbf5131949729fd9ed1a3 8U4/exSwgQGgZId8oCAqaw==";
                    //"K42pxRmH+OFvqtdBxKUEsZyf/S5upbrHjKHSzehadr8HjPCEOWl2pJoqGRoogh4wPa/G+3bLwGBEYZs6txErUjtmd5RCHKCHM05ae9PeT+DFXXs/Jt+OMzmToqdLlDHGY67/ZCmEocY8vgmTew/KQKrRM7dvkYcNOOGwhgP41d6SOOMqsFkwGVEVKNeEXFqyE9L6YkspJl8PREUW5Vqve6b7cZ0sOqWoQt9HlhB5r3+Ty3W7KONkpTc+iNpZs6cul7NfzP8KFMVRd5SXx0ijrecJ8Cwbazz5qOzkaqhqizgTi7DwV7maWXim8Osm7wuU4xCdfEd9PTdlTMpfMpeN1g==";
                    //"fd4605af2d8276ca4a0d6ab8f9f8c747 920b8d6bb10bbf5131949729fd9ed1a3 8U4/exSwgQGgZId8oCAqaw==";
            //"fab3adbf66874026e9d93663dd7bd4835aca00de694f605cf6dad1aa812df4a4SBBpM4mTjB/tZT/Yn2fzAAqubyw9re5jZijZrZmrI01LIGxBnDE0/FPS/3u10XwhaghhE6Zy93LxwlmbPr93BvjHONhctY5M5tCkfq2cM14="
             String rt2=_clsobj.dcrymessage(iu,"");//
            //"bE86HoKVy+Hp/tjV8kH7AlyjI7D5i9dh24wxdqnxle5fGlG8MyM8QpYwRTspxlNYdN2C6Y2SQkSMTXfTbndqQ8TPItIuY+q6TZXLvwy3tG1ubOG55Gj5NierzZZOHN0gcoO+H2kY0XqmM44jFvieJZgLyvv81SKPGKNPLAhW9WpVN+3iIsi8NhVBXdtKVEYUsMhSfRQmLdshtTcElVlbWSN53y2ANeGtnVKdHP03ZE7k5jC3EBkkrrWqL0+tPkSjISirQKNzUGRxYT0v4/IJzj0P/H5l2/NK9r/9QH+IKiA6Dm1I1Cgemy+2nPzyLF0eu6Cw/nyqkeeA1g77AASgJA==","");
            rt2=_clsobj.dcrymessage(rt2,"");//
            String enr=_clsobj.encrymessage("{\"data\":{\"NPCIRefID\":\"7a6a55a64c1c4b599820672a404cc2af\",\"transactionID\":\"UBIN7021908210002003\",\"PRN\":\"Exception:Decryption error22032022184835612\",\"AccountNo\":\"Exception:Decryption error\",\"CustomerName\":\"JAIN\",\"CardNo\":\"UseniwO/eSm+qybm5j+ifUhRMp2ORkUorZirnO8qnQ208vNV1PClX5Bi1+uBWvSAJgDBBWYalzova8ylp9DOdWjAgY/q8+qoGxb4lYnpDS3xFTii93UDvdqgjZP63uDKZMl43RNG+6E1TsMhEwovCkFdY77xCALbXRAiBPRdTNKdmJvLXB79EOJJsr6cuoVPKhmmt5TsqpjXM1TrXhatWGcRsu+9OmPzA9nGTMrUfiWJnI+7HmE4254J5qB1uVGZ5jj85CbLO3FoBuWXmEFjWc6TZm9+6D8hfeVu5Dbz2/pWmY0clbOf+Vlzs+2H24TqRp53Enm1ijvCN6l9S61Wyg==\",\"CardExpDate\":\"F4bMZMoU8U1wZa3z+lDCzuxGDl92mTjdyQZyxMDH6g6EGeUh5v1Au7KVXMgcnuWw/YaKGeO062HMdxIRKrVo9dulgea17yRmJQQZyEZjqdlw3L8xKjlUJzXh6/5SrdeD9Hvj32zm3WFKXWDycLFVcKqIkbbhYf2QYQ3dcAdUo5ftARo4MmrVfBGepGGwN/IL4WA++qPEA0SW7+nQwKyzOIe8qwc4C6tFG8YKSmKwJIY6SiepjrnbvkWBlRytnhA1HXrH2BLwTrm+8afg93n3ovxwbDnjr3GBzE0V4U4vQtTFoSONUZXdGTIAS4lWb8JXVOEm9WFgWGDseKFD020K8A==\",\"CardCVV\":\"FIkxZkXkjNGkpZ10lBAqdqe5ZcbJJXTgMMfC0fEqF5JAvV9lrJUgMlvJ31o6RHbmT+VDNtUSUYcCLbPWFXPePGWD4r4y/zDUPtKZx/lec/pxA3TGHccOC3l6tnHbw2rWlakv92GFonahhkY1heqqBK5Q3ey/mlhgZXq/RhmCGeCPjSxyomGoLX6+ozyhLYuRzaUiahqcZhJkqElT7swMWvwt0UYC2jQBdWm1CpVgZLVZ3aPBOegdq3dH895xRoPx1OjOLMR99FW6qqCa2XFyaE+eJODUncIBzrtLMdiCrGa4QQ7AFjgjvlssLGJjSgsSkW3Lh0B0x/JpgClP448YqA==\",\"FreeField1\":\"UBIN7021908210002003\",\"FreeField2\":\"NA\",\"Timestamp\":\"2022-03-16T17:58:13\",\"ProcessingCode\":\"NA\",\"PointOfServiceEntryMode\":\"NA\",\"PointOfServiceConditionCode\":\"NA\",\"AcquiringInstitutionIndentCode\":\"NA\",\"CardAcceptorTerminalIdentification\":\"NA\",\"CardAcceptorNameOrLocation\":\"NA\"}}","");
            
            String tmpEcy=_clsobj.encrymessage("{\"otpInfo\":{\"transactionID\":\"UBIN7022709210002015\",\"otp\":\"604842\"}}","");
            
            String res=_clsobj.dcrymessage("E4p91pkStq5+aNaKixVS3RXxte+pu2y/BmwLAJKiUh2tzh5b0rLoDAY/gPbXw+rKKONq84My7hgAaUdu8ygjZPeyxI3xE1cBd1bls8kTaGarQH8aheLblZ6xohRIm73JbuOezBCBQxwDS3cruM6vkO+lQgreeDpYZZKdgcUoZWhJqf+uSVeWdxW9F9Q5R4ZheTjVcqSAFpuKTH19JWxFhH3VKbkFeRH0dgGU1vOGZwxoc/s8ZU65OylJLfusuM97zqtmLftXzPm1qyjg0Mqh1IocYAmHrUFTLKTW58+tO09DDjODMHGRi/iCflPRxPRZlHEvPzrspk/K3Ziv+3UVkQ==","");
            
            System.out.println("Decrypt Text1 : "+ res);   
            
            String res1=_clsobj.dcrymessage(tmpEcy,"");
            //bbd56487d3412a166c03e46a55ab65a4 4461ff1a611fe1372ef3677546a58eca KionskS8fhSc0D99jsMOvj2pmbOghT4+Z3MWUc/7MQig40W5s9/kr6FqFx8V+nb8foLnJFMMrWtoc2qcufcCY7gBlAZ046K/H+KKRUe952XCqX5yUucr29hh4ztf0LRwXXvMVs0Rd2RYpqzTxKUmv25E+uaNn74u8trE9/DU5KxyOGgKz3ayh9Z1GLCVzrOMc/hNr1143We3aDvYWSiOx95JoKvJuyrkYxXjilPT3EQia+NkBy9yKk9O8i84LBkynkIeuLp9MV/32iCXDVXS7yMD4K5AGL+tC7YjxfsNvg4uUYIv3jE9uRkpTmnJIVGyJaA9acfDGQxi7OR7bGyMyGcgwsqXwOWflpaLLuJsAVnA8fdvM5nzUi8LgmX8wqBsG1ApYC1DVGpkQRoB+X30wMDJh8xqYB7T+wY2iB2aFvFuaW/6a4OFzWjPbMnHAPUoVFFITCM/XIKoo3yqOg1+QvmvY2mMKRAkppS2ForJy4CM3m35FIVfJGoHElJq5ZIij01m2394H2nhSMG8kI44V3r8GpBez/+a88sGcIahrg9B5izt8Elub2H6pkAjLfOElsUn5dBvocrsJZITTGAs5rUJDHYpi8VchtRoXJWt2Ygw5ONsDJLrOzlaRwmDnQ/T", "9d9e1fc5999a4b6ea72b9bd2aacff093");
            System.out.println("Decrypt Text2 : "+ res1);   
        }
        catch(Exception err)
        {
            System.out.println("Exception : "+ err.getMessage());
        }
    }
    
}
