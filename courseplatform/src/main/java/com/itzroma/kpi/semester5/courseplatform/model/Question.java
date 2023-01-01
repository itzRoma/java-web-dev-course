package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Question implements Entity<Long> {
    private Long id;
    private String question;
    private Integer grade;
    private Long testId;
    private Set<Answer> answers;
}
