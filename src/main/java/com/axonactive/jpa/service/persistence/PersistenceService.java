package com.axonactive.jpa.service.persistence;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public interface PersistenceService<T> {
    List<T> findAll();
    T findById(Integer id);
    T save (T entity);
    void removeEntity(T entity);
    void remove(Integer id);
    T update (T entity);
    TypedQuery<T> createTypeQuery(String query);
    EntityManager getEntityManager();
    void setPersistenceClass(Class<T> persistenceClass);


}
