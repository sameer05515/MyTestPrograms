
package com.p.pers.smbg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ReadFileLineByLineUsingScanner {

	public static void main(String[] args) {

		List<Integer> chapterNos = new ArrayList<>();
		for (int i = 1; i <= 18; i++) {
			chapterNos.add(i);
		}
		for (int i : chapterNos) {
			createJson(i);
		}

		/////////////////////////
		// int chapterNo = 1;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try {
			JsonArray chapterArr = getJsonArrayFromFile(
					"D:\\Prem\\GIT-PROJ\\MyTestPrograms\\ShrimadBhagwatGeeta\\src\\main\\webapp\\data\\json\\chapter-verse-summary.json");

			List<String> chapterIds = new ArrayList<>();
			List<String> verseIds = new ArrayList<>();

			for (int chapterIndex = 0; chapterIndex < chapterArr.size(); chapterIndex++) {
				chapterIds.add(((JsonObject) chapterArr.get(chapterIndex)).get("id").getAsString());
				JsonArray verseArr = getJsonArrayFromFile(
						"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\json\\ch" + (chapterIndex + 1) + ".json");
				for (int verseIndex = 0; verseIndex < verseArr.size(); verseIndex++) {
					verseIds.add(((JsonObject) verseArr.get(verseIndex)).get("id").getAsString());
				}
			}

			String prevChapter = "";
			String nextChapter = "";
			String currentChapter = "";

			String currentVerse = "";
			String nextVerse = "";
			String prevVerse = "";
			int verseIdIndex = 0;
			int verseIdsSize = verseIds.size();

			for (int chapterIndex = 0, chapterArrSize = chapterArr
					.size(); chapterIndex < chapterArrSize; chapterIndex++) {

				JsonObject currChapObj = (JsonObject) chapterArr
						.get(Math.floorMod(chapterIndex + chapterArrSize, chapterArrSize));
//				JsonObject nextChapObj = (JsonObject) chapterArr.get((chapterIndex + 1 + chapterArr.size()) % chapterArr.size());
//				JsonObject prevChapObj = (JsonObject) chapterArr.get((chapterIndex - 1 + chapterArr.size()) % chapterArr.size());

//				currentChapter = currChapObj.get("id").getAsString();
//				nextChapter = nextChapObj.get("id").getAsString();
//				prevChapter = prevChapObj.get("id").getAsString();

				currentChapter = chapterIds.get(Math.floorMod(chapterIndex + chapterArrSize, chapterArrSize));
				nextChapter = chapterIds.get(Math.floorMod(chapterIndex + 1 + chapterArrSize, chapterArrSize));
				;
				prevChapter = chapterIds.get(Math.floorMod(chapterIndex - 1 + chapterArrSize, chapterArrSize));
				;

				currChapObj.addProperty("currentChapter", currentChapter);
				currChapObj.addProperty("nextChapter", nextChapter);
				currChapObj.addProperty("previousChapter", prevChapter);

				currChapObj.addProperty("currentChapterUrl", "#/chapter/" + currentChapter);
				currChapObj.addProperty("nextChapterUrl", "#/chapter/" + nextChapter);
				currChapObj.addProperty("previousChapterUrl", "#/chapter/" + prevChapter);

				System.out.println("========================================");
				System.out.printf("currentChapter == %s \t nextChapter == %s \t prevChapter == %s%n", currentChapter,
						nextChapter, prevChapter);

				// int chapNo = currChapObj.get("id").getAsInt();

				JsonArray verseArr = getJsonArrayFromFile(
						"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\json\\ch" + (chapterIndex + 1) + ".json");
				for (int verseIndex = 0, verseArrSize = verseArr.size(); verseIndex < verseArr.size(); verseIndex++) {
					JsonObject currVerseObj = (JsonObject) verseArr
							.get(Math.floorMod(verseIndex + verseArrSize, verseArrSize));
//					JsonObject nextVerseObj = (JsonObject) verseArr.get((verseIndex + 1 + verseArr.size()) % verseArr.size());
//					JsonObject prevVerseObj = (JsonObject) verseArr.get((verseIndex - 1 + verseArr.size()) % verseArr.size());

//					String currentVerse = currVerseObj.get("id").getAsString();
//					String nextVerse = nextVerseObj.get("id").getAsString();
//					String prevVerse = prevVerseObj.get("id").getAsString();

					currentVerse = verseIds.get(Math.floorMod(verseIdIndex + verseIdsSize, verseIdsSize));
					nextVerse = verseIds.get(Math.floorMod(verseIdIndex + 1 + verseIdsSize, verseIdsSize));
					prevVerse = verseIds.get(Math.floorMod(verseIdIndex - 1 + verseIdsSize, verseIdsSize));

					currVerseObj.addProperty("currentVerse", currentVerse);
					currVerseObj.addProperty("nextVerse", nextVerse);
					currVerseObj.addProperty("previousVerse", prevVerse);
					
					currVerseObj.add("translation", new JsonArray());
					currVerseObj.add("commentary", new JsonArray());
					currVerseObj.add("shlok", new JsonArray());
					currVerseObj.add("shlokEng", new JsonArray());
					currVerseObj.add("meaning", new JsonArray());
					

					currVerseObj.addProperty("currentVerseUrl",
							"#/chapter/" + currentChapter + "/verse/" + currentVerse);
					currVerseObj.addProperty("nextVerseUrl",
							"#/chapter/" + ((verseIndex == (verseArrSize - 1)) ? nextChapter : currentChapter)
									+ "/verse/" + nextVerse);
					currVerseObj.addProperty("previousVerseUrl",
							"#/chapter/" + (verseIndex == 0 ? prevChapter : currentChapter) + "/verse/" + prevVerse);

					// int verseNo = currVerseObj.get("id").getAsInt();
					System.out.printf("currentVerse == %s \t nextVerse == %s \t prevVerse == %s%n", currentVerse,
							nextVerse, prevVerse);
					verseArr.set(verseIndex, currVerseObj);
					verseIdIndex++;
				}
				currChapObj.add("verses", verseArr);

				chapterArr.set(chapterIndex, currChapObj);

			}

			PrintStream ps = new PrintStream(new File(
					"D:\\Prem\\GIT-PROJ\\MyTestPrograms\\ShrimadBhagwatGeeta\\src\\main\\webapp\\data\\json\\chapter-verse-detail.json"));
			ps.print(gson.toJson(chapterArr));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		/////////////////////

//		try {
//			getTraversalArray("C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\json\\chapter-verse-summary.json");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

//	private static JsonElement getElementFromJsonArray(JsonArray arr,int index) {
//		return arr.
//	}

	public static JsonArray getTraversalArray(String path) throws FileNotFoundException {
		JsonArray result = new JsonArray();

		JsonArray chapterArr = getJsonArrayFromFile(path);
		String prevChapter = "";
		String nextChapter = "";
		String currChapter = "";
		for (int i = 0; i < chapterArr.size(); i++) {
			JsonObject currChapObj = (JsonObject) chapterArr.get((i + chapterArr.size()) % (chapterArr.size()));
			JsonObject nextChapObj = (JsonObject) chapterArr.get((i + 1 + chapterArr.size()) % chapterArr.size());
			JsonObject prevChapObj = (JsonObject) chapterArr.get((i - 1 + chapterArr.size()) % chapterArr.size());

			currChapter = currChapObj.get("id").getAsString();
			nextChapter = nextChapObj.get("id").getAsString();
			prevChapter = prevChapObj.get("id").getAsString();

//			System.out.print("currChapter == " + currChapter);
//			System.out.print("   nextChapter == " + nextChapter);
//			System.out.print("   prevChapter == " + prevChapter);
			// System.out.println();
			System.out.printf("currChapter == %s \t nextChapter == %s \t prevChapter == %s%n", currChapter, nextChapter,
					prevChapter);

		}
//		for (JsonElement e : chapterArr) {
//			if(e instanceof JsonObject) {
//				JsonObject obj=(JsonObject)e;
//				String chapNo=obj.get("id").getAsString();
//				System.out.println("chapter == "+chapNo);
//				
//			}
//		}

		return result;
	}

	public static void createJson(int chapterNo) {

		try {
			Scanner scanner = new Scanner(
					new File("C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\ch" + chapterNo + ".txt"));

			JsonArray arr = new JsonArray();

			List<String> data = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				data.add(line);
				// System.out.println(line);
			}
			scanner.close();

			// int count = 0;
			for (int i = 0; i < data.size();) {
				String d = "";
				JsonObject obj = new JsonObject();
				if (i % 3 == 0 && i < data.size()) {
					d = data.get(i++);
					obj.addProperty("verseHeader", d);
					obj.addProperty("id", d.substring("Bhagavad Gita ".length()));
				}
				if (i % 3 == 1 && i < data.size()) {
					d = data.get(i++);
					obj.addProperty("oneLiner", d);
				}
				if (i % 3 == 2 && i < data.size()) {
					// d = data.get(i++);
					i++;
				}
				arr.add(obj);
			}

			PrintStream ps = new PrintStream(
					new File("C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\json\\ch" + chapterNo + ".json"));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			ps.print(gson.toJson(arr));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static JsonArray getJsonArrayFromFile(String JSON_PATH) throws FileNotFoundException {
		JsonArray arr = new JsonArray();
		BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
		JsonParser parser = new JsonParser();
		arr = parser.parse(br).getAsJsonArray();
		return arr;
	}

}
