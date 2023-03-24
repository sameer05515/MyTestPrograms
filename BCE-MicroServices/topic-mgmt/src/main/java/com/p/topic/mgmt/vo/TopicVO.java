package com.p.topic.mgmt.vo;

import com.p.topic.mgmt.pojo.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicVO {
    private int id;
    private String title;
    private String uniqueStrid;
    private String description;
    private Date dateCreated;
    private Date dateLastModified;
    private Date dateLastRead;
    private boolean personal;
    private int rating;

    public static TopicVO map(Topic topic){
        TopicVO topicVO=new TopicVO();
        topicVO.setId(topic.getId());
        topicVO.setTitle(topic.getTitle());
        return topicVO;
    }
}
