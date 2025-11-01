package com.example.jsptut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("demos", List.of(
                new DemoLink("/jstl/home", "JSTL Core Demo", "Looping, escaping, and URL generation with sample data.", null),
                new DemoLink("/xml/import", "JSTL XML Parsing", "Parses an XML document via JSTL's x:parse tag.", "Depends on /data/books.xml"),
                new DemoLink("/xml/transform", "XSLT Transformation", "Applies XSLT to inline and external XML sources.", null),
                new DemoLink("/xml/include", "JSP XML Element", "Demonstrates the jsp:element and jsp:body actions.", null),
                new DemoLink("/xml/response", "XML Response", "Renders a JSP that produces raw XML from the server.", "Sets content type to text/xml"),
                new DemoLink("/forms/name", "Session-backed Form", "Collects a name and stores it in the HTTP session.", null),
                new DemoLink("/custom/hello", "Custom Tag", "Invokes a simple custom tag defined via TLD.", null),
                new DemoLink("/samples/scriptlet", "Legacy Scriptlet", "Shows how older scriptlet-heavy pages can still run.", null)
        ));
        return "index";
    }

    public static class DemoLink {
        private final String path;
        private final String title;
        private final String description;
        private final String notes;

        public DemoLink(String path, String title, String description, String notes) {
            this.path = path;
            this.title = title;
            this.description = description;
            this.notes = notes;
        }

        public String getPath() {
            return path;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getNotes() {
            return notes;
        }
    }
}
