package com.ist.iagent.admin.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;

public class VariableReader extends EmptyVisitor {
	private Map<String, Map<Integer, String>> methodParameters = new HashMap<String, Map<Integer, String>>();
	private String currentMethod;

	public MethodVisitor visitMethod(int access, String name, String desc,
			String signature, String[] exceptions) {

		currentMethod = name + desc;
		return this;

	}

	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		Map<Integer, String> parameters = methodParameters.get(currentMethod);
		if (parameters == null) {
			parameters = new HashMap<Integer, String>();
			methodParameters.put(currentMethod, parameters);
		}
		parameters.put(index, name);
	}

	public Map<Integer, String> getVariableNames(Method m) {
		Map<Integer, String> ret = methodParameters.get(m.getName()
				+ Type.getMethodDescriptor(m));
		return methodParameters.get(m.getName() + Type.getMethodDescriptor(m));
	}

}