package com.p.examples.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class JsonFormatter {

    public static void main(String[] args) {
        String sourceFilePath = "source.txt";
        String targetFilePath = "target.txt";

        File sourceDirectory = new File("D:\\notice\\seq");
        File[] children = sourceDirectory.listFiles();

        if (children != null) {
            for (File child : children) {
                if (child.isFile()) {
                    convertUglyJsonToPrettyJson(child.getAbsolutePath(), child.getAbsolutePath());
                }
            }
        }
    }

    public static void convertUglyJsonToPrettyJson(String sourceFilePath, String targetFilePath) {
        try {
            String uglyJSONString = readTxtFile(sourceFilePath);
            String prettyJsonString = prettyPrint(uglyJSONString);
            try (PrintStream ps = new PrintStream(new File(targetFilePath))) {
                ps.println(prettyJsonString);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String prettyPrint(String uglyJSONString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        return gson.toJson(je);
    }

    public static String readTxtFile(String fileName) throws FileNotFoundException {
        StringBuilder result = new StringBuilder();
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                result.append(sc.nextLine());
            }
        }
        return result.toString();
    }
}
