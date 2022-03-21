package com.p.secure;

import java.security.MessageDigest;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Secure {

	public static void main(String[] args) throws Exception {

		// Secure testEncryption = new Secure();

		// String Key = "Indi@$$bu11s";

		// String decrypted = Secure.AESDecrypt("4iIDrUSQ8PFwK0n/IUNmuA==", "");
		// System.out.println("decrypted : " + decrypted);

	}

	// SHA256 starts here
	@SuppressWarnings("unused")
	private static synchronized String getSha256(String value) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes());
			return bytesToHex(md.digest());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes)
			result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}

	public static synchronized String AESDecrypt(String textToDecrypt, String key)
			throws Exception, BadPaddingException {
//		byte[] decryptedPlainText = null;
		String decryptedString = "";
		key = "Indi@$$bu11s";
		try {

			textToDecrypt = textToDecrypt.replaceAll(" ", "+");

			String DECRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
			SecretKeySpec keySpec;
			byte[] keyBytes = GetKeyAsBytes(key);

			keySpec = new SecretKeySpec(keyBytes, "AES");

			Cipher cipher = null;

			cipher = Cipher.getInstance(DECRYPTION_ALGORITHM);

			// IV = 0 is yet another issue, we'll ignore it here
			IvParameterSpec iv = new IvParameterSpec(keyBytes);

			cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

			decryptedString = new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(textToDecrypt)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedString;
	}

	private static byte[] GetKeyAsBytes(String key) {
		byte[] keyBytes = new byte[0x10];

		for (int i = 0; i < key.length() && i < keyBytes.length; i++) {

			keyBytes[i] = (byte) key.charAt(i);

		}

		return keyBytes;
	}

	// public static String CreateMD5Hash(String input) {
	// // Use input string to calculate MD5 hash
	// byte[] inputBytes = null;
	// byte[] hashBytes = null;
	// String hash = null;
	// try {
	// inputBytes = input.getBytes("ASCII");
	//
	// MessageDigest md = null;
	//
	// md = MessageDigest.getInstance("MD5");
	//
	// hashBytes = md.digest(inputBytes);
	// hash = new BigInteger(1, hashBytes).toString(16);
	// hash = hash.toUpperCase();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return hash;
	// }

	// public static byte[] zeroPad(int length, byte[] bytes) {
	// byte[] padded = new byte[length]; // initialized to zero by JVM
	// System.arraycopy(bytes, 0, padded, 0, bytes.length);
	// return padded;
	// }

	// public static byte[] returnbyte() throws IOException {
	//
	// FileInputStream fileinputstream = new FileInputStream("xxTTTxx.key");
	// System.out.println("fileinputstream.available() >>>" +
	// fileinputstream.available());
	// byte[] abyte = new byte[fileinputstream.available()];
	// fileinputstream.read(abyte);
	// fileinputstream.close();
	// System.out.println("original:" + abyte.length);
	// return abyte;
	// }

}
