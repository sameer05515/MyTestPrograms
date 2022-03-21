package com.p.consume.rest;


import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    private static String URI_USERS_ID = "https://api.github.com/users/sameer05515/repos?per_page=100&page=";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            Gson gson = new Gson();
            JsonArray finalArr = new JsonArray();
            JsonParser parser = new JsonParser();
            Gson prettyJson = new GsonBuilder().setPrettyPrinting().create();

            for (int i = 1; i <= 3; i++) {
                String userStr = restTemplate.getForObject(URI_USERS_ID + i, String.class);
                JsonArray temp = parser.parse(userStr).getAsJsonArray();
                for (JsonElement el : temp) {
                    JsonObject c = (JsonObject) el;
                    JsonObject obj = new JsonObject();
                    obj.addProperty("id", getStringValue(c, "id"));
                    obj.addProperty("name", getStringValue(c, "name"));
                    obj.addProperty("description", getStringValue(c, "description"));
                    obj.addProperty("language", getStringValue(c, "language"));
                    obj.addProperty("html_url", getStringValue(c, "html_url"));
                    finalArr.add(obj);
                }
            }
//            System.out.println(prettyJson.toJson(finalArr));
//            System.out.println(finalArr.size());

            createHtml(finalArr);

        };
    }

    private void createHtml(JsonArray finalArr){
        try(PrintStream ps=new PrintStream(new File("C:\\Users\\premendra\\Desktop\\aa.txt"))){
            ps.println(HTML_PREFIX);

            int count=1;
            for(JsonElement el:finalArr){
                JsonObject c = (JsonObject) el;
                ps.println("                    <tr>\n" +
                        "                        <td style=\"width: 5%;\"> 1</td>\n" +
                        "                        <td style=\"width: 25%;\"> \n" +
                        "                            <a href=\""+getStringValue(c,"html_url")+"\" target=\"_blank\">\n" +
                        "                                "+getStringValue(c,"name")+"\n" +
                        "                            </a>\n" +
                        "                        </td>\n" +
                        "                        <td style=\"width: 45%;\"> "+ getStringValue(c,"description")+" </td>\n" +
                        "                        <td style=\"width: 25%;\"> "+getStringValue(c,"language")+" </td>\n" +
                        "                    </tr>");
            }
            ps.println(HTML_SUFFIX);
        }catch (Exception e){
            e.printStackTrace();
        }finally {}
    }

    private String getStringValue(JsonObject obj, String key) {
        String val = "";
        JsonElement element = obj.get(key);
        if (obj != null && key != null && element != null && !element.isJsonNull()) {
            val = obj.get(key).getAsString();
        }
        return val;
    }

    private final String HTML_PREFIX="<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>My Resume</title>\n" +
            "    <link rel=\"stylesheet\" href=\"lib/style.css\">\n" +
            "    <script src=\"lib/jquery-3.6.0.js\"></script>\n" +
            "    \n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "       <div>\n" +
            "            <table style=\"border-collapse: collapse; width: 100%;\" border=\"1\" id=\"mytable\">\n" +
            "                <thead>\n" +
            "                    <tr>\n" +
            "                        <th style=\"width: 5%;\" id=\"sl\">#</th>\n" +
            "                        <th style=\"width: 25%;\" id=\"comp\"><b>Name</b></th>\n" +
            "                        <th style=\"width: 45%;\"><b>Description</b></th>\n" +
            "                        <th style=\"width: 25%;\"><b>Language</b></th>\n" +
            "                    </tr>\n" +
            "                </thead>\n" +
            "\n" +
            "                <tbody>";

    private final String HTML_SUFFIX="                </tbody>\n </table>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "    <div>\n" +
            "        <pre>\n" +
            "            to get all repos in github below is API url\n" +
            "            <code>https://api.github.com/users/sameer05515/repos?per_page=100&page=3</code>\n" +
            "        </pre>\n" +
            "    </div>   <script>\n" +
            "        $(function () {\n" +
            "            var includes = $('[data-include]')\n" +
            "            $.each(includes, function () {\n" +
            "                var file = 'header-footer/' + $(this).data('include') + '.html'\n" +
            "                $(this).load(file)\n" +
            "            })\n" +
            "        })\n" +
            "    </script>\n" +
            "    <script src=\"lib/table-sort.js\"></script>\n" +
            "    <script src=\"lib/custom.js\"></script>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "</html>\n ";
}
