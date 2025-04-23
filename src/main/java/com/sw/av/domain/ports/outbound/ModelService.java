package com.sw.av.domain.ports.outbound;

import com.sw.av.domain.exceptions.DAOException;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;


public interface ModelService<E,T>
{

    List<E> findAll();

    E getById(T id);

    E save(E entity);

    void deleteById(T id);
    
    EntityManager getEntityManager();
    
    List<E> getResultList(String sql, Map<String,Object> params) throws DAOException;
    
    E getSingleResult(String sql, Map<String,Object> params) throws DAOException;
    
}
