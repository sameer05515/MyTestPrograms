package com.prem.tags;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class FileResolvor extends TagSupport {

    private String base;
    private String traverseSubFolder;
    private String allowedExtentions;

    private Set<String> listAllowedExtensionsForIndexing = new HashSet<>();

    @Override
    public int doStartTag() throws JspException {
        initialiseAllowedExtensions();
        boolean traverseDirectories = !Boolean.FALSE.toString().equalsIgnoreCase(traverseSubFolder);

        try {
            JspWriter out = pageContext.getOut();
            String content = createFileElement(new File(base), true, traverseDirectories);
            out.println("<div class=\"homecontent\">");
            out.println("<h3 class=\"h3home\">" + base.toUpperCase() + " files</h3>");
            out.println("<br/>#####################################<br/>");
            out.println("The " + base + " folder has following files : <br/>" + content);
            out.println("base == " + base + "<br/>");
            out.println("traverseSubFolder == " + traverseSubFolder + "<br/>");
            out.println("allowedExtentions == " + allowedExtentions + "<br/>");
            out.println("<br/>#####################################<br/>");
            out.println("</div>");
        } catch (IOException ex) {
            throw new JspException("Failed to render file listing", ex);
        }

        return SKIP_BODY;
    }

    private void initialiseAllowedExtensions() {
        listAllowedExtensionsForIndexing = new HashSet<>();
        if (allowedExtentions == null || allowedExtentions.isBlank()) {
            return;
        }
        String[] exts = allowedExtentions.trim().toLowerCase().split(",");
        for (String ext : exts) {
            if (ext != null && !ext.isBlank()) {
                listAllowedExtensionsForIndexing.add(ext.trim());
            }
        }
    }

    private String createFileElement(File candidate, boolean traverse, boolean traverseSubTree) {
        StringBuilder builder = new StringBuilder();
        if (!candidate.exists()) {
            builder.append(candidate.getAbsolutePath()).append(" not exists");
            return builder.toString();
        }

        if (candidate.isFile()) {
            if (validExtension(getExtension(candidate.getName()))) {
                builder.append("\n<li>")
                        .append("<a class=\"fileClass\" href=\"")
                        .append(candidate.getAbsolutePath().replace("\"", "/"))
                        .append("\">")
                        .append(candidate.getName())
                        .append("</a></li>");
            }
        } else if (candidate.isDirectory() && traverse) {
            builder.append("\n<ul>")
                    .append("\n<li>")
                    .append("<a class=\"folderClass\" href=\"")
                    .append(candidate.getAbsolutePath().replace("\"", "/"))
                    .append("\">")
                    .append(candidate.getName())
                    .append("</a>");
            File[] children = candidate.listFiles();
            if (children != null && children.length > 0) {
                builder.append("\n<ul>");
                for (File child : children) {
                    String node = createFileElement(child, traverseSubTree, traverseSubTree);
                    if (node != null) {
                        builder.append("\n").append(node);
                    }
                }
                builder.append("\n</ul>");
            }
            builder.append("</li>")
                   .append("\n</ul>");
        }
        return builder.toString();
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "NA";
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private boolean validExtension(String extension) {
        if (listAllowedExtensionsForIndexing.isEmpty()) {
            return true;
        }
        return listAllowedExtensionsForIndexing.contains(extension);
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setTraverseSubFolder(String traverseSubFolder) {
        this.traverseSubFolder = traverseSubFolder;
    }

    public void setAllowedExtentions(String allowedExtentions) {
        this.allowedExtentions = allowedExtentions;
    }
}

