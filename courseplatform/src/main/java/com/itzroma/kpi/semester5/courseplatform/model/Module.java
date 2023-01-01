package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Module implements Entity<Long> {
    private Long id;
    private String title;
    private Long unitId;
    private Set<Material> materials;
}
