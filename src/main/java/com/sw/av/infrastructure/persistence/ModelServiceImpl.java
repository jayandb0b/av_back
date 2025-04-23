package com.sw.av.infrastructure.persistence;

import com.sw.av.domain.exceptions.DAOException;
import com.sw.av.domain.ports.outbound.ModelService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

@Slf4j
public class ModelServiceImpl<E,T,DAO extends CrudRepository> implements ModelService<E,T>, Serializable
{
    //private final Logger LOG = LoggerFactory.getLogger(AlbalaModelServiceImpl.class);
    private Class<E> clazz;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DAO dao;

    public E create() {
        try {
            if(clazz!=null){
            log.info("CREATING ENTITY " + clazz.getCanonicalName());
            Constructor<E> constructor = clazz.getDeclaredConstructor();
            log.info("MAKE ACCESIBLE " + constructor);
            constructor.setAccessible(true);
            return constructor.newInstance();
            }
            return null;
        } catch (Exception e) {
            log.error("🔥 🔥 🚒 ERROR CommonModelServiceImpl.create :", e);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List findAll() {
        List<E> list;

        list = (List<E>) dao.findAll();

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public E getById(T id) {
        try {
            if (id != null) {
                return (E) dao.findById(id).orElse(create());
            }
            return create();
        } catch (Exception e) {
            log.error("Error CommonModelServiceImpl.getById ",e);
            //log.info("Creating Entity Instance");
            return create();
        }
    }

    @Override
    public E save(E entity) {
        return (E) dao.save(entity);
    }

    @Override
    public void deleteById(T id) {

    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<E> getResultList(String sql, Map<String, Object> params) throws DAOException {

        List<E> result = new ArrayList<>();

        if (sql != null && !sql.equals("")) {
            Query jpqlQuery = getEntityManager().createQuery(sql);

            if (params != null) {
                params.forEach((k, v) -> jpqlQuery.setParameter(k, v));
            }

            result = jpqlQuery.getResultList();
        }

        return result;
    }

    @Override
    public E getSingleResult(String sql, Map<String, Object> params) throws DAOException {

        E object = null;

        if (sql != null && !sql.equals("")) {
            Query jpqlQuery = getEntityManager().createQuery(sql);

            if (params != null) {
                params.forEach((k, v) -> jpqlQuery.setParameter(k, v));
            }

            object = (E) jpqlQuery.getSingleResult();
        }

        return object;
    }

    public List<Sort.Order> getOrderList(String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getDirection(sort[1]), sort[0]));
        }
        
        return orders;
    }

    public Sort.Direction getDirection(String direction) {
        if (direction != null) {
            return direction.contains("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        }
        return null;
    }

}
