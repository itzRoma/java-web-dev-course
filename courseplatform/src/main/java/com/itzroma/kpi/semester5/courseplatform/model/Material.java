package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"materialId"})
public abstract class Material implements Entity<Long> {
    private Long materialId;
    private String title;
    private String description;
    private Long moduleId;

    @Override
    public Long getId() {
        return getMaterialId();
    }

    @Override
    public void setId(Long id) {
        setMaterialId(id);
    }

    @Override
    public String toString() {
        return "Material: { id: %d, title: %s, description: %s, moduleId: %d }"
                .formatted(materialId, title, description.substring(0, 10), moduleId);
    }
}
