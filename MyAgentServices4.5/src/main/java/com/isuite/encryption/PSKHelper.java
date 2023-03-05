package com.isuite.encryption;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class PSKHelper {

	private static final String PS_ALGORITHM = "PBEWithMD5AndDES";
	
	private static final byte[] salt = { (byte) 0xf9, (byte) 0xfB, (byte) 0xf8,
			(byte) 0xf2, (byte) 0x56, (byte) 0xf4, (byte) 0xE3, (byte) 0x03 };
	private static final int iterationCount = 19;

	public static SecretKey getPasswordProtectedKey(String passPhrase)
			throws IOException, NoSuchAlgorithmException,
			InvalidKeySpecException {

		
		
		KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
				iterationCount);
		SecretKey key = SecretKeyFactory.getInstance(PS_ALGORITHM)
				.generateSecret(keySpec);
		return key;
	}

	public static byte[] encryptWithPassword(byte[] dataBytes, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		byte[] encryptedBytes = null;

		SecretKey skey = PSKHelper.getPasswordProtectedKey(password);
		Cipher cipher = Cipher.getInstance(skey.getAlgorithm());
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
				iterationCount);
		cipher.init(Cipher.ENCRYPT_MODE, skey, paramSpec);

		encryptedBytes = cipher.doFinal(dataBytes);

		return encryptedBytes;
	}

	public static byte[] decryptWithPassword(byte[] dataBytes, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException,
			IOException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		byte[] dectyptedBytes = null;

		SecretKey skey = PSKHelper.getPasswordProtectedKey(password);

		Cipher cipher = Cipher.getInstance(skey.getAlgorithm());

		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt,
				iterationCount);
		cipher.init(Cipher.DECRYPT_MODE, skey, paramSpec);

		dectyptedBytes = cipher.doFinal(dataBytes);
		return dectyptedBytes;
	}

	public static byte[] encryptWithSecureKey(byte[] dataBytes, SecretKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] encryptedBytes = null;

		Cipher cipher = Cipher.getInstance(key.getAlgorithm());

		cipher.init(Cipher.ENCRYPT_MODE, key);

		encryptedBytes = cipher.doFinal(dataBytes);

		return encryptedBytes;
	}

	public static byte[] decryptWithSecureKey(byte[] dataBytes, SecretKey key)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException
			{
		byte[] dectyptedBytes = null;
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());

		cipher.init(Cipher.DECRYPT_MODE, key);

		dectyptedBytes = cipher.doFinal(dataBytes);

		return dectyptedBytes;
	}

}
