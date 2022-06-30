package com.test.enc.decr.util;

//import java.util.*;
//import com.basic.common.access.AccessPoint;
//import com.ils.ibatis.utility.CommonEFSCO_UtilityIbatis;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
//import java.util.HashMap;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
//import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;

public class AESEncryption {

    /*
    
     select hex(aes_encrypt('123456', 'strongkey@#$&12093487'))//for encryption


     select aes_decrypt(UNHEX('enriptedString'), 'strongkey@#$&12093487') //for decryption
    
    
     */
    private static final Logger LOGGER = Logger.getLogger(AESEncryption.class.getName());
    private static SecretKeySpec secretKey;
    private static byte[] key;
    public static final String KEY_STR = "strongkey@#&12093487";

    private static final String SESSION_ID = "abcdefg";
    private static final String DEFAULT_SALT = "salt";

    /* Encryption Method */
    public static String encrypt(String strToEncrypt, String sessionId) {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            sessionId = (sessionId!=null && sessionId.trim().length()>0)? sessionId.trim():DEFAULT_SALT;
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            /* Create factory for secret keys. */
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            /* PBEKeySpec class implements KeySpec interface. */
            KeySpec spec = new PBEKeySpec(KEY_STR.toCharArray(), sessionId.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            /* Retruns encrypted value. */
            String encryptedValue = Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
            return encodeToBase64(encryptedValue);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException |
                 InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("Error occured during encryption: " + e.toString());
        }
        return null;
    }

