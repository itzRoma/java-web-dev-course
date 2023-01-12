package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<Theme> themes;
    private List<Unit> units;

    public Course(String title, String description, Integer duration, LocalDateTime startingDate, List<Theme> themes) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.startingDate = startingDate;
        this.themes = themes;
    }
}
