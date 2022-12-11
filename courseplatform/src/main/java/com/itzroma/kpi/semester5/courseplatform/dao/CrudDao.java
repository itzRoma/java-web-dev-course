package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Entity;

import java.util.List;

public interface CrudDao<ID, T extends Entity<ID>> extends AbstractDao<ID, T> {
    // C - create
    T create(T entity) throws UnsuccessfulOperationException;

    // R - read (one)
    T findById(ID id) throws UnsuccessfulOperationException;

    // R - read (all)
    List<T> findAll() throws UnsuccessfulOperationException;

    // U - update
    T update(T target, T source) throws UnsuccessfulOperationException;

    // D - delete
    void delete(T entity) throws UnsuccessfulOperationException;
}
