package test.p;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ParameterSplitter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String className = "com.isuite.tree.Node";

		// String className = "com.test.globalweather.GlobalWeatherSoap";

		// String className =
		// "com.isuite.ws.service.serviceLoaderrpc.ServiceLoaderRPC";

		try {
			new ParameterSplitter().analyze(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void analyze(String className) throws IOException,
			ClassNotFoundException {

		File file = new File(
				"C:\\Users\\premendra.kumar\\Desktop\\DUMP/p.txt");
		/** if file doesnt exists, then create it */
		if (!file.exists()) {
			file.createNewFile();
		}
		PrintStream printStream = new PrintStream(new FileOutputStream(file));
		System.setOut(printStream);
		System.setErr(printStream);
		analyzeClass(className);
	}

	private void analyzeClass(String className) throws ClassNotFoundException {
		try {

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
			Class<?> c = Class.forName(className);
			System.out.println("Class == " + c);

			/** ####---CONSTRUCTORS--##### */
			System.out.println(getTabs(1) + "Constructor List : ");
			Constructor<?>[] cons = c.getConstructors();
			for (int i = 0; i < cons.length; i++) {
				Constructor<?> constructor = cons[i];
				System.out.print(getTabs(2) + "Constructor[" + (i + 1)
						+ "] == " + constructor.getName() + "(");
				int count = 0;
				for (Class<?> conss : constructor.getParameterTypes()) {
					System.out
							.print(conss.getName()
									+ ((count < constructor.getParameterTypes().length - 1) ? " , "
											: ""));
					count++;
				}
				System.out.print(")\n");
			}

			/** ####---METHODS--##### */
			System.out.println("\n\n" + getTabs(1) + "Method List");
			Method[] meths = c.getDeclaredMethods();
			for (int i = 0; i < meths.length; i++) {
				Method meth = meths[i];
				int mod = meth.getModifiers();
				System.out.println(getTabs(2) + "Method[" + (i + 1) + "] == "
						+ meth.getName() + " $$ is Public == "
						+ Modifier.isPublic(mod));
				Class<?>[] parameterTypes = meth.getParameterTypes();
				getParameterTypes(parameterTypes);
			}
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
		} catch (ClassNotFoundException e) {
			System.err.println("ClassNotFound : " + className + " : " + e);
			throw e;
		}
	}

	private void getParameterTypes(Class<?>[] parameterTypes) {
		System.out.println(getTabs(3) + "Parameter Types List : ");
		for (int j = 0; j < parameterTypes.length; j++) {
			Class<?> parameterType = parameterTypes[j];
			System.out.println(getTabs(4) + "parameterTypes[" + (j + 1)
					+ "] == " + parameterType.getName());
			if (splittingRequired(parameterType, 0)) {
				splitParameters(parameterType, 0);
			}
		}
		System.out.println(getTabs(3) + "Total parameters == "
				+ parameterTypes.length + "\n");
	}

	private boolean isPrimitive(String parameterType) {
		boolean isPrimitive = false;
		if (parameterType.equals("boolean")) {
			isPrimitive = true;
		} else if (parameterType.equals("byte")) {
			isPrimitive = true;
		} else if (parameterType.equals("char")) {
			isPrimitive = true;
		} else if (parameterType.equals("double")) {
			isPrimitive = true;
		} else if (parameterType.equals("float")) {
			isPrimitive = true;
		} else if (parameterType.equals("int")) {
			isPrimitive = true;
		} else if (parameterType.equals("long")) {
			isPrimitive = true;
		} else if (parameterType.equals("short")) {
			isPrimitive = true;
		} else if (parameterType.equals("void")) {
			isPrimitive = true;
		} else {
			isPrimitive = false;
		}
		return isPrimitive;
	}

	/**
	 * gives Class object for a java primitive
	 * 
	 * @param parameterType
	 *            String name of parameterType
	 * @return Class object for a java primitive
	 * */
	private Class<?> getWrapperClass(String parameterType) {
		if (parameterType.equals("boolean")) {
			return Boolean.class;
		} else if (parameterType.equals("byte")) {
			return Byte.class;
		} else if (parameterType.equals("char")) {
			return Character.class;
		} else if (parameterType.equals("double")) {
			return Double.class;
		} else if (parameterType.equals("float")) {
			return Float.class;
		} else if (parameterType.equals("int")) {
			return Integer.class;
		} else if (parameterType.equals("long")) {
			return Long.class;
		} else if (parameterType.equals("short")) {
			return Short.class;
		} else if (parameterType.equals("void")) {
			return Void.class;
		}
		return null;
	}

	private boolean splittingRequired(Class<?> name, int increment) {
		boolean splittingRequired = true;
		// String name = parameterClassName.getName();
		boolean isPrimitive = isPrimitive(name.getName());
		if (isPrimitive) {
			name = getWrapperClass(name.getName());
		}
		if (name == java.lang.String.class) {
			splittingRequired = false;
		} else if (name == java.lang.Object.class) {
			splittingRequired = false;
		} /*else if (name == flex.messaging.io.amf.ASObject.class) {
			splittingRequired = false;
		} */else if (name == java.util.List.class) {
			splittingRequired = false;
		} else if (name == java.util.ArrayList.class) {
			splittingRequired = false;
		} else if (name == java.lang.Boolean.class) {
			splittingRequired = false;
		} else if (name == java.lang.Byte.class) {
			splittingRequired = false;
		} else if (name == java.lang.Character.class) {
			splittingRequired = false;
		} else if (name == java.lang.Double.class) {
			splittingRequired = false;
		} else if (name == java.lang.Float.class) {
			splittingRequired = false;
		} else if (name == java.lang.Integer.class) {
			splittingRequired = false;
		} else if (name == java.lang.Long.class) {
			splittingRequired = false;
		} else if (name == java.lang.Short.class) {
			splittingRequired = false;
		}
		// + "isPrimitive == "+ isPrimitive
		// System.out.println(getTabs(4 + increment) + "Field Type == " + name
		// + " splittingRequired == " + splittingRequired);
		return splittingRequired;
	}

	private void splitParameters(Class<?> parameterClassName, int increment) {
		Field[] fields = parameterClassName.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			System.out.println(getTabs(5 + increment) + ">>field name : "
					+ fields[i].getName() + " # field type :: "
					+ fields[i].getType());

			if (splittingRequired(fields[i].getType(), increment + 1)) {
				splitParameters(fields[i].getType(), increment + 1);
			}
		}
	}

	private String getTabs(int count) {
		String result = "";
		while (count > 0) {
			result += "\t";
			count--;
		}
		return result;
	}

}
