package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"testId"}, callSuper = true)
public class Test extends Material {
    private Long testId;
    private Integer maxGrade;
    private Set<Question> questions;

    @Override
    public Long getId() {
        return getTestId();
    }

    @Override
    public void setId(Long id) {
        setTestId(id);
    }

    @Override
    public String toString() {
        return "Test: { %s, testId: %d, maxGrade: %d }".formatted(super.toString(), testId, maxGrade);
    }
}
