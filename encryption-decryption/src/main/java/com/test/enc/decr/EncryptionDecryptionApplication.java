package com.test.enc.decr;


import com.test.enc.decr.util.AESEncryption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionDecryptionApplication {

	public static void main(String[] args) {
		byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

		String originalval = "99999999999999999999999999999999999999999999999999999999999";
		/* Call the encrypt() method and store result of encryption. */
		String encryptedval = AESEncryption.encrypt(iv,originalval);
//		String md5= getMd5(encryptedval);
		String encBase64 = encodeToBase64(encryptedval);
		/* Call the decrypt() method and store result of decryption. */

		String decBase64 = decodeFromBase64(encBase64);
		String decryptedval = AESEncryption.decrypt(decBase64,iv);

		/* Display the original message, encrypted message and decrypted message on the console. */
		System.out.println("Original value: " + originalval);
		System.out.println("Encrypted value: " + encryptedval);
		System.out.println("encBase64: "+encBase64);
		System.out.println("decBase64: "+decBase64);
		System.out.println("Decrypted value: " + decryptedval);

	}

	public static String encodeToBase64(String message) {
		return Base64.getEncoder().encodeToString(message.getBytes());
	}

	public static String decodeFromBase64(String encodedMessage) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}

	public static String getMd5(String input)
	{
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

}
