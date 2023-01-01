package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"assignmentId"}, callSuper = true)
public class Assignment extends Material {
    private Long assignmentId;
    private String question;
    private Integer maxGrade;

    @Override
    public Long getId() {
        return getAssignmentId();
    }

    @Override
    public void setId(Long id) {
        setAssignmentId(id);
    }

    @Override
    public String toString() {
        return "Assignment: { %s, assignmentId: %d, question: %s, maxGrade: %d }"
                .formatted(super.toString(), assignmentId, question.substring(0, 10), maxGrade);
    }
}
