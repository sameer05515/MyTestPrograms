package com.p.mongo.curd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stream")
public class StreamingController {

    @GetMapping("/getFile")
    @CrossOrigin
    public StreamingResponseBody download(@RequestParam("documentId") String documentId,
                                          final HttpServletRequest request,
                                          final HttpServletResponse response) {
        System.out.println("documentId : " + documentId);
        return out -> {
            try {
                if (documentId != null && documentId.trim().length() > 0) {
                    File file = new File(documentId);
                    if (!file.exists()) {
                        throw new IOException("File not exists!");
                    }
                    if (file.isDirectory()) {
                        File[] children = file.listFiles();

                        int count = ((children != null) ? children.length : 0);
                        StringBuffer sb = new StringBuffer();
                        sb.append("<b>Total file found : - " + count + "</b>");
                        sb.append("<ul>");
                        for (File child : children) {
                            sb.append("<li>" + "<a href=\"?documentId=" + child.getAbsolutePath().trim().replace("\\", "/") + "\" >" + child.getName() + "</a>" + "</li>");
                        }
                        sb.append("</ul>");
                        out.write(sb.toString().getBytes());
                        return;
                    }
                    response.setHeader("Content-Type", request.getServletContext().getMimeType(file.getName()));
                    response.setHeader("Content-Length", String.valueOf(file.length()));
                    response.setHeader("Content-Disposition", "inline; filename=\"" +
                            file.getName() +
                            "\"");
                    Files.copy(file.toPath(), out);
                    System.out.println(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
                out.write(("No File found : " + documentId).getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @GetMapping("/testAsyncSupported")
    public StreamingResponseBody handleRequest1(HttpServletRequest r) {
        System.out.println("asyncSupported: " + r.isAsyncSupported());
        System.out.println(Thread.currentThread().getName());

        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                System.out.println(Thread.currentThread().getName());
                outputStream.write("from test request".getBytes());
            }
        };
    }

    @GetMapping("/testSendMap")
    public StreamingResponseBody handleRequest2() {
        return outputStream -> {
            Map<String, BigInteger> map = new HashMap<>();
            map.put("one", BigInteger.ONE);
            map.put("ten", BigInteger.TEN);
            try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
                oos.writeObject(map);
            }
        };
    }

    @GetMapping("/sendResponseEntity")
    public ResponseEntity<StreamingResponseBody> handleRequest() {

        StreamingResponseBody responseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream out) throws IOException {
                for (int i = 0; i < 1000000000; i++) {
                    out.write((i + " - ")
                            .getBytes());
                    out.flush();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        return new ResponseEntity(responseBody, HttpStatus.OK);
    }
}