    /* Decryption Method */
    public static String decrypt(String strToDecrypt,String sessionId) {
        try {
            /* Declare a byte array. */
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            sessionId = (sessionId!=null && sessionId.trim().length()>0)? sessionId.trim():DEFAULT_SALT;
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            /* Create factory for secret keys. */
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            /* PBEKeySpec class implements KeySpec interface. */
            KeySpec spec = new PBEKeySpec(KEY_STR.toCharArray(), sessionId.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            /* Retruns decrypted value. */
            String decodedValue = decodeFromBase64(strToDecrypt);
            return new String(cipher.doFinal(Base64.getDecoder().decode(decodedValue)));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException |
                 InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println("Error occured during decryption: " + e.toString());
        }
        return null;
    }

    public static String encodeToBase64(String message) {
        return Base64.getEncoder().encodeToString(message.getBytes());
    }

    public static String decodeFromBase64(String encodedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }

//    public static String encriptBySql(String value) {
//        String str = "";
//        SqlSession session = AccessPoint.getDBTemplate().openSession();
//        try {
//            CommonEFSCO_UtilityIbatis mapper = (CommonEFSCO_UtilityIbatis) session.getMapper(CommonEFSCO_UtilityIbatis.class);
//            Map m = new HashMap();
//            m.put("key", KEY_STR);
//            m.put("string", value);
//            str = mapper.encryptCommon(m);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("" + e.getMessage());
//        } finally {
//            session.close();
//        }
//        return str;
//    }

//    public static String decriptBySql(String value) {
//        String str = "";
//        SqlSession session = AccessPoint.getDBTemplate().openSession();
//        try {
//            CommonEFSCO_UtilityIbatis mapper = (CommonEFSCO_UtilityIbatis) session.getMapper(CommonEFSCO_UtilityIbatis.class);
//            Map m = new HashMap();
//            m.put("key", KEY_STR);
//            m.put("string", value);
//            str = mapper.decryptCommon(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("" + e.getMessage());
//        } finally {
//            session.close();
//        }
//        return str;
//    }
    
//     public static String encriptBySqlNSession(String value, String sessionid) {
//        String str = "";
//        try (SqlSession session = AccessPoint.getDBTemplate().openSession()) {
//            CommonEFSCO_UtilityIbatis mapper = (CommonEFSCO_UtilityIbatis) session.getMapper(CommonEFSCO_UtilityIbatis.class);
//            Map m = new HashMap();
//            m.put("key", sessionid+KEY_STR);
//            m.put("string", value);
//            str = mapper.encryptCommon(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("" + e.getMessage());
//        }
//        return str;
//    }

//    public static String decriptBySqlNSessionAndStrongKey(String value, String sessionId) {
//        String str = "";
//        SqlSession session = AccessPoint.getDBTemplate().openSession();
//        try {
//            CommonEFSCO_UtilityIbatis mapper = (CommonEFSCO_UtilityIbatis) session.getMapper(CommonEFSCO_UtilityIbatis.class);
//            Map m = new HashMap();
//            m.put("key", sessionId+KEY_STR);
//            m.put("string", value);
//            str = mapper.decryptCommon(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("" + e.getMessage());
//        } finally {
//            session.close();
//        }
//        return str;
//    }
    
//    public static String decriptBySqlNSession(String value, String sessionId) {
//        String str = "";
//        SqlSession session = AccessPoint.getDBTemplate().openSession();
//        try {
//            CommonEFSCO_UtilityIbatis mapper = (CommonEFSCO_UtilityIbatis) session.getMapper(CommonEFSCO_UtilityIbatis.class);
//            Map m = new HashMap();
//            m.put("key", sessionId);
//            m.put("string", value);
//            str = mapper.decryptCommon(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("" + e.getMessage());
//        } finally {
//            session.close();
//        }
//        return str;
//    }

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(byte[] key, String strToEncrypt) {

        String encData = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            byte[] keyBytes = new byte[16];
            int len = key.length;
            if (len > keyBytes.length) {
                len = keyBytes.length;
            }
            System.arraycopy(key, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] results = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

            BASE64Encoder encoder = new BASE64Encoder();
            encData = encoder.encode(results);

        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return encData;

    }

    public static String decrypt(String textToDecrypt, byte[] key) {
        String result = null;
        try {
            byte[] keyBytes = new byte[16];
            int len = key.length;
            if (len > keyBytes.length) {
                len = keyBytes.length;
            }
            System.arraycopy(key, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decordedValue = DatatypeConverter.parseBase64Binary(textToDecrypt);
            result = new String(cipher.doFinal(decordedValue), "UTF-8");
            LOGGER.info("result ===" + result);
        } catch (Exception ex) {
            LOGGER.error(ex);
        }

        return result;

    }
//    Code Changed by CampusEAI for Security Fixes - 31/JULY/2020    Start

//    public static String encriptBySql(String key, String value) {
//        String str = "";
//        SqlSession session = AccessPoint.getDBTemplate().openSession();
//        try {
//            CommonEFSCO_UtilityIbatis mapper = (CommonEFSCO_UtilityIbatis) session.getMapper(CommonEFSCO_UtilityIbatis.class);
//            Map m = new HashMap();
//            m.put("key", key);
//            m.put("string", value);
//            str = mapper.encryptCommon(m);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("" + e.getMessage());
//        } finally {
//            session.close();
//        }
//        return str;
//    }

//    public static String decriptBySql(HttpServletRequest request, String value) {
//    public static String decriptBySql(String Key, String value) {
////        HttpSession httpSession = request.getSession(false);
//        String str = "";
//        SqlSession session = AccessPoint.getDBTemplate().openSession();
////        String key = null;
//        try {
////            key = (String)httpSession.getAttribute("randomNumber");
//            CommonEFSCO_UtilityIbatis mapper = (CommonEFSCO_UtilityIbatis) session.getMapper(CommonEFSCO_UtilityIbatis.class);
//            Map m = new HashMap();
//            m.put("key", Key);
//            m.put("string", value);
//            str = mapper.decryptCommon(m);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("" + e.getMessage());
//        } finally {
//            session.close();
//        }
//        return str;
//    }

    //    Code Changed by CampusEAI for Security Fixes - 31/JULY/2020   End
}
