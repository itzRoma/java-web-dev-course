package com.itzroma.kpi.semester5.courseplatform.service;

import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityExistsException;
import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Unit;

import java.util.List;

public interface UnitService {
    Unit create(Unit unit) throws EntityExistsException, ServiceException;

    List<Unit> findAllByCourseId(Long courseId) throws ServiceException;

    Unit findById(Long id) throws EntityNotFoundException, ServiceException;

    Unit update(Unit target, Unit source) throws EntityExistsException, ServiceException;

    void delete(Unit unit) throws ServiceException;
}
