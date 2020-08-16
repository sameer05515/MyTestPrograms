package com.p.xml_to_json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.p.xml_to_json.pojo.Word;

public class ConvertXMLToJSONArrayTest {
	public static String xmlString = "<?xml version=\"1.0\" ?>\r\n" + "<root>\r\n"
			+ "    <test attrib=\"jsontext1\">tutorialspoint</test>\r\n"
			+ "    <test attrib=\"jsontext2\">tutorix</test>\r\n" + "</root>";
	private static int wordIdCounter=1;

	public static String readFileAsString(String fileName) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(fileName)));
		return data;
	}

	public static void usingFileOutputStream(String fileContent, String filePath) throws IOException {
		// String fileContent = "Hello Learner !! Welcome to howtodoinjava.com.";

		Path path = Paths.get(filePath);
		Files.write(path, fileContent.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	public static void convertXMLToJson() throws Exception {
		System.out.println(readFileAsString(
				"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\db-files\\db-bckp-27-sep-2019\\khajana.xml"));
		try {
			JSONObject json = XML.toJSONObject(readFileAsString(
					"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\db-files\\db-bckp-27-sep-2019\\khajana.xml")); // converts
																												// xml
																												// to
																												// json
			String jsonPrettyPrintString = json.toString(4); // json pretty print
			System.out.println(jsonPrettyPrintString);

			usingFileOutputStream(jsonPrettyPrintString,
					"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\db-files\\db-bckp-27-sep-2019\\khajana.json");
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
	}
	
	public static void convertXMLToJsonAfterTransform() throws Exception {
		System.out.println(readFileAsString(
				"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\db-files\\db-bckp-27-sep-2019\\khajana.xml"));
		try {
			JSONObject json = XML.toJSONObject(readFileAsString(
					"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\db-files\\db-bckp-27-sep-2019\\khajana.xml")); // converts
																												// xml
																												// to
			JSONArray transformedJson=transform(json);		 																							// json
			String jsonPrettyPrintString = transformedJson.toString(4); // json pretty print
			System.out.println(jsonPrettyPrintString);

			usingFileOutputStream(jsonPrettyPrintString,
					"C:\\Users\\premendra.kumar\\Desktop\\DUMP\\db-files\\db-bckp-27-sep-2019\\khajana-transformed.json");
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
	}

	private static JSONArray transform(JSONObject json) {
		JSONArray transformedJson=new JSONArray();
		
		JSONObject vocabConfig=json.getJSONObject("vocab-config");
		JSONObject wordList=vocabConfig.getJSONObject("word-list");
		JSONArray mywordList=wordList.getJSONArray("myword");
		for(Object wrd:mywordList) {
			if(wrd instanceof JSONObject) {
				JSONObject word=(JSONObject)wrd;
				Word wrdPojo=new Word();
				
				JSONObject wordJSONObject=word.getJSONObject("word");
				System.out.println(wordJSONObject.toString());
				wrdPojo.setWord(wordJSONObject.getString("content"));
				wrdPojo.setType(wordJSONObject.getString("type"));
				
				JSONObject meanings=word.getJSONObject("meanings");
				JSONObject examples=word.getJSONObject("examples");
				
				List<String> meaningsStrList=new ArrayList<String>();
				List<String> examplesStrList=new ArrayList<String>();
				
				try {
					JSONArray meaning=meanings.getJSONArray("meaning");
					for(Object m:meaning) {
						meaningsStrList.add((String)m);
					}
				} catch (Exception e) {
					if(e instanceof JSONException) {
						String meaning=meanings.getString("meaning");
						meaningsStrList.add(meaning);						
					}
				}
				
				try {
					JSONArray example=examples.getJSONArray("example");
					for(Object m:example) {
						examplesStrList.add((String)m);
					}
				} catch (Exception e) {
					if(e instanceof JSONException) {
						String example=examples.getString("example");
						examplesStrList.add(example);						
					}
				}
				
				wrdPojo.setExamplesList(examplesStrList);
				wrdPojo.setMeaningsList(meaningsStrList);
				
				JSONObject trnsWrd=new JSONObject();
				trnsWrd.put("word", wrdPojo.getWord());
				trnsWrd.put("type", wrdPojo.getType());
				
				//trnsWrd.put("examples", wrdPojo.getExamplesList());
				JSONArray trnsWrdExmp=new JSONArray();
				int count=1;
				for(String exmpl:wrdPojo.getExamplesList()) {
					JSONObject exmp=new JSONObject();
					exmp.put("id", count++);
					exmp.put("example", exmpl);
					trnsWrdExmp.put(exmp);
				}
				trnsWrd.put("examples", trnsWrdExmp);
				
				//trnsWrd.put("meanings", wrdPojo.getMeaningsList());
				JSONArray trnsWrdMngs=new JSONArray();
				count=1;
				for(String exmpl:wrdPojo.getMeaningsList()) {
					JSONObject exmp=new JSONObject();
					exmp.put("id", count++);
					exmp.put("meaning", exmpl);
					trnsWrdMngs.put(exmp);
				}
				trnsWrd.put("meanings", trnsWrdMngs);
				
				trnsWrd.put("id", wordIdCounter++);
				transformedJson.put(trnsWrd);
			}
			
		}
		
		
		return transformedJson;
	}

	public static void main(String[] args) throws Exception {
		convertXMLToJsonAfterTransform();
	}
}