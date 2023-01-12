package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Unit;

import java.util.List;

public interface UnitDao extends CrudDao<Long, Unit> {
    boolean existsByTitleAndCourseId(String title, Long courseId) throws UnsuccessfulOperationException;
    List<Unit> findAllByCourseId(Long courseId) throws UnsuccessfulOperationException;
}
