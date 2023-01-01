package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Answer implements Entity<Long> {
    private Long id;
    private String option;
    private Boolean correct;
    private Long questionId;
}
