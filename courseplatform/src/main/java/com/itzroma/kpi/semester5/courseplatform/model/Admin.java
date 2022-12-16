package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"adminId"}, callSuper = true)
public class Admin extends User {
    private Long adminId;

    public Admin(
            Long userId, String firstName, String lastName, String email, String password,
            LocalDateTime registrationDate, Long adminId
    ) {
        super(userId, firstName, lastName, email, password, Role.ADMIN, registrationDate);
        this.adminId = adminId;
    }

    public Admin(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, Role.ADMIN);
    }

    @Override
    public Long getId() {
        return getAdminId();
    }

    @Override
    public void setId(Long id) {
        setAdminId(id);
    }

    @Override
    public String toString() {
        return "Admin: { %s, adminId: %d }".formatted(super.toString(), adminId);
    }
}
