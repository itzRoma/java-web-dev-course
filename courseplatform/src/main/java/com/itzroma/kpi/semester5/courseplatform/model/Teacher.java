package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"teacherId"}, callSuper = true)
public class Teacher extends User {
    private Long teacherId;

    public Teacher(
            Long userId, String firstName, String lastName, String email, String password,
            LocalDateTime registrationDate, Long teacherId
    ) {
        super(userId, firstName, lastName, email, password, Role.TEACHER, registrationDate);
        this.teacherId = teacherId;
    }

    public Teacher(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, Role.TEACHER);
    }

    @Override
    public Long getId() {
        return getTeacherId();
    }

    @Override
    public void setId(Long id) {
        setTeacherId(id);
    }

    @Override
    public String toString() {
        return "Teacher: { %s, teacherId: %d }".formatted(super.toString(), teacherId);
    }
}
