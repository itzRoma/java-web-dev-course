package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeDao extends CrudDao<Long, Theme> {
    Optional<Theme> findByName(String name) throws UnsuccessfulOperationException;

    int getNumberOfUses(long themeId) throws UnsuccessfulOperationException;

    boolean existsByName(String name) throws UnsuccessfulOperationException;

    List<Theme> getByCourseId(Long courseId) throws UnsuccessfulOperationException;
}
