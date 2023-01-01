package com.itzroma.kpi.semester5.courseplatform.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"videoLectureId"}, callSuper = true)
public class VideoLecture extends Material {
    private Long videoLectureId;
    private String videoUrl;

    @Override
    public Long getId() {
        return getVideoLectureId();
    }

    @Override
    public void setId(Long id) {
        setVideoLectureId(id);
    }

    @Override
    public String toString() {
        return "VideoLecture: { %s, videoLectureId: %d, videoUrl: %s }"
                .formatted(super.toString(), videoLectureId, videoUrl);
    }
}
