package com.p.hiber;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.p.appconfiguration.PropertiesFile;

/**
 * @author imssbora
 */
public class HibernateUtil {

	private static String url = PropertiesFile.getDsurl();
	private static String driver = PropertiesFile.getDsdriverclassname();
	private static String uname = PropertiesFile.getDsusername();
	private static String upass = PropertiesFile.getDspassword();
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	private static Session cmsession = null;
	private static Session cdsession = null;
	private static Session fosession = null;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

				Map<String, Object> settings = new HashMap<>();
				settings.put(Environment.DRIVER, driver);
				settings.put(Environment.URL, url);
				settings.put(Environment.USER, uname);
				settings.put(Environment.PASS, upass);

				// settings.put(Environment.SHOW_SQL, true);

				// c3p0 configuration
				settings.put(Environment.C3P0_MIN_SIZE, 10); // Minimum size of pool
				settings.put(Environment.C3P0_MAX_SIZE, 20); // Maximum size of pool
				settings.put(Environment.C3P0_ACQUIRE_INCREMENT, 1);// Number of connections acquired at a time when
																	// pool is exhausted
				settings.put(Environment.C3P0_TIMEOUT, 1800); // Connection idle time
				settings.put(Environment.C3P0_MAX_STATEMENTS, 150); // PreparedStatement cache size

				settings.put(Environment.C3P0_CONFIG_PREFIX + ".initialPoolSize", 10);
				
				settings.put(Environment.POOL_SIZE, 25);

				registryBuilder.applySettings(settings);

				registry = registryBuilder.build();

				/*
				 * etadata metadata = new
				 * MetadataSources(serviceRegistry).getMetadataBuilder().build();
				 * sessionFactory= metadata.getSessionFactoryBuilder().build();
				 */

				Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	// ---------------cm
	public static Session openCmSession() {
		/*
		 * if(cmsession==null) { cmsession = getSessionFactory().openSession(); }
		 */
		cmsession = getSessionFactory().openSession();
		return cmsession;
	}

	public static void closeCmSession() {
		cmsession.close();
		// return cmsession;
	}
	// ----------------
	// ---------------------------cd

	public static Session openCdSession() {

		// if(!cdsession.isConnected()){

		cdsession = getSessionFactory().openSession();
		// }

		/*
		 * if(cdsession==null) { cdsession = getSessionFactory().openSession(); }
		 */
		return cdsession;
	}

	public static void closeCdSession() {
		cdsession.close();
		// return cmsession;
	}
	// ---------------------------

	// ---------------------------fo

	public static Session openFoSession() {
		/*
		 * if(fosession==null) { fosession = getSessionFactory().openSession(); }
		 */

		fosession = getSessionFactory().openSession();
		return fosession;
	}

	public static void closeFoSession() {
		fosession.close();
		// return cmsession;
	}
	// ---------------------------

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
