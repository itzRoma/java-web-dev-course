package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"userId", "email"})
public abstract class User implements Entity<Long> {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private LocalDateTime registrationDate = LocalDateTime.now();

    protected User(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public Long getId() {
        return getUserId();
    }

    @Override
    public void setId(Long id) {
        setUserId(id);
    }

    @Override
    public String toString() {
        return "User: { id: %d, firstName: %s, lastName: %s, email: %s, password: %s, role: %s, registrationDate: %s }"
                .formatted(userId, firstName, lastName, email, password, role, registrationDate);
    }
}
