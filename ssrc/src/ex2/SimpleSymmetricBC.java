package ex2;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Exemplo simples de cifra simetrica
 */
public class SimpleSymmetricBC {

	private static byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04,
			0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
			0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };

	private static final SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

	private static Cipher cipher;

	static {
		try {
			cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// byte[] input = new byte[] { 0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66,
		// 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xaa, (byte) 0xbb,
		// (byte) 0xcc, (byte) 0xdd, (byte) 0xee, (byte) 0xff };
		// byte[] keyBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05,
		// 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
		// 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };

		// byte[] input = "1234567890123456".getBytes();

		byte[] input = new byte[32];
		byte[] txt = "ola".getBytes();
		for (int i = 0; i < txt.length; i++)
			input[i] = txt[i];

		// Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");

		System.out.println("input text : " + Utils.toHex(input));

		// encryption pass

		byte[] cipherText = new byte[input.length];

		cipher.init(Cipher.ENCRYPT_MODE, key);

		int ctLength = cipher.update(input, 0, input.length, cipherText, 0);

		ctLength += cipher.doFinal(cipherText, ctLength);

		System.out.println("cipher text: " + Utils.toHex(cipherText)
				+ " bytes: " + ctLength);

		// decryption pass

		byte[] plainText = new byte[ctLength];

		cipher.init(Cipher.DECRYPT_MODE, key);

		int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);

		ptLength += cipher.doFinal(plainText, ptLength);

		System.out.println("plain text : " + Utils.toHex(plainText)
				+ " bytes: " + ptLength);
	}

	public static byte[] cipherBC(byte[] input, int len) {
		byte[] cipherText = new byte[len];
		int ctLength = 0;

		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			ctLength = cipher.update(input, 0, len, cipherText, 0);
			ctLength += cipher.doFinal(cipherText, ctLength);
		} catch (InvalidKeyException | ShortBufferException
				| IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cipherText;
	}

	public static byte[] deCipherBC(byte[] input, int len) {
		byte[] plainText = new byte[len];
		int ptLength = 0;

		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			ptLength = cipher.update(input, 0, len, plainText, 0);
			ptLength += cipher.doFinal(plainText, ptLength);
		} catch (InvalidKeyException | ShortBufferException
				| IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return plainText;
	}

	public static byte[] cipherBC(byte[] input) {
		return SimpleSymmetricBC.cipherBC(input, input.length);
	}

	public static byte[] deCipherBC(byte[] input) {
		return SimpleSymmetricBC.deCipherBC(input, input.length);
	}
}
