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

public class TestJsonObject {
	public static void main(String[] args) {
		try {
			List<String> rawDataList = getRawList(
					"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\verseDetails\\verseDetails.txt");

			JsonArray chapterArr = getJsonArrayFromFile(
					"D:\\Prem\\GIT-PROJ\\MyTestPrograms\\ShrimadBhagwatGeeta\\src\\main\\webapp\\data\\json\\chapter-verse-detail.json");
			JsonObject currentVerseObject = null;
			for (int i = 0; i < rawDataList.size(); i++) {
				String line = rawDataList.get(i);
				if (Arrays.asList(TOKEN_ARRAY).contains(line)) {
					String key = line.replaceAll("=", "");
					System.out.printf("====================%s%n", key);
					switch (key) {
					case "start":
						line = rawDataList.get(++i);
						String[] idArr = line.split(",");
						System.out.println("start == " + idArr[0] + " " + idArr[1]);
						currentVerseObject = getVerse(chapterArr, idArr[0], idArr[1]);
						break;
					case "end":
//						currentTokenIndex = 0;
//						nextTokenIndex = 1;
						printJsonElement(currentVerseObject, System.out);
						break;
					case "shlok":
					case "shlokEng":
					case "commentary":
						JsonArray jsonArray = new JsonArray();
						int idCnt = 0;
						for (int j = i + 1; j < rawDataList.size(); j++) {
							line = rawDataList.get(j);
							if (Arrays.asList(TOKEN_ARRAY).contains(line)) {
								i = j - 1;
								// currentTokenIndex = nextTokenIndex;
								// System.out.printf("\"%s\" : %s,%n ", key, gson.toJson(jsonArray));
								// if (!Arrays.asList("start").contains(key)) {
								currentVerseObject.add(key, jsonArray);
								// }
								// System.out.print("\n\n");
								break;
							} else {
								int id = ++idCnt;
								JsonObject obj = new JsonObject();
								obj.addProperty("id", id);
								obj.addProperty("value", line);
								jsonArray.add(obj);
							}
						}
						break;
					case "meaning":
						JsonArray jsonMeaningArray = new JsonArray();
						int idMeaningCnt = 0;
						for (int j = i + 1; j < rawDataList.size(); j++) {
							line = rawDataList.get(j);
							if (Arrays.asList(TOKEN_ARRAY).contains(line)) {
								i = j - 1;
								// currentTokenIndex = nextTokenIndex;
								// System.out.printf("\"%s\" : %s,%n ", key, gson.toJson(jsonArray));
								// if (!Arrays.asList("start").contains(key)) {
								currentVerseObject.add(key, jsonMeaningArray);
								// }
								// System.out.print("\n\n");
								break;
							}else {
								String[] meanings = line.split(";");
								for (String mean : meanings) {
									int id = ++idMeaningCnt;
									JsonObject obj = new JsonObject();
									obj.addProperty("id", id);
									obj.addProperty("sanskrit", mean.split("—")[0]);
									obj.addProperty("meaning", mean.split("—")[1]);
									jsonMeaningArray.add(obj);
								}
							}
						}
						break;
					case "translation":
						JsonArray jsonTranslationArray = new JsonArray();
						int idTranslationCnt = 0;
						for (int j = i + 1; j < rawDataList.size(); j++) {
							line = rawDataList.get(j);
							if (Arrays.asList(TOKEN_ARRAY).contains(line)) {
								i = j - 1;
								currentVerseObject.add(key, jsonTranslationArray);
								break;
							}else {
								String[] tranArr = new String[2];
								if (line.startsWith("BG ")) {
									tranArr[0] = line.substring(0, line.indexOf(":"));
									tranArr[1] = line.substring(tranArr[0].length() + 1);
								} else {
									tranArr[0] = "";
									tranArr[1] = line;
								}

								int id = ++idTranslationCnt;
								JsonObject obj = new JsonObject();
								obj.addProperty("id", id);
								obj.addProperty("header", tranArr[0]);
								obj.addProperty("value", tranArr[1]);
								jsonTranslationArray.add(obj);
							}
						}
					
					default:
						break;

					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String[] TOKEN_ARRAY = { "===start===", "===shlok===", "===shlokEng===", "===meaning===",
			"===translation===", "===commentary===", "===end===" };

	private static void printJsonElement(JsonElement element, PrintStream printStreamObject) {
		Gson prettyJson = new GsonBuilder()/* .setPrettyPrinting() */.create();
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
