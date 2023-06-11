package com.p.pers.smbg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

	private static final String[] TOKEN_ARRAY = { "===start===", "===shlok===", "===shlokEng===", "===meaning===",
			"===translation===", "===commentary===", "===end===" };
	private static final String SHLOK_IN_COMMENTARY = "$$shlok-commentary$$";

	public static void main(String[] args) {
		try {
			String chapterArrJsonFilePath = "D:\\Prem\\GIT-PROJ\\file-bckp\\smbg\\temp\\t.txt";
			String rawListFilePath = "D:\\Prem\\GIT-PROJ\\file-bckp\\smbg\\verseDetails\\temp.txt";
			JsonArray chapterArr = getJsonArrayFromFile(chapterArrJsonFilePath);
			List<String> rawDataList = getRawList(rawListFilePath);

			if (rawDataList != null && rawDataList.size() > 0) {
				JsonObject currentVerseObject = null;
				String currentKey = null;
				boolean hasMoreToken = true;
				int tokenTraversalIndex = 0;
				int rawListSize = rawDataList.size();
				while (hasMoreToken && tokenTraversalIndex < rawListSize) {
					int nextTokenIndex = getTokenIndex(rawDataList, tokenTraversalIndex);
					//System.out.printf("nextTokenIndex == %s rawListSize %s %n", nextTokenIndex, rawListSize);
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
						System.out.printf("==================== currentKey == %s%n", currentKey);

						int nextNonEmptyIndex = skipEmptyLines(rawDataList, tokenTraversalIndex);
						tokenTraversalIndex = nextNonEmptyIndex;
						JsonObject nextLineObject = getLineJson(rawDataList, tokenTraversalIndex, CURRENT);

						if (nextLineObject != null && nextLineObject.get("isToken").getAsBoolean()) {
							hasMoreToken = true;
							continue;
						} else {
							nextTokenIndex = getTokenIndex(rawDataList, tokenTraversalIndex);
							if ("start".equals(currentKey)) {
								// get id, current object, skip 2nd line , get next token
								String line = nextLineObject.get("line").getAsString();
								String[] idArr = line.split(",");
								//System.out.println("start == " + idArr[0] + " " + idArr[1]);
								currentVerseObject = getVerse(chapterArr, idArr[0], idArr[1]);
								tokenTraversalIndex = nextTokenIndex;
							} else if ("end".equals(currentKey)) {
								currentVerseObject = null;
								tokenTraversalIndex = nextTokenIndex;
							} else if ("shlok".equals(currentKey)) {
								JsonArray jsonArray = new JsonArray();
								int idCnt = 0;
								System.out.printf(
										"\n$$$\nstart of shlok : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								while (tokenTraversalIndex < nextTokenIndex) {
									String line = nextLineObject.get("line").getAsString();
									int id = ++idCnt;
									JsonObject obj = new JsonObject();
									obj.addProperty("id", id);
									obj.addProperty("value", line);
									jsonArray.add(obj);

									nextLineObject = getLineJson(rawDataList, tokenTraversalIndex++, NEXT);
								}
								currentVerseObject.add(key, jsonArray);
								System.out.printf("end of shlok : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								tokenTraversalIndex = nextTokenIndex;
							} else if ("shlokEng".equals(currentKey)) {
								JsonArray jsonArray = new JsonArray();
								int idCnt = 0;
								System.out.printf(
										"\n$$$\nstart of slokEng : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								while (tokenTraversalIndex < nextTokenIndex) {
									String line = nextLineObject.get("line").getAsString();
									int id = ++idCnt;
									JsonObject obj = new JsonObject();
									obj.addProperty("id", id);
									obj.addProperty("value", line);
									jsonArray.add(obj);

									nextLineObject = getLineJson(rawDataList, tokenTraversalIndex++, NEXT);
								}
								currentVerseObject.add(key, jsonArray);
								System.out.printf(
										"end of shlokEng : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								tokenTraversalIndex = nextTokenIndex;
							} else if ("meaning".equals(currentKey)) {
								JsonArray jsonArray = new JsonArray();
								int idCnt = 0;
								System.out.printf(
										"\n$$$\nstart of meaning : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								while (tokenTraversalIndex < nextTokenIndex) {
									String line = nextLineObject.get("line").getAsString();
									String[] meanings = line.split(";");
									for (String mean : meanings) {
										int id = ++idCnt;
										JsonObject obj = new JsonObject();
										obj.addProperty("id", id);
										//System.out.println(mean);
										obj.addProperty("sanskrit", mean.split("—")[0]);
										obj.addProperty("wordMeaningDetailUrl", "#/word-meaning/"+mean.split("—")[0].trim());
										///word-meaning/
										obj.addProperty("meaning", mean.split("—")[1]);
										jsonArray.add(obj);
									}
									nextLineObject = getLineJson(rawDataList, tokenTraversalIndex++, NEXT);
								}
								currentVerseObject.add(key, jsonArray);
								System.out.printf(
										"end of meaning : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								tokenTraversalIndex = nextTokenIndex;
							} else if ("translation".equals(currentKey)) {
								JsonArray jsonArray = new JsonArray();
								int idCnt = 0;
								System.out.printf(
										"\n$$$\nstart of translation : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								while (tokenTraversalIndex < nextTokenIndex) {
									String line = nextLineObject.get("line").getAsString();
									String[] tranArr = new String[2];
									if (line.startsWith("BG ")) {
										tranArr[0] = line.substring(0, line.indexOf(":"));
										tranArr[1] = line.substring(tranArr[0].length() + 1);
									} else {
										tranArr[0] = "";
										tranArr[1] = line;
									}

									int id = ++idCnt;
									JsonObject obj = new JsonObject();
									obj.addProperty("id", id);
									obj.addProperty("header", tranArr[0]);
									obj.addProperty("value", tranArr[1]);
									jsonArray.add(obj);
									nextLineObject = getLineJson(rawDataList, tokenTraversalIndex++, NEXT);
								}
								currentVerseObject.add(key, jsonArray);
								System.out.printf(
										"end of translation : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								tokenTraversalIndex = nextTokenIndex;
							} else if ("commentary".equals(currentKey)) {
								JsonArray jsonArray = new JsonArray();
								int idCnt = 0;
								System.out.printf(
										"\n$$$\nstart of commentary : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
								while (tokenTraversalIndex < nextTokenIndex) {
									String line = nextLineObject.get("line").getAsString();
									int id = ++idCnt;
									JsonObject obj = new JsonObject();
									obj.addProperty("id", id);

									boolean isShlokCommentary = (line != null && line.startsWith(SHLOK_IN_COMMENTARY));
									if (isShlokCommentary) {
										obj.addProperty("value", line.substring(SHLOK_IN_COMMENTARY.length()));
									} else {
										obj.addProperty("value", line);
									}
									obj.addProperty("isShlokCommentary", isShlokCommentary);

									jsonArray.add(obj);

									nextLineObject = getLineJson(rawDataList, tokenTraversalIndex++, NEXT);
								}
								currentVerseObject.add(key, jsonArray);
								System.out.printf(
										"end of commentary : tokenTraversalIndex == %s : nextTokenIndex == %s %n",
										tokenTraversalIndex, nextTokenIndex);
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

			PrintStream ps = new PrintStream(new File(chapterArrJsonFilePath));
			printJsonElement(chapterArr, ps);

			JsonArray wordMeaningArr = getWordMeaningArray(chapterArr);
			printJsonElement(wordMeaningArr, new PrintStream(new File(
					"D:\\Prem\\GIT-PROJ\\file-bckp\\smbg\\temp\\wm.txt")));
			printJsonElement(wordMeaningArr, new PrintStream(new File(
					"D:\\Prem\\GIT-PROJ\\MyTestPrograms\\ShrimadBhagwatGeeta\\src\\main\\webapp\\data\\json\\chapter-verse-word-meaning-temp.json")));
			printJsonElement(wordMeaningArr, new PrintStream(new File(
					"D:\\Prem\\CUST-INST\\apache-tomcat-8.5.59\\webapps\\ShrimadBhagwatGeeta\\data\\json\\chapter-verse-word-meaning-temp.json")));

			// D:\Prem\GIT-PROJ\MyTestPrograms\ShrimadBhagwatGeeta\src\main\webapp\data\json\chapter-verse-detail-temp.json
			// D:\Prem\CUST-INST\apache-tomcat-8.5.59\webapps\ShrimadBhagwatGeeta\data\json\chapter-verse-detail-temp.json
			printJsonElement(chapterArr, new PrintStream(new File(
					"D:\\Prem\\GIT-PROJ\\MyTestPrograms\\ShrimadBhagwatGeeta\\src\\main\\webapp\\data\\json\\chapter-verse-detail-temp.json")));
			printJsonElement(chapterArr, new PrintStream(new File(
					"D:\\Prem\\CUST-INST\\apache-tomcat-8.5.59\\webapps\\ShrimadBhagwatGeeta\\data\\json\\chapter-verse-detail-temp.json")));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static JsonArray getWordMeaningArray(JsonArray chapterArr) {
		JsonArray wmArr = new JsonArray();
		HashMap<String, JsonArray> wmMap = new HashMap<>();		
		for (JsonElement ec : chapterArr) {
			JsonObject chapterObj = (JsonObject) ec;
			String chapterId = chapterObj.get("id").getAsString().trim();
			for (JsonElement ev : (JsonArray) chapterObj.get("verses")) {
				JsonObject verseObj = (JsonObject) ev;
				String verseHeader = verseObj.get("verseHeader").getAsString().trim();
				String verseId = verseObj.get("id").getAsString().trim();
				for (JsonElement evm : (JsonArray) verseObj.get("meaning")) {
					JsonObject meaningObj = (JsonObject) evm;
					String sanskrit = meaningObj.get("sanskrit").getAsString().trim();
					String meaning = meaningObj.get("meaning").getAsString().trim();
					JsonArray jm = null;
					if (!wmMap.containsKey(sanskrit)) {
						jm = new JsonArray();
					} else {
						jm = wmMap.get(sanskrit);
					}
					JsonObject mObj = new JsonObject();
					mObj.addProperty("word", sanskrit);
					mObj.addProperty("meaning", meaning);
					mObj.addProperty("referenceHeader", verseHeader);
					mObj.addProperty("refLink", "#/chapter/" + chapterId + "/verse/" + verseId);
					jm.add(mObj);
					wmMap.put(sanskrit, jm);
				}
			}
		}
		
		Set<String> keys=wmMap.keySet();
		List<String> lkeys=new LinkedList<String>(keys);
		Collections.sort(lkeys);
		int lkeysSize=lkeys.size();
		for(int i=0;i<lkeysSize;i++) {
			String currkey=lkeys.get(i);
			String prevkey=lkeys.get((i-1+lkeysSize)%lkeysSize);
			String nextkey=lkeys.get((i+1+lkeysSize)%lkeysSize);
			JsonObject mObj = new JsonObject();
			mObj.addProperty("id", currkey);
			mObj.add("data", wmMap.get(currkey));
			mObj.addProperty("previousWMUrl", "#/word-meaning/"+prevkey);
			mObj.addProperty("nextWMUrl", "#/word-meaning/"+nextkey);
			mObj.addProperty("currentWMUrl", "#/word-meaning/"+currkey);
			wmArr.add(mObj);
		}
		
		return wmArr;
	}

	private static final int CURRENT = 0;
	private static final int NEXT = 1;
	@SuppressWarnings("unused")
	private static final int PREVIOUS = -1;
	private static final List<String> TOKEN_LIST = Arrays.asList(TOKEN_ARRAY);

	private static void printJsonElement(JsonElement element, PrintStream printStreamObject) {
		Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
		printStreamObject.println(prettyJson.toJson(element));
		//printStreamObject.close();
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

	@SuppressWarnings("deprecation")
	public static JsonArray getJsonArrayFromFile(String JSON_PATH) throws FileNotFoundException {
		JsonArray arr = new JsonArray();
		BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
		JsonParser parser = new JsonParser();
		arr = parser.parse(br).getAsJsonArray();
		return arr;
	}
}
