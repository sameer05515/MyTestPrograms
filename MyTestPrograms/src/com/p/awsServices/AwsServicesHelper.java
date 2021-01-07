package com.p.awsServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AwsServicesHelper {

	
	private static final String[] TOKEN_ARRAY = { "===start===",  "===end===" };
	private static String rawFileLocation="/home/premendra/Downloads/apache-tomcat-8.5.40/webapps/my-pages/other-sample-application/aws-services/js/data/raw-services-overview.txt";
	
	private static final int CURRENT = 0;
	private static final int NEXT = 1;
	@SuppressWarnings("unused")
	private static final int PREVIOUS = -1;
	private static final List<String> TOKEN_LIST = Arrays.asList(TOKEN_ARRAY);
	
	
	public static void main(String[] args) {
		
		try {
			List<String> rawDataList = getRawList(rawFileLocation);
			
			if (rawDataList != null && rawDataList.size() > 0) {
				for(String line:rawDataList) {
					System.out.println(line);
				}
				

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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}

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
