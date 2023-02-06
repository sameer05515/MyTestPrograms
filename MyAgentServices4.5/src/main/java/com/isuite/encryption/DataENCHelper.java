package com.isuite.encryption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DataENCHelper {

	public static void generateAndSaveEncryptedKeyFile(String password,
			Path keyFilePath) throws NoSuchAlgorithmException, IOException,
			InvalidKeyException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {

		KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
		keyGenerator.init(128);
		SecretKey secretkey = keyGenerator.generateKey();

		Files.write(keyFilePath,
				PSKHelper.encryptWithPassword(secretkey.getEncoded(), password));

	}

	public static void encodeDataToFile(byte[] data, String password,
			Path keyFilePath, Path dataFilePath) throws InvalidKeyException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, IOException {

		byte[] encKey = PSKHelper.decryptWithPassword(
				Files.readAllBytes(keyFilePath), password);

		SecretKey secretKey = new SecretKeySpec(encKey, 0, encKey.length,
				"Blowfish");

		Files.write(dataFilePath,
				PSKHelper.encryptWithSecureKey(data, secretKey));

	}

	public static byte[] decodeDataFromFile(String password, Path keyFilePath,
			Path dataFilePath) throws InvalidKeyException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, IOException {

		byte[] encKey = PSKHelper.decryptWithPassword(
				Files.readAllBytes(keyFilePath), password);

		SecretKey secretKey = new SecretKeySpec(encKey, 0, encKey.length,
				"Blowfish");

		return PSKHelper.decryptWithSecureKey(Files.readAllBytes(dataFilePath),
				secretKey);
	}

}
