package com.ist.iagent.admin.jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.ClassDTO;
import com.ist.iagent.admin.db.pojo.JarDTO;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.PublicMethodDTO;
import com.ist.iagent.admin.util.PropertyUtil;
import com.ist.iagent.admin.util.Snippet;

public class JarRPCHandler {

	static String destDir = PropertyUtil.getInstance().getValueForKey(
			"serverLibPath");
	static Logger log = Logger.getLogger(JarRPCHandler.class);

	/**
	 * Gives names of all classes in a given jar file
	 * 
	 * @param jarName
	 *            name of jar file in which we check for classes
	 * @return List of all required classes' name
	 */

	public static List<String> getAllClassName(String jarName) {
		ArrayList<String> classes = new ArrayList<String>();
		jarName = destDir + jarName;
		log.debug("Jar " + jarName + " looking for all classes");

		File file = new File(jarName);
		JarInputStream jarFile = null;
		try {
			if (file.exists()) {
				jarFile = new JarInputStream(new FileInputStream(file));
				JarEntry jarEntry;
				boolean flag = true;
				while (flag) {
					jarEntry = jarFile.getNextJarEntry();
					if (jarEntry == null) {
						flag = false;
						break;
					}
					String classname = jarEntry.getName()
							.replaceAll("/", "\\.");
					if (classname.endsWith(".class")) {
						Class c = Class.forName(classname.substring(0,
								classname.indexOf(".class")));
						classes.add(classname.substring(0,
								classname.indexOf(".class")));
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception occured while getting class names form jar : "
					+ jarName, e);
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (Exception e) {
			}
		}
		log.debug("classes found : " + classes);
		return classes;
	}

	/**
	 * Gives names of all classes in a given jar file which are implementing a
	 * given interface
	 * 
	 * @param jarDTO
	 *            DTO of jar file in which we check for classes
	 * @return List of all required classes' name
	 */
	public static List<ClassDTO> getAllClassInJar(JarDTO objJarDTO) {
		ArrayList<ClassDTO> classes = new ArrayList<ClassDTO>();
		String jarName = destDir + objJarDTO.getJar_name();
		log.debug("Jar " + jarName + " looking for classes");
		File file = new File(jarName);
		JarInputStream jarFile = null;

		try {
			if (file.exists()) {
				jarFile = new JarInputStream(new FileInputStream(file));
				JarEntry jarEntry;
				while (true) {
					jarEntry = jarFile.getNextJarEntry();
					if (jarEntry == null) {
						break;
					}
					String classname = jarEntry.getName()
							.replaceAll("/", "\\.");
					if (classname.endsWith(".class")) {
						Class<?> c = Class.forName(classname.substring(0,
								classname.indexOf(".class")));
						if (!c.isInterface()) {
							// Class<?>[] interfaces = c.getInterfaces();

							// for (int z = 0; z < interfaces.length; z++) {
							// if
							// (interfaces[z].getName().equals(interfaceName)) {
							ClassDTO objClassDTO = new ClassDTO();
							objClassDTO.setLinked_jar_id(objJarDTO.getJar_id());
							objClassDTO.setLinkedJarName(objJarDTO
									.getJar_name());
							objClassDTO.setClassName(classname.substring(0,
									classname.indexOf(".class")));
							classes.add(objClassDTO);
							// }
							// }
						}

					}
				}
			}
		} catch (Exception e) {
			log.error("Exception occured while getting class names form jar : "
					+ jarName, e);
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (Exception e) {
			}
		}
		log.debug("All classes found in jar :- " + classes);
		return classes;
	}

	/**
	 * Gives names of all public methods in a given class file name
	 * 
	 * @param className
	 *            name of class file in which we check for methods
	 * @return List of all required methods
	 */
	public static List<PublicMethodDTO> getPublicMethodsInClass(
			ClassDTO objClassDTO) {
		ArrayList<PublicMethodDTO> publicMethods = new ArrayList<PublicMethodDTO>();
		try {
			String className = objClassDTO.getClassName();
			Class<?> c = Class.forName(className);
			Method[] methods = c.getDeclaredMethods();
			if (methods.length > 0) {
				for (int z = 0; z < methods.length; z++) {
					int mod = methods[z].getModifiers();
					if (Modifier.isPublic(mod)) {
						PublicMethodDTO objPublicMethodDTO = new PublicMethodDTO();
						Class<?> pvec[] = methods[z].getParameterTypes();
						List<ParameterDescDTO> inputType = new ArrayList<ParameterDescDTO>();
						for (int j = 0; j < pvec.length; j++) {
							ParameterDescDTO objParamDesc = new ParameterDescDTO();
							objParamDesc.setParameterType(pvec[j].getName());
							inputType.add(objParamDesc);
						}

						try {
							String[] names = Snippet
									.getParameterNames(methods[z]);
							for (int index = 0; index < names.length; index++) {
								ParameterDescDTO objParamDesc = inputType
										.get(index);
								objParamDesc.setParameterName(names[index]);
								inputType.remove(index);
								inputType.add(index, objParamDesc);
							}
						} catch (IOException e) {
							e.printStackTrace();
							log.error("IOException from here", e);
						} catch (Exception e) {
							e.printStackTrace();
							log.error("IOException from here", e);
						} catch (Throwable e) {
							e.printStackTrace();
							log.error("IOException from here", e);
						}
						objPublicMethodDTO.setLinked_jar_id(objClassDTO
								.getLinked_jar_id());
						objPublicMethodDTO.setClassName(objClassDTO
								.getClassName());
						objPublicMethodDTO.setMethodName(methods[z].getName());
						objPublicMethodDTO.setInputType(inputType);
						publicMethods.add(objPublicMethodDTO);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			log.error("Class not found : ", e);
		} catch (Exception e) {
			log.error("Error occured : ", e);
		}
		log.debug("public methods found in " + objClassDTO.getClassName()
				+ " are : " + publicMethods);
		return publicMethods;
	}
}