package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"studentId"}, callSuper = true)
public class Student extends User {
    private Long studentId;
    private Boolean blocked = false;

    public Student(
            Long userId, String firstName, String lastName, String email, String password,
            LocalDateTime registrationDate, Long studentId, Boolean blocked
    ) {
        super(userId, firstName, lastName, email, password, Role.STUDENT, registrationDate);
        this.studentId = studentId;
        this.blocked = blocked;
    }

    public Student(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, Role.STUDENT);
    }

    @Override
    public Long getId() {
        return getStudentId();
    }

    @Override
    public void setId(Long id) {
        setStudentId(id);
    }

    @Override
    public String toString() {
        return "Student: { %s, studentId: %d, blocked: %b }".formatted(super.toString(), studentId, blocked);
    }
}
