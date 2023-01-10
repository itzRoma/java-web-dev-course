package com.itzroma.kpi.semester5.courseplatform.service;

import com.itzroma.kpi.semester5.courseplatform.exception.entity.EntityNotFoundException;
import com.itzroma.kpi.semester5.courseplatform.exception.service.ServiceException;
import com.itzroma.kpi.semester5.courseplatform.model.Theme;

import java.util.List;

public interface ThemeService {
    Theme create(Theme course);

    List<Theme> findMany(int quantity) throws ServiceException;

    List<Theme> findAll() throws ServiceException;

    Theme findById(Long id) throws EntityNotFoundException, ServiceException;

    Theme findByName(String name) throws EntityNotFoundException, ServiceException;

    Theme update(Theme target, Theme source) throws ServiceException;

    void delete(Theme course) throws ServiceException;
}
