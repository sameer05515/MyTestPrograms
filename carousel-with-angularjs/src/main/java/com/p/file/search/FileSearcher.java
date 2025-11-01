package com.p.file.search;

import java.io.File;
import java.io.FileFilter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

    List<String> fileList = new ArrayList<>();
    public List<String> extensionList = new ArrayList<>();

    /**
     * @param appBaseUrl scheme + host + port + context, e.g. {@code http://localhost:8080}
     */
    public String startSearch(String fileName, List<String> extnList, String appBaseUrl) {
        extensionList = extnList != null ? extnList : new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (fileName != null) {
            fileList = new ArrayList<>();
            serarch(fileList, fileName, appBaseUrl);

            System.out.println(fileName + "  " + fileList);
            for (int i = 0; i < fileList.size(); i++) {
                if (i == 0) {
                    sb.append("[");
                }

                sb.append(fileList.get(i));

                if (i == fileList.size() - 1) {
                    sb.append("]");
                } else {
                    sb.append(" , ");
                }
            }
        }

        return sb.toString();
    }

    private void serarch(List<String> fileList, String fileName, String appBaseUrl) {

        File f = new File(fileName);
        File[] children = f.listFiles(new MyFileFilter(extensionList));
        if (children == null) {
            return;
        }
        for (File child : children) {
            if (child.isDirectory()) {
                serarch(fileList, child.getAbsolutePath(), appBaseUrl);
            }
            if (child.isFile()) {

                String encoded = URLEncoder.encode(
                        child.getAbsolutePath().trim().replace("\\", "/"),
                        StandardCharsets.UTF_8);

                fileList.add("{\"name\":\"" + child.getName()
                        + "\" , \"filePath\":\"" + appBaseUrl
                        + "/my.jsp?documentId="
                        + encoded
                        + "\"}");

            }
        }

    }

    private static class MyFileFilter implements FileFilter {

        List<String> allowedExtentions = new ArrayList<>();

        public MyFileFilter(List<String> allowedExtentions) {
            this.allowedExtentions = allowedExtentions != null ? allowedExtentions : new ArrayList<>();
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

            String fileNameee = (file.getName() != null) ? file.getName()
                    .trim().toLowerCase() : null;

            for (String exts : allowedExtentions) {

                if (fileNameee != null && fileNameee.endsWith(exts)) {
                    return true;
                }
            }

            return false;
        }

    }

}
