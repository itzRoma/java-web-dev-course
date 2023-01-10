package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "name"})
public class Theme implements Entity<Long> {
    private Long id;
    private String name;

    // field that doesn't go to the database
    private int numberOfUses;

    public Theme(String name) {
        this.name = name;
    }
}
