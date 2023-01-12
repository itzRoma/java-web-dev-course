package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Unit implements Entity<Long> {
    private Long id;
    private String title;
    private Long courseId;
    private Set<Module> modules;

    public Unit(String title, Long courseId) {
        this.title = title;
        this.courseId = courseId;
    }
}
