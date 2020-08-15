package com.p.xml_to_json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class ConvertXMLToJSONArrayTest {
	public static String xmlString = "<?xml version=\"1.0\" ?>\r\n" + "<root>\r\n"
			+ "    <test attrib=\"jsontext1\">tutorialspoint</test>\r\n"
			+ "    <test attrib=\"jsontext2\">tutorix</test>\r\n" + "</root>";

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

	public static void main(String[] args) throws Exception {
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
}