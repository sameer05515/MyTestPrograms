package com.ist.iagent.admin.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class CustomClassLoader extends ClassLoader {
	static String destDir = PropertyUtil.getInstance().getValueForKey("serverLibPath");
	static Logger log = Logger.getLogger(CustomClassLoader.class);

	public CustomClassLoader() {
		
		super(CustomClassLoader.class.getClassLoader());
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		return findClass(className);
	}

	public Class<?> findClass(String className) {
		byte classByte[];
		Class<?> result = null;
		result = (Class<?>) classes.get(className);
		if (result != null) {
			return result;
		}

		try {
			return findSystemClass(className);
		} catch (Exception e) {
		}
		try {
			String classPath = ((String) ClassLoader.getSystemResource(
					destDir + className.replace('.', File.separatorChar)
							+ ".class").getFile()).substring(1);
			classByte = loadClassData(classPath);
			result = defineClass(className, classByte, 0, classByte.length,
					null);
			classes.put(className, result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private byte[] loadClassData(String className) throws IOException {

		File f;
		f = new File(className);
		int size = (int) f.length();
		byte buff[] = new byte[size];
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		dis.readFully(buff);
		dis.close();
		return buff;
	}

	private Hashtable classes = new Hashtable();
}