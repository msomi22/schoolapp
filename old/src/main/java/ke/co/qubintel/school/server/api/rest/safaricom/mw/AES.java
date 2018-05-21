package ke.co.qubintel.school.server.api.rest.safaricom.mw;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 *  @author <a href="mailto:mwendapeter72@gmail.com">Peter mwenda</a>
 *
 */
public class AES {

	/**
	 * 
	 * @param sSrc
	 * @param mykey
	 * @return
	 * @throws Exception
	 */
	public static byte[] ecbEncrypt(byte data[], byte[] mykey) throws Exception {
		if (mykey == null) {
			System.out.print("key null");
			return null;
		}
		if (mykey.length % 16 != 0) {
			System.out.print("key must be equal to 16");
			return null;
		}
		SecretKeySpec skeySpec = new SecretKeySpec(mykey, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(data);
		return encrypted;
	}

	/**
	 * 
	 * @param sSrc
	 * @param mykey
	 * @return
	 * @throws Exception
	 */
	public static byte[] ecbDecrypt(byte data[], byte[] mykey) throws Exception {
		try {
			if (mykey == null) {
				System.out.print("Key null");
				return null;
			}

			if (mykey.length % 16 != 0) {
				System.out.print("key must be equal to 16");
				return null;
			}

			SecretKeySpec skeySpec = new SecretKeySpec(mykey, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			try {
				return cipher.doFinal(data);
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}


}