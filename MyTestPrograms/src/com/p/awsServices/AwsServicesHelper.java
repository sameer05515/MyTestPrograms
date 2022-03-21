package com.p.awsServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AwsServicesHelper {

	private static final String[] TOKEN_ARRAY = { "===start===", "===end===" };
	private static String rawFileLocation = "/home/premendra/Downloads/apache-tomcat-8.5.40/webapps/my-pages/other-sample-application/aws-services/data/raw-services-overview-2.txt";

	private static final int CURRENT = 0;
	private static final int NEXT = 1;
	@SuppressWarnings("unused")
	private static final int PREVIOUS = -1;
	private static final List<String> TOKEN_LIST = Arrays.asList(TOKEN_ARRAY);

	public static void main(String[] args) {
		step1ConvertRawToJsonArray();
	}

	/**
	 * 
	 * CONVERT RAW DATA IN SECTION ARRAY CONTAINING SERVICE ARRAY
	 * 
	 */
	private static void step1ConvertRawToJsonArray() {

		try {
			List<String> rawDataList = getRawList(rawFileLocation);
			JsonArray sectionArray = new JsonArray();

			if (rawDataList != null && rawDataList.size() > 0) {
				for (String line : rawDataList) {
//					System.out.println(line);
				}

				JsonObject currentSectionObject = null;
				String currentKey = null;
				boolean hasMoreToken = true;
				int tokenTraversalIndex = 0;
				int rawListSize = rawDataList.size();
				
				int sectionIdIndex=1;
				int serviceIdIndex=1;
				
				Map<String, String> serviceIdMap=new HashMap<String, String>();
				
				while (hasMoreToken && tokenTraversalIndex < rawListSize) {
					int nextTokenIndex = getTokenIndex(rawDataList, tokenTraversalIndex);
					// System.out.printf("nextTokenIndex == %s rawListSize %s %n", nextTokenIndex,
					// rawListSize);
					tokenTraversalIndex = nextTokenIndex;

					if (tokenTraversalIndex == rawListSize - 1) {
						hasMoreToken = false;
						break;
					}

					JsonObject currentlineHavingTokenObject = getLineJson(rawDataList, tokenTraversalIndex++, CURRENT);

					if (currentlineHavingTokenObject.has("isToken")
							&& currentlineHavingTokenObject.get("isToken").getAsBoolean()) {
						String key = currentlineHavingTokenObject.get("key").getAsString();
						currentKey = key;
						System.out.printf("==================== %ncurrentKey == %s%n", currentKey);

						int nextNonEmptyIndex = skipEmptyLines(rawDataList, tokenTraversalIndex);
						tokenTraversalIndex = nextNonEmptyIndex;
						JsonObject nextLineObject = getLineJson(rawDataList, tokenTraversalIndex, CURRENT);

						if (nextLineObject != null && nextLineObject.get("isToken").getAsBoolean()) {
							hasMoreToken = true;
							continue;
						} else {
							nextTokenIndex = getTokenIndex(rawDataList, tokenTraversalIndex);
							if ("start".equals(currentKey)) {
								currentSectionObject = new JsonObject();
								JsonArray servicesArr = new JsonArray();
								JsonObject service = null;
								int pointer = 0;
//								int id=0;
								while (tokenTraversalIndex < nextTokenIndex) {
									String line = nextLineObject.get("line").getAsString();

									if (pointer == 0) {
										System.out.println(line);
										currentSectionObject.addProperty("sectionName", line);
										currentSectionObject.addProperty("id", "SECTION-"+sectionIdIndex++ +"-"+line.replaceAll(" ", "-"));
										currentSectionObject.add("description", new JsonArray());
									} else if (pointer % 2 == 1) {
										service = new JsonObject();
										service.addProperty("serviceName", line);
										///
										String serviceId="";
										if(serviceIdMap.containsKey(line)) {
											serviceId=serviceIdMap.get(line);
										}else {
											serviceId="SERVICE-"+serviceIdIndex++ +"-"+line.replaceAll(" ", "-");
											serviceIdMap.put(line, serviceId);
										}
										service.addProperty("id", serviceId);
										///
										service.addProperty("expansionOfAcronym", "");
										service.add("description", new JsonArray());
									} else if (pointer % 2 == 0) {
										service.addProperty("shortDescription", line);
										servicesArr.add(service);
									}
									pointer++;
									nextLineObject = getLineJson(rawDataList, tokenTraversalIndex++, NEXT);
								}
								currentSectionObject.add("services", servicesArr);
								tokenTraversalIndex = nextTokenIndex;
							} else if ("end".equals(currentKey)) {
								sectionArray.add(currentSectionObject);

								currentSectionObject = null;
								tokenTraversalIndex = nextTokenIndex;
							} else {
								tokenTraversalIndex = nextTokenIndex;
								hasMoreToken = true;
								continue;
							}

						}

					}

				}

			}

//			PrintStream ps = new PrintStream(new File(
//					"/home/premendra/Downloads/apache-tomcat-8.5.40/webapps/my-pages/other-sample-application/aws-services/js/data/raw-services-overview-2.json"));
			printJsonElementToFile(sectionArray,
					"/home/premendra/Downloads/apache-tomcat-8.5.40/webapps/my-pages/other-sample-application/aws-services/data/raw-services-overview-2.json");
			System.out.println(sectionArray.size());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * HELPER METHODS
	 * 
	 */
	private static void printJsonElementToFile(JsonElement element, String filePath) throws FileNotFoundException {
		try (PrintStream printStreamObject = new PrintStream(new File(filePath))) {
			Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
			printStreamObject.println(prettyJson.toJson(element));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}

		// printStreamObject.close();
	}

	private static void printJsonElement(JsonElement element, PrintStream printStreamObject) {
		Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
		printStreamObject.println(prettyJson.toJson(element));
		// printStreamObject.close();
	}

	public static List<String> getRawList(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filePath));
		List<String> rawDataList = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			rawDataList.add(line);
		}
		scanner.close();
		return rawDataList;
	}

	private static JsonObject getLineJson(List<String> rawDataList, int index, int direction) {
		JsonObject lineJson = null;
		if (rawDataList != null) {
			int calculatedIndex = index + direction + rawDataList.size();
			String line = rawDataList.get(calculatedIndex % rawDataList.size());
			lineJson = new JsonObject();
			lineJson.addProperty("line", line);
			if (TOKEN_LIST.contains(line)) {
				lineJson.addProperty("isToken", true);
				lineJson.addProperty("key", line.replaceAll("=", ""));
			} else {
				lineJson.addProperty("isToken", false);
				lineJson.addProperty("key", "");
			}
			lineJson.addProperty("isEmptyOrNull", (line == null || line.isEmpty()));
		} else {
			System.out.println("Null rawDataList!");
		}
		return lineJson;
	}

	private static int getTokenIndex(List<String> rawDataList, int index) {
		// JsonObject tokenJson=null;
		int nextTokenIndex = -1;
		if (rawDataList != null && rawDataList.size() > 0 && index >= 0 && index < rawDataList.size()) {
//			int tokenTraversalIndex = index;
			int rawListSize = rawDataList.size();
			int tokenTraversalIndex = (index + rawListSize) % rawListSize;

			int nextNonEmptyIndex = skipEmptyLines(rawDataList, tokenTraversalIndex);
			tokenTraversalIndex = nextNonEmptyIndex;
			boolean isTokenIndex = false;
			while (!isTokenIndex) {
				JsonObject currentlineObject = getLineJson(rawDataList, tokenTraversalIndex, CURRENT);
				if (currentlineObject != null) {
					boolean isToken = currentlineObject.has("isToken") ? currentlineObject.get("isToken").getAsBoolean()
							: false;
					if (isToken) {
						nextTokenIndex = tokenTraversalIndex;
						isTokenIndex = true;
						break;
					} else {
						tokenTraversalIndex++;
						isTokenIndex = false;
					}
				}
			}

		}

		return nextTokenIndex;
	}

	private static int skipEmptyLines(List<String> rawDataList, int index) {
		int nextNonEmptyIndex = -1;
		if (rawDataList != null && rawDataList.size() > 0 && index >= 0 && index < rawDataList.size()) {
			int rawListSize = rawDataList.size();
			int tokenTraversalIndex = (index + rawListSize) % rawListSize;
			while (tokenTraversalIndex < rawDataList.size()) {
				JsonObject currentlineObject = getLineJson(rawDataList, tokenTraversalIndex, CURRENT);
				if (currentlineObject != null) {
					boolean isEmptyOrNull = currentlineObject.has("isEmptyOrNull")
							? currentlineObject.get("isEmptyOrNull").getAsBoolean()
							: false;
					if (isEmptyOrNull) {
						nextNonEmptyIndex = ++tokenTraversalIndex;
					} else {
						return tokenTraversalIndex;
					}
				}
			}

		}
		return nextNonEmptyIndex;
	}

}
