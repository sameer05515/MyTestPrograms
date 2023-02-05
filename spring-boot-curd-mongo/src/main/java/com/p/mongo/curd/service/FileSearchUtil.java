package com.p.mongo.curd.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

@Service
public class FileSearchUtil {

    public static void main(String[] args) {
        JsonArray data = new FileSearchUtil().startSearch("C:/Prem/data/others/self/comics", Arrays.asList(".pdf"));
        Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(prettyJson.toJson(data));
    }

    public JsonArray startSearch(String fileName, List<String> extList) {
        JsonArray fileList = new JsonArray();
        if (fileName != null) {
            searchRecursively(fileList, fileName, extList);
        }
        return  fileList;
    }

    private void searchRecursively(JsonArray fileList, String fileName, List<String> extensionList) {
        File f = new File(fileName);
        File[] children = f.listFiles(new MyFileFilter(extensionList));
        if (children != null && children.length > 0) {
            for (File child : children) {
                if (child.isDirectory()) {
                    searchRecursively(fileList, child.getAbsolutePath(), extensionList);
                }
                if (child.isFile()) {
                    JsonObject fileObj=new JsonObject();
                    fileObj.addProperty("name",child.getName());
                    fileObj.addProperty("filePath",child.getAbsolutePath().trim().replace("\\", "/"));
                    fileObj.addProperty("parentFile",fileName.trim().replace("\\", "/"));
                    fileList.add(fileObj);
                }
            }
        }
    }

    private static class MyFileFilter implements FileFilter {
        private List<String> allowedExtensions;
        public MyFileFilter(List<String> allowedExtensions) {
            this.allowedExtensions = allowedExtensions;
        }
        @Override
        public boolean accept(File file) {
            if (file == null) {
                return false;
            }
            if (!file.exists()) {
                return false;
            }
            if (file.isDirectory()) {
                return true;
            }
            String filename = (file.getName() != null) ? file.getName()
                    .trim().toLowerCase() : null;
            for (String ext : allowedExtensions) {
                if (filename != null && filename.endsWith(ext)) {
                    return true;
                }
            }
            return false;
        }
    }
}