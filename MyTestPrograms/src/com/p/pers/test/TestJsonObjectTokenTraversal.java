package com.p.pers.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class TestJsonObjectTokenTraversal {

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
				lineJson.addProperty("key", "");
			}
			lineJson.addProperty("isEmptyOrNull", (line == null || line.isEmpty()));
		} else {
			System.out.println("Null rawDataList!");
		}
		return lineJson;
	}
	
	private static JsonObject getTokenJson(List<String> rawDataList, int index) {
		JsonObject tokenJson=null;
		if (rawDataList != null && rawDataList.size() > 0 && index>0 && index<rawDataList.size()) {
			int tokenTraversalIndex = index;
			int rawListSize = rawDataList.size();			
			JsonObject currentlineObject = getLineJson(rawDataList, tokenTraversalIndex++, CURRENT);
			if (currentlineObject != null) {
				boolean isEmptyOrNull = currentlineObject.has("isEmptyOrNull")
						? currentlineObject.get("isEmptyOrNull").getAsBoolean()
						: false;
				if (isEmptyOrNull) {
//					hasMoreToken = true;
//					continue;
					JsonObject tempToken=getTokenJson(rawDataList,index+1);
					tokenJson=tempToken;
				}
			}
		}
		return tokenJson;
	}

	private static final String[] TOKEN_ARRAY = { "===start===", "===shlok===", "===shlokEng===", "===meaning===",
			"===translation===", "===commentary===", "===end===" };
	
	
	public static void main(String[] args) {
		try {
			String chapterArrJsonFilePath="D:\\Prem\\GIT-PROJ\\db-files\\smbg\\temp\\t.txt";
			String rawListFilePath="D:\\Prem\\GIT-PROJ\\db-files\\smbg\\temp\\t.txt";
			JsonArray chapterArr = getJsonArrayFromFile(chapterArrJsonFilePath);
			List<String> rawDataList = getRawList(rawListFilePath);

			if (rawDataList != null && rawDataList.size() > 0) {
				JsonObject currentVerseObject = null;
				String currentKey=null;
				boolean hasMoreToken = true;
				int tokenTraversalIndex = 0;
				int rawListSize = rawDataList.size();
				while (hasMoreToken && tokenTraversalIndex < rawListSize) {
					JsonObject currentlineObject = getLineJson(rawDataList, tokenTraversalIndex++, CURRENT);
					if (currentlineObject != null) {
						boolean isEmptyOrNull = currentlineObject.has("isEmptyOrNull")
								? currentlineObject.get("isEmptyOrNull").getAsBoolean()
								: false;
						if (isEmptyOrNull) {
							hasMoreToken = true;
							continue;
						} 
						
						if (currentlineObject.has("isToken")
								&& currentlineObject.get("isToken").getAsBoolean()) {
							String key=currentlineObject.get("key").getAsString();
							currentKey=key;
							System.out.printf("====================%s%n", key);
							if(currentKey!=null&&!currentKey.isEmpty()&&TOKEN_LIST.contains(currentKey)) {
								JsonObject nextLineObject=getLineJson(rawDataList, tokenTraversalIndex++, CURRENT);
								if (nextLineObject != null) {
									//skip empty rows(trimmed value) till non-empty line
									boolean isNextEmptyOrNull = currentlineObject.has("isEmptyOrNull")
											? currentlineObject.get("isEmptyOrNull").getAsBoolean()
											: false;
									if (isNextEmptyOrNull) {
										hasMoreToken = true;
										continue;
									}
								}
								
								
								if("start".equals(currentKey)) {
									// get id, current object, skip 2nd line , get next token
								}else if("shlok".equals(currentKey)){
									
								}else if("shlokEng".equals(currentKey)){
									
								}else if("meaning".equals(currentKey)){
									
								}else if("translation".equals(currentKey)){
									
								}else if("commentary".equals(currentKey)){
									
								}else if("end".equals(currentKey)){
									
								}else {
									hasMoreToken = true;
									continue;
								}
							}

						}
					}

				}
			}

//			for (int i = 0; i < rawDataList.size(); i++) {
////				String line = rawDataList.get(i);
//				JsonObject lineObject = getLineJson(rawDataList, i, CURRENT);
//				String line=lineObject.get("line").getAsString();
//				
//				if (lineObject.get("isToken").getAsBoolean()) {
////					String key = line.replaceAll("=", "");
//					String key=lineObject.get("key").getAsString();
//					System.out.printf("====================%s%n", key);
//					switch (key) {
//					case "start":
//						String nextStartKeyLine = rawDataList.get(++i);
//						String[] idArr = nextStartKeyLine.split(",");
//						System.out.println("start == " + idArr[0] + " " + idArr[1]);
//						currentVerseObject = getVerse(chapterArr, idArr[0], idArr[1]);
//						break;
//					case "end":
//						printJsonElement(currentVerseObject, System.out);
//						break;
//					case "shlok":
//					case "shlokEng":
//					case "commentary":
//						JsonArray jsonArray = new JsonArray();
//						int idCnt = 0;
//						for (int j = i + 1; j < rawDataList.size(); j++) {
//							String nextCSSLine = rawDataList.get(j);
//							if (Arrays.asList(TOKEN_ARRAY).contains(nextCSSLine)) {
//								i = j - 1;
//								currentVerseObject.add(key, jsonArray);
//								break;
//							} else {
//								int id = ++idCnt;
//								JsonObject obj = new JsonObject();
//								obj.addProperty("id", id);
//								obj.addProperty("value", nextCSSLine);
//								jsonArray.add(obj);
//							}
//						}
//						break;
//					case "meaning":
//						JsonArray jsonMeaningArray = new JsonArray();
//						int idMeaningCnt = 0;
//						for (int j = i + 1; j < rawDataList.size(); j++) {
//							String nextMeaningKeyLine = rawDataList.get(j);
//							if (Arrays.asList(TOKEN_ARRAY).contains(nextMeaningKeyLine)) {
//								i = j - 1;
//								currentVerseObject.add(key, jsonMeaningArray);
//								break;
//							} else {
//								String[] meanings = line.split(";");
//								for (String mean : meanings) {
//									int id = ++idMeaningCnt;
//									JsonObject obj = new JsonObject();
//									obj.addProperty("id", id);
//									obj.addProperty("sanskrit", mean.split("—")[0]);
//									obj.addProperty("meaning", mean.split("—")[1]);
//									jsonMeaningArray.add(obj);
//								}
//							}
//						}
//						break;
//					case "translation":
//						JsonArray jsonTranslationArray = new JsonArray();
//						int idTranslationCnt = 0;
//						for (int j = i + 1; j < rawDataList.size(); j++) {
//							String nextTranslationKeyLine = rawDataList.get(j);
//							if (Arrays.asList(TOKEN_ARRAY).contains(nextTranslationKeyLine)) {
//								i = j - 1;
//								currentVerseObject.add(key, jsonTranslationArray);
//								break;
//							} else {
//								String[] tranArr = new String[2];
//								if (nextTranslationKeyLine.startsWith("BG ")) {
//									tranArr[0] = nextTranslationKeyLine.substring(0, nextTranslationKeyLine.indexOf(":"));
//									tranArr[1] = nextTranslationKeyLine.substring(tranArr[0].length() + 1);
//								} else {
//									tranArr[0] = "";
//									tranArr[1] = nextTranslationKeyLine;
//								}
//
//								int id = ++idTranslationCnt;
//								JsonObject obj = new JsonObject();
//								obj.addProperty("id", id);
//								obj.addProperty("header", tranArr[0]);
//								obj.addProperty("value", tranArr[1]);
//								jsonTranslationArray.add(obj);
//							}
//						}
//						break;
//
//					default:
//						break;
//
//					}
//				}
//			}
			
			

			PrintStream ps = new PrintStream(new File(chapterArrJsonFilePath));
			printJsonElement(chapterArr, ps);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static final int CURRENT = 0, NEXT = 1, PREVIOUS = -1;
	private static final List<String> TOKEN_LIST = Arrays.asList(TOKEN_ARRAY);

	private static void printJsonElement(JsonElement element, PrintStream printStreamObject) {
		Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
		printStreamObject.println(prettyJson.toJson(element));
	}

	private static JsonObject getVerse(JsonArray chapterArr, String chapterId, String verseId) {
		JsonObject obj = null;
		for (JsonElement ec : chapterArr) {
			JsonObject chapterObj = (JsonObject) ec;
			if (chapterObj.get("id").getAsString().equals(chapterId)) {
				for (JsonElement ev : (JsonArray) chapterObj.get("verses")) {
					JsonObject verseObj = (JsonObject) ev;
					if (verseObj.get("id").getAsString().equals(verseId)) {
						return verseObj;
					}
				}
			}
		}
		return obj;
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

	public static JsonArray getJsonArrayFromFile(String JSON_PATH) throws FileNotFoundException {
		JsonArray arr = new JsonArray();
		BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
		JsonParser parser = new JsonParser();
		arr = parser.parse(br).getAsJsonArray();
		return arr;
	}
}
