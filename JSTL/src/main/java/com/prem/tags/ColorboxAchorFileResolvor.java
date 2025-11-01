package com.prem.tags;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class ColorboxAchorFileResolvor extends TagSupport {

    private String base;
    private String traverseSubFolder;
    private String allowedExtentions;
    private String classname;
    private String baseURLPrefix;

    private Set<String> allowedExtensions = new HashSet<>();

    @Override
    public int doStartTag() throws JspException {
        initialiseAllowedExtensions();
        boolean traverseDirectories = !Boolean.FALSE.toString().equalsIgnoreCase(traverseSubFolder);

        try {
            JspWriter out = pageContext.getOut();
            String markup = createFileElement(new File(base), true, traverseDirectories);
            out.println(markup);
        } catch (IOException ex) {
            throw new JspException("Failed to render Colorbox anchors", ex);
        }

        return SKIP_BODY;
    }

    private void initialiseAllowedExtensions() {
        allowedExtensions = new HashSet<>();
        if (allowedExtentions == null || allowedExtentions.isBlank()) {
            return;
        }
        String[] exts = allowedExtentions.trim().toLowerCase().split(",");
        for (String ext : exts) {
            if (ext != null && !ext.isBlank()) {
                allowedExtensions.add(ext.trim());
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
                builder.append("\n<p>")
                        .append("<a class=\"")
                        .append(classname)
                        .append("\" title=\"")
                        .append(candidate.getName())
                        .append("\" href=\"")
                        .append(resolveUrl(candidate))
                        .append("\">")
                        .append(candidate.getName())
                        .append("</a></p>");
            }
        } else if (candidate.isDirectory() && traverse) {
            File[] children = candidate.listFiles();
            if (children != null && children.length > 0) {
                for (File child : children) {
                    String element = createFileElement(child, traverseSubTree, traverseSubTree);
                    if (element != null) {
                        builder.append("\n").append(element);
                    }
                }
            }
        }
        return builder.toString();
    }

    private String resolveUrl(File file) {
        String absolutePath = file.getAbsolutePath().replace("\\", "/");
        String prefix = baseURLPrefix != null ? baseURLPrefix : "";
        return prefix + absolutePath;
    }

    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "NA";
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    private boolean validExtension(String extension) {
        if (allowedExtensions.isEmpty()) {
            return true;
        }
        return allowedExtensions.contains(extension);
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

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public void setBaseURLPrefix(String baseURLPrefix) {
        this.baseURLPrefix = baseURLPrefix;
    }
}

