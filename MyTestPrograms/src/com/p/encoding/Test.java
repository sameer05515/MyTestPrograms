package com.p.encoding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Test {

	public static void main(String[] args) {

		// scanLibForAllJars("D:\\Prem\\EclipseWS\\uidd_UAT_Code\\IVLAUA\\WebContent\\WEB-INF\\lib");
		try {
			
			String authorization="MjBGNEM5ODQ0MDkyM0NGN0FENkIxNDlGNkMyMjIzQUM3NTNBQUE5NjRDMDhDN0IxNkExQjFGRURGM0FGMDg4Ng==1111";
			
			String clearText = "Imy@1111";
			String resp = Encoder.encode(clearText);
			System.out.println("clearText :\t\t" + clearText);
			System.out.println("resp :\t\t" + resp);
			
			if(authorization==null || (!resp.equals(authorization))) {
				System.out.println("not matching");
			}else{
				System.out.println("matching");
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	private static class  Decoder{
		public static String decode(String clearText) throws NoSuchAlgorithmException {
			return clearText;
		}
		
		private static String decodeBase64(String str) {
			
			Base64.Decoder decoder = Base64.getDecoder();
			String dStr = new String(decoder.decode(str));  
			
			return dStr;
		}
		
		private static String decodeSHA256(String str) {
			return null;
		}
		
		private static String decodeSHA1(String str) {
			return null;
		}
	}

	private static class Encoder{
		
		public static String encode(String clearText) throws NoSuchAlgorithmException {
			String finalString = getBase64(getSha256(getSha1(clearText)));
			return finalString;
		}

		private static String getBase64(String str) {
			String b64 = new String(Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8)));
			return b64;
		}

		private static synchronized String getSha256(String value) throws NoSuchAlgorithmException {

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes());
			return bytesToHex(md.digest());

		}

		private static String bytesToHex(byte[] digest) {
			String hexStr = "";
			for (byte b : digest) {
				String st = String.format("%02X", b);
				// System.out.print(st);
				hexStr += st;
			}
			return hexStr;
		}

		private static String getSha1(String input) throws NoSuchAlgorithmException {

			/* getInstance() method is called with algorithm SHA-1 */
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			/*
			 * digest() method is called
			 * 
			 * to calculate message digest of the input string
			 * 
			 * returned as array of byte
			 */
			byte[] messageDigest = md.digest(input.getBytes());

			/* Convert byte array into signum representation */
			BigInteger no = new BigInteger(1, messageDigest);

			/* Convert message digest into hex value */
			String hashtext = no.toString(16);

			/* Add preceding 0s to make it 32 bit */
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			/* return the HashText */
			return hashtext;

		}
	}

	private static void scanLibForAllJars(String libLocation) {
		// File f = new File("D:\\Prem\\Dilip\\IVLAUA\\IVLAUA\\WEB-INF\\lib");

		File f = new File(libLocation);

		File[] children = f.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File arg0, String arg1) {
				if (arg1 != null && arg1.trim().toLowerCase().endsWith(".jar")) {
					return true;
				}
				return false;
			}
		});

		// StringBuffer sb = new StringBuffer();
		// sb.append(".;");
		// for (File ff : children) {
		// if (ff != null && ff.isFile() && ff.getName() != null) {
		// sb.append("..\\" + ff.getName() + ";");
		// }
		// }
		//
		// System.out.println(sb.toString());

		try {
			aa(children);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void aa(File[] children) throws IOException {

		System.out.println("Size :" + children.length);

		for (File ff : children) {

			// if (ff.getName().toLowerCase().equalsIgnoreCase("rt.jar")) {
			// System.out.println("Escaping rt.jar");
			// continue;
			// }
			// StringBuffer sb = new StringBuffer();
			PrintStream ps = new PrintStream(
					new File("C:\\Users\\premendra.kumar\\Desktop\\jars\\" + ff.getName() + ".txt"));
			List<String> classNames = new ArrayList<String>();
			ps.println("\n======\n" + ff.getAbsolutePath() + "\n======\n");
			ZipInputStream zip = new ZipInputStream(new FileInputStream(ff.getAbsolutePath()));
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
				if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
					// This ZipEntry represents a class. Now, what class does it represent?
					String className = entry.getName().replace('/', '.'); // including ".class"
					classNames.add(className.substring(0, className.length() - ".class".length()));
				}

			}

			for (String str : classNames) {
				ps.println(str);
			}

			// ps.println(sb.toString());
		}

	}

}

