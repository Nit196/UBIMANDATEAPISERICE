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



import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class ShoppingMallStringByteConverter {
    
     // Local Map to hold the Character set reference.
	private static final ConcurrentMap<String, Charset> charsetsByAlias = new ConcurrentHashMap<String, Charset>();
	private static final String CHARACTER_SET = System.getProperty("CHARACTER_SET", "UTF-8");	
	private static final String DEFAULT_CHARSET = Charset.defaultCharset().displayName();

	// Initializing Local Map with the default Character set and UTF-8.
        
        public void ShoppingMallStringByteConverter()
         {
		charsetsByAlias.put(CHARACTER_SET, Charset.forName(CHARACTER_SET));
		charsetsByAlias.put(DEFAULT_CHARSET, Charset.defaultCharset());
	}
    
    
    
    
        public  byte[] convertStringtoByteArray(String sInputString,
			String charsetName) throws UnsupportedEncodingException {

		Charset cs = lookup(charsetName);

		// Convert the String to a ByteBuffer.
		ByteBuffer b = cs.encode(sInputString);

		// Convert the ByteBuffer to a byte[].
		byte[] bArray = b.array();

		// Get the size of the Byte Array w/o the empty space in the end.
		int size = b.limit();

		byte[] bFinal = new byte[size];

		// Removing the last entry in the Array. As the result array always
		// returns a blank in the end.
		System.arraycopy(bArray, 0, bFinal, 0, size);

		return bFinal;
	}

	public  byte[] convertStringtoByteArray(String sInputString)
	throws UnsupportedEncodingException {

		// Return byte[] by encoding with the default CHARACTER_SET.
		return convertStringtoByteArray(sInputString, DEFAULT_CHARSET);
	}

	public  String convertByteArrayToString(byte[] bArray, int off,
			int len, String charsetName) throws UnsupportedEncodingException {

		Charset cs = lookup(charsetName);

		// Convert a byte[] to a String.
		ByteBuffer bb = ByteBuffer.wrap(bArray, off, len);

		return cs.decode(bb).toString();
	}

	public  String convertByteArrayToString(byte[] bArray,
			String charsetName) throws UnsupportedEncodingException {

		Charset cs = lookup(charsetName);

		// Convert a byte[] to a String.
		return cs.decode(ByteBuffer.wrap(bArray)).toString();
	}

	public  String convertByteArrayToString(byte[] bArray, int off,
			int len) throws UnsupportedEncodingException {
		// Return String by encoding with the default CHARACTER_SET.
		return convertByteArrayToString(bArray, off, len, DEFAULT_CHARSET);
	}

	public  String convertByteArrayToString(byte[] bArray)
	throws UnsupportedEncodingException {

		// Return String by encoding with the default CHARACTER_SET.
		return convertByteArrayToString(bArray, DEFAULT_CHARSET);
	}

	private  Charset lookup(String alias)
	throws UnsupportedCharsetException {
		Charset cs = charsetsByAlias.get(alias);
		if (cs == null) {
			cs = Charset.forName(alias);
			charsetsByAlias.putIfAbsent(alias, cs);
		}
		return cs;
	}

	public  byte[] convertBlobToByteArray (Blob blob, long pos, int length) throws SQLException {

		return   blob.getBytes(pos, length);

	}
    
    
    
    
}
