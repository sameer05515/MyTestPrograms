package com.ist.iagent.admin.db.wrapper;

public enum IAgentServiceConfigConstants {

	ROOT("iagent-service-config"), JARLIST("jar-list"), JAVA_SERVICE_LIST(
			"java-services"), WSDL_LIST("wsdl-list"), WS_SERVICE_LIST(
			"ws-services"), DATA_SOURCE_LIST("data-source-list"), QUERY_SERVICE_LIST(
			"query-services");

	/** ### java services ### */
	public enum JarDTO {
		node("jar"), jarId("jarId"), jarName("jarName");

		private String nodeName;

		JarDTO(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	public enum JavaService {
		node("service"), serviceId("serviceId"), serviceName("serviceName"), className(
				"className"), methodName("methodName"), saveLastRecord(
				"saveLastRecord"), linkedJarId("linkedJarId"), loggingAllowed(
				"loggingAllowed"), inputType("input-parameters"), returnType(
				"return-parameters");

		private String nodeName;

		JavaService(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	public enum ParameterDescDTO {
		node("service-parameter"), paramId("paramId"), serviceId("serviceId"), parameterName(
				"parameterName"), parameterType("parameterType"), parameterDescr(
				"parameterDescr");

		private String nodeName;

		ParameterDescDTO(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	/** ### web services ### */
	public enum WSdlDTO {
		node("wsdl-value-object"), wsId("wsId"), wsName("wsName"), wsURL(
				"wsURL"), nameSpace("nameSpace"), usePrefix("usePrefix"), soapBindingInterface(
				"soapBindingInterface"), serviceClass("serviceClass"), wsPackageName(
				"wsPackageName");

		private String nodeName;

		WSdlDTO(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	public enum WSService {
		node("ws-service"), serviceId("serviceId"), serviceName("serviceName"), operationName(
				"operationName"), saveLastRecord("saveLastRecord"), linkedWSId(
				"linkedWSId"), portName("portName"), loggingAllowed(
				"loggingAllowed"), parameterSchema("parameterSchema"), inputType(
				"input-parameters"), returnType("return-parameters");

		private String nodeName;

		WSService(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	public enum DataSource {
		node("datasource"), dbID("dbID"), dbAlias("dbAlias"), dbUsername(
				"dbUsername"), dbPassword("dbPassword"), dbDriverClassName(
				"dbDriverClassName"), dbURL("dbURL");

		private String nodeName;

		DataSource(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	/** ### query services ### */

	public enum QueryService {
		node("query-service"), serviceId("serviceId"), serviceName(
				"serviceName"), linkedDbID("linkedDbID"), storedProc(
				"storedProc"), queryText("queryText"), queryResultType(
				"queryResultType"), loggingAllowed("loggingAllowed"), queryType(
				"queryType"), inputType("input-parameters"), returnType(
				"return-parameters");

		private String nodeName;

		QueryService(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	public enum QueryParameter {
		node("query-service-parameter"), paramId("paramId"), serviceId(
				"serviceId"), parameterName("parameterName"), parameterType(
				"parameterType"), parameterDescr("parameterDescr"), parameterResultType(
				"parameterResultType"), blankAllowed("blankAllowed"), paramValue(
				"paramValue");

		private String nodeName;

		QueryParameter(String name) {
			this.nodeName = name;
		}

		public String getNodeName() {
			return nodeName;
		}
	}

	private String nodeName;

	IAgentServiceConfigConstants(String name) {
		this.nodeName = name;
	}

	public String getNodeName() {
		return nodeName;
	}

}
