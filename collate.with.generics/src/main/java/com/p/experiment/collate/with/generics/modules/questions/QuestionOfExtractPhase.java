package com.p.experiment.collate.with.generics.modules.questions;

import com.p.experiment.collate.with.generics.modules.common.PojoOfEP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionOfExtractPhase  extends PojoOfEP {
    private String heading;
}
