package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"textLectureId"}, callSuper = true)
public class TextLecture extends Material {
    private Long textLectureId;
    private String fileUrl;

    @Override
    public Long getId() {
        return getTextLectureId();
    }

    @Override
    public void setId(Long id) {
        setTextLectureId(id);
    }

    @Override
    public String toString() {
        return "TextLecture: { %s, textLectureId: %d, fileUrl: %s }"
                .formatted(super.toString(), textLectureId, fileUrl);
    }
}
