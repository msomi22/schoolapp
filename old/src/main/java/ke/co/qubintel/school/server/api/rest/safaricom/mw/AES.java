package ke.co.qubintel.school.server.api.rest.safaricom.mw;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 
 * @author peter
 *
 */
public class AES {

	private static final String ALGO = "AES";
	
	/**
	 * 
	 * @param data
	 * @param mykey
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] mykey) throws Exception {
		Key key = generateKey(mykey); 
		Cipher c = Cipher.getInstance("AES/CBC/NoPadding");
		c.init(Cipher.ENCRYPT_MODE, key);
		return c.doFinal(data);
	}
	

	/**
	 * 
	 * @param encryptedData
	 * @param mykey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptedData, byte[] mykey) throws Exception {
		Key key = generateKey(mykey);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		return new String(decValue);
	}


	/**
	 * 
	 * @param mykey
	 * @return
	 * @throws Exception
	 */
	private static Key generateKey(byte[] mykey) throws Exception {
		return new SecretKeySpec(mykey, ALGO);
	}


	
}