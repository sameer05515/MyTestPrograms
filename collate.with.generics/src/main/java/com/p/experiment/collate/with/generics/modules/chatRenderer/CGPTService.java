package com.p.experiment.collate.with.generics.modules.chatRenderer;

import com.p.experiment.collate.with.generics.modules.tasks.TaskOfEP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CGPTService {
    @Autowired
    private RestTemplate restTemplate;

    public ChatGPTFile[] getAllChatGPTFileBasicInfo() {
        String url = "http://localhost:3003/cgpt/f";
        return restTemplate.getForObject(url, ChatGPTFile[].class);
    }

    public ChatGPTFile getChatGPTFileInfoWithConversationNames(String fileUID) {
        String url = "http://localhost:3003/cgpt/f/"+fileUID;
        return restTemplate.getForObject(url, ChatGPTFile.class);
    }
}
