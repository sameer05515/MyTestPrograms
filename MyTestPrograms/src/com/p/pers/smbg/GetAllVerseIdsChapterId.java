package com.p.pers.smbg;

import java.io.FileNotFoundException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class GetAllVerseIdsChapterId {

	public static void main(String[] args) throws FileNotFoundException {
		JsonArray chapterArr = TestJsonObject.getJsonArrayFromFile("C:\\Users\\premendra.kumar\\Desktop\\t.txt");
		
		getVerseids(chapterArr);
	}

	private static JsonObject getVerseids(JsonArray chapterArr) {
		JsonObject obj = null;
		for (JsonElement ec : chapterArr) {
			JsonObject chapterObj = (JsonObject) ec;
			String chapterId = chapterObj.get("id").getAsString();

			for (JsonElement ev : (JsonArray) chapterObj.get("verses")) {
				JsonObject verseObj = (JsonObject) ev;
				String verseId = verseObj.get("id").getAsString();
				System.out.printf("===start===%n%s,%s%n",chapterId,verseId);
				System.out.printf("===shlok===%n%n");
				System.out.printf("===shlokEng===%n%n");
				System.out.printf("===meaning===%n%n");
				System.out.printf("===translation===%n%n");
				System.out.printf("===commentary===%n%n");
				System.out.printf("===end===%n");
//				if (verseObj.get("id").getAsString().equals(verseId)) {
//					return verseObj;
//				}
			}

		}
		return obj;
	}

}
