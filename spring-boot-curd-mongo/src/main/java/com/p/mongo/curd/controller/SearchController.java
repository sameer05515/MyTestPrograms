package com.p.mongo.curd.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.p.mongo.curd.service.FileSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("file-search")
public class SearchController {

    @Autowired
    private FileSearchUtil fileSearchUtil;

    @GetMapping("/")
    @CrossOrigin
    public String search(@RequestParam("fileName") String fileName, @RequestParam(name = "extensions")List<String> extensions) {
        System.out.println("fileName : "+fileName+" | extensions : "+extensions);
        Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray data=fileSearchUtil.startSearch(fileName,extensions);
        return prettyJson.toJson(data);
    }

}
