package com.p.mongo.curd.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Annotations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "word")
public class Word {

    /**
     * Attributes
     */
    @Id
    private String id;
    private String word;
}
