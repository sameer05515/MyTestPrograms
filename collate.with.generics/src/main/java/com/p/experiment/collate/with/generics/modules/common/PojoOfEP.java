package com.p.experiment.collate.with.generics.modules.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PojoOfEP {
    private String uniqueId;
    private String parentId;
    private String name;
    private boolean isPrivate;
    private int rating;
    private String createdDate;
    private String updatedDate;
    private List<String> tags = new ArrayList<>();
}
