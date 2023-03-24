package com.p.topic.mgmt.rest;

import com.p.topic.mgmt.pojo.Topic;
import com.p.topic.mgmt.repository.TopicRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/streaming")
public class TestController {

    @Autowired
    TopicRepository topicRepository;

    @GetMapping(path = "/update-unique-ids", produces = MediaType.APPLICATION_XML_VALUE)
    public StreamingResponseBody handleRequest() {

        return new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream out) throws IOException {
//                for (int i = 0; i < 1000; i++) {
//                    out.write((Integer.toString(i) + " - ")
//                                        .getBytes());
//                    out.flush();
//                    try {
//                        Thread.sleep(5);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                int page = 0;
                int size = 10;
                Page<Topic> topicPage = topicRepository.findAll(PageRequest.of(page, size));
                boolean hasNext = topicPage != null && topicPage.hasNext();

                while (hasNext) {
                    StringJoiner sj = new StringJoiner(", \n", "", "<br/>");
                    topicPage.get().forEach(t->{
                        if(t.getUniqueStrid()==null||t.getUniqueStrid().trim().isEmpty()){
                            t.setUniqueStrid(generateUniqueId());
                        }
                    });
                    List<Topic> list= new ArrayList<>();
                    topicRepository.saveAll(topicPage.toList()).forEach(list::add);
                    list.stream().map(t -> t.getId() + ": " + t.getTitle() + ": uniqueid: " + t.getUniqueStrid()).collect(Collectors.toList()).forEach(i -> sj.add(i));
//                    topicPage.toList().stream().map(t -> t.getId() + ": " + t.getTitle() + ": uniqueid: " + t.getUniqueStrid()).collect(Collectors.toList()).forEach(i -> sj.add(i));
                    out.write((sj.toString().getBytes()));
                    out.flush();
                    topicPage = topicRepository.findAll(PageRequest.of(++page, size));
                    hasNext = topicPage != null && topicPage.hasNext();
                }
            }
        };
    }

    private String generateUniqueId() {
        return RandomStringUtils.randomAlphabetic(20);
    }
}