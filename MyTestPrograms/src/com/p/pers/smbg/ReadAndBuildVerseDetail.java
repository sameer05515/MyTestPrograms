package com.p.pers.smbg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ReadAndBuildVerseDetail {

	private static String[] TOKEN_ARRAY = { "===shlok===", "===shlokEng===", "===meaning===", "===translation===",
			"===commentary===" };

	public static void main(String[] args) {
		try {
			Gson gson = new GsonBuilder().create();
			Scanner scanner = new Scanner(
					new File("C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\verseDetails\\verseDetails.txt"));
			int currentTokenIndex = 0;
			List<String> rawDataList = new ArrayList<>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				rawDataList.add(line);
			}
			scanner.close();
//			for(String ss:rawDataList) {
//				System.out.println(ss);
//			}

			for (int i = 0; i < rawDataList.size(); i++) {
				String line = rawDataList.get(i);
				// System.out.println(line);
				int idCnt = 0;
				if (line.equals(TOKEN_ARRAY[currentTokenIndex])) {
					String key = line.replaceAll("=", "");
					// System.out.printf("====================%s%n", key);

					JsonArray jsonArray = new JsonArray();
					for (int j = i + 1; j < rawDataList.size(); j++) {
						line = rawDataList.get(j);

						int nextTokenIndex = (currentTokenIndex + 1) % TOKEN_ARRAY.length;
						if (line.equals(TOKEN_ARRAY[nextTokenIndex]) || (j == rawDataList.size()-1)) {
							i = j - 1;
							currentTokenIndex = nextTokenIndex;

							System.out.printf("\"%s\" : %s,%n ", key, gson.toJson(jsonArray));
							// System.out.print("\n\n");
							break;
						} else {

							// System.out.printf("id = %s ,\t value= %s%n", id, line);
							if (key.equals("meaning")) {
								String[] meanings = line.split(";");
								for (String mean : meanings) {
									int id = ++idCnt;
									JsonObject obj = new JsonObject();
									obj.addProperty("id", id);
									obj.addProperty("sanskrit", mean.split("—")[0]);
									obj.addProperty("meaning", mean.split("—")[1]);
									jsonArray.add(obj);
								}
							} else if (key.equals("translation")) {
								String[] tranArr= new String[2];
								if(line.startsWith("BG ")) {
									tranArr[0]=line.substring(0, line.indexOf(":"));
									tranArr[1]=line.substring(tranArr[0].length()+1);
								}else {
									tranArr[0]="";
									tranArr[1]=line;
								}
								
								int id = ++idCnt;
								JsonObject obj = new JsonObject();
								obj.addProperty("id", id);
								obj.addProperty("header", tranArr[0]);
								obj.addProperty("value", tranArr[1]);
								jsonArray.add(obj);
//								for (int indexTrans : Arrays.asList(line.indexOf(":"), line.length() - 1)) {
//
//								}
							} else if(key.equals("end")){
								break;
							}
							else {
								int id = ++idCnt;
								JsonObject obj = new JsonObject();
								obj.addProperty("id", id);
								obj.addProperty("value", line);
								jsonArray.add(obj);
							}

						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void basicImpl() {

		try {
			String[] keyArr = { "shlok", "shlokEng", "meaning", "translation", "commentary" };
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Gson gson = new GsonBuilder().create();
			Scanner scanner = new Scanner(
					new File("C:\\Users\\premendra.kumar\\Desktop\\DUMP\\smbg\\verseDetails\\temp.txt"));
			JsonArray arr = new JsonArray();

			int count = 1;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				JsonObject obj = new JsonObject();
				obj.addProperty("id", count++);
				obj.addProperty("value", line);
				arr.add(obj);
			}
			scanner.close();

//			System.out.printf("\"%s\" : %s ",keyArr[0],gson.toJson(arr));
			List<String> list = new ArrayList<>();
			for (String s : keyArr) {
				// System.out.printf("===%s===%n",s);
				list.add(String.format("===%s===", s));
			}
			for (String s : list) {
				System.out.println(s);
			}
			System.out.println("\n\n\n");
			for (String s : list) {
				System.out.println(s.replaceAll("=", ""));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
