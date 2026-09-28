package com.p.experiment.collate.with.generics.modules.tasks;

import com.p.experiment.collate.with.generics.modules.common.PojoOfEP;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskOfEP  extends PojoOfEP {
    private String taskStatus;
}
