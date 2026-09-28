package com.p.consume.rest.flux;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.util.unit.DataSize;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Value("${app.github.user}")
    private String githubUser;

    @Value("${app.github.max-page:3}")
    private int maxPage;

    @Value("${app.output.html}")
    private String outputHtmlPath;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public WebClient webClient(
            WebClient.Builder builder,
            @Value("${spring.codec.max-in-memory-size:10MB}") DataSize maxInMemory) {
        int maxBytes = (int) Math.min(maxInMemory.toBytes(), Integer.MAX_VALUE - 8);
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(maxBytes))
                .build();
        return builder
                .exchangeStrategies(strategies)
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "consumer.rest.flux")
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "app.run-on-startup", havingValue = "true", matchIfMissing = true)
    public CommandLineRunner run(WebClient webClient) {
        return args -> fetchAndWriteHtml(webClient);
    }

    private void fetchAndWriteHtml(WebClient webClient) {
        Flux.range(1, maxPage)
                .concatMap(page -> fetchPage(webClient, page))
                .collectList()
                .map(this::mergeRepoArrays)
                .doOnNext(arr -> log.info("Merged {} repositories", arr.size()))
                .doOnNext(this::createHtml)
                .block();
    }

    private Mono<String> fetchPage(WebClient webClient, int page) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users/{user}/repos")
                        .queryParam("per_page", 100)
                        .queryParam("page", page)
                        .build(githubUser))
                .retrieve()
                .bodyToMono(String.class);
    }

    private JsonArray mergeRepoArrays(java.util.List<String> jsonPages) {
        JsonArray finalArr = new JsonArray();
        for (String userStr : jsonPages) {
            JsonArray temp = JsonParser.parseString(userStr).getAsJsonArray();
            for (JsonElement el : temp) {
                JsonObject c = el.getAsJsonObject();
                JsonObject obj = new JsonObject();
                obj.addProperty("id", getStringValue(c, "id"));
                obj.addProperty("name", getStringValue(c, "name"));
                obj.addProperty("description", getStringValue(c, "description"));
                obj.addProperty("language", getStringValue(c, "language"));
                obj.addProperty("html_url", getStringValue(c, "html_url"));
                finalArr.add(obj);
            }
        }
        return finalArr;
    }

    private void createHtml(JsonArray finalArr) {
        Path out = Path.of(outputHtmlPath);
        try {
            if (out.getParent() != null) {
                Files.createDirectories(out.getParent());
            }
        } catch (Exception e) {
            log.warn("Could not create parent directories for {}: {}", out, e.getMessage());
        }

        try (PrintStream ps = new PrintStream(new File(out.toFile().getAbsolutePath()))) {
            ps.println(HTML_PREFIX);

            int count = 1;
            for (JsonElement el : finalArr) {
                JsonObject c = el.getAsJsonObject();
                ps.println("                    <tr>\n" +
                        "                        <td style=\"width: 5%;\"> " + count++ + "</td>\n" +
                        "                        <td style=\"width: 25%;\"> \n" +
                        "                            <a href=\"" + getStringValue(c, "html_url") + "\" target=\"_blank\">\n" +
                        "                                " + getStringValue(c, "name") + "\n" +
                        "                            </a>\n" +
                        "                        </td>\n" +
                        "                        <td style=\"width: 45%;\"> " + getStringValue(c, "description") + " </td>\n" +
                        "                        <td style=\"width: 25%;\"> " + getStringValue(c, "language") + " </td>\n" +
                        "                    </tr>");
            }
            ps.println(HTML_SUFFIX);
            log.info("Wrote HTML to {}", out.toAbsolutePath());
        } catch (Exception e) {
            log.error("Failed to write HTML", e);
        }
    }

    private String getStringValue(JsonObject obj, String key) {
        if (obj == null || key == null) {
            return "";
        }
        JsonElement element = obj.get(key);
        if (element == null || element.isJsonNull()) {
            return "";
        }
        if (element.isJsonPrimitive()) {
            var p = element.getAsJsonPrimitive();
            if (p.isString()) {
                return p.getAsString();
            }
            if (p.isNumber()) {
                return p.getAsNumber().toString();
            }
            if (p.isBoolean()) {
                return Boolean.toString(p.getAsBoolean());
            }
        }
        return element.toString();
    }

    private final String HTML_PREFIX = "<!DOCTYPE html>\n" +
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

    private final String HTML_SUFFIX = "                </tbody>\n </table>\n" +
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
