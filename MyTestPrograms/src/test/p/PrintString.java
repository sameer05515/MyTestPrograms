package test.p;

public class PrintString {

	public static void main(String[] args) {
		for(String s:sectionNames) {
			System.out.printf("   {\n" + 
					"       \"sectionName\":\"%s\",\n" + 
					"       \"description\":[],\n" + 
					"       \"topics\":[\n" + 
					"\n" + 
					"       ]\n" + 
					"   },\n",s);
		}

	}
	
	private static String[] sectionNames= {"Featured Services",
			"Analytics",
			"Application Integration",
			"AWS Cost Management",
			"Blockchain",
			"Business Applications",
			"Compute",
			"Containers",
			"Customer Engagement",
			"Database",
			"Developer Tools",
			"End User Computing",
			"Front-End Web & Mobile",
			"Game Tech",
			"Internet of Things",
			"Machine Learning",
			"Management & Governance",
			"Media Services",
			"Migration & Transfer",
			"Networking & Content Delivery",
			"Quantum Technologies",
			"Robotics",
			"Satellite",
			"Security, Identity, & Compliance",
			"Serverless",
			"Storage",
			"VR & AR"};

}
