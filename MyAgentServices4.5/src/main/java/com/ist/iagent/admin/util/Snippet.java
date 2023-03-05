package com.ist.iagent.admin.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Map;

import org.objectweb.asm.ClassReader;

public class Snippet {

	public static String[] getParameterNames(Method m) throws IOException {
		Class<?> declaringClass = m.getDeclaringClass();
		String resourceName = "/" + declaringClass.getName().replace('.', '/')
				+ ".class";
		InputStream classData = declaringClass
				.getResourceAsStream(resourceName);

		VariableReader variableDiscoverer = new VariableReader();

		ClassReader r = new ClassReader(classData);
		r.accept(variableDiscoverer, ClassReader.EXPAND_FRAMES);

		Map<Integer, String> variableNames = variableDiscoverer
				.getVariableNames(m);
		String[] parameterNames = new String[m.getParameterTypes().length];
		for (int i = 0,j=0; i < parameterNames.length; i++,j++) {
			if(variableNames.get(j).toString().equals("this")){
				j++;
			}
			parameterNames[i] = variableNames.get(j);
		}
		return parameterNames;
	}
}