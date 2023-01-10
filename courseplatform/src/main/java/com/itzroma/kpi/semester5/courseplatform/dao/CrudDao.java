package com.itzroma.kpi.semester5.courseplatform.dao;

import com.itzroma.kpi.semester5.courseplatform.exception.dao.UnsuccessfulOperationException;
import com.itzroma.kpi.semester5.courseplatform.model.Entity;

import java.util.List;
import java.util.Optional;


public interface CrudDao<ID, T extends Entity<ID>> extends AbstractDao {
    // C - create
    T create(T entity) throws UnsuccessfulOperationException;

    // R - read (one)
    Optional<T> findById(ID id) throws UnsuccessfulOperationException;

    List<T> findMany(int quantity) throws UnsuccessfulOperationException;

    // R - read (all)
    List<T> findAll() throws UnsuccessfulOperationException;

    // U - update
    T update(T target, T source) throws UnsuccessfulOperationException;

    // D - delete
    void delete(T entity) throws UnsuccessfulOperationException;
}
