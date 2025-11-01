package com.p.java.playground.api.streamingResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/streaming")
public class StreamingController {

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> streamData() {
        StreamingResponseBody responseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream outputStream) throws IOException {
                // Simulate streaming data, you can replace this with your data source
                for (int i = 0; i < 10; i++) {
                    String data = "Data " + i + "\n";
                    outputStream.write(data.getBytes());
                    outputStream.flush();
                    try {
                        Thread.sleep(100); // Simulate delay between data chunks
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                outputStream.close();
            }
        };

        return ResponseEntity.ok().body(responseBody);
    }
}