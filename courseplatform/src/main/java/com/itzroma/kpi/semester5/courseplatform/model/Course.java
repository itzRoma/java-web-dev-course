package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Course implements Entity<Long> {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private Integer minGrade;
    private Integer maxGrade;
    private LocalDateTime startingDate;
    private CourseStatus status;
    private Set<Theme> themes;
    private Set<Unit> units;

    public Course(String title, String description, Integer duration, LocalDateTime startingDate) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.startingDate = startingDate;
    }
}
