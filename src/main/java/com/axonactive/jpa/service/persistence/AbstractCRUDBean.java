package com.axonactive.jpa.service.persistence;

import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractCRUDBean<T extends IEntity> {
    protected abstract PersistenceService<T> getPersistenceService();

    public List<T> findAll(){
        return this.getPersistenceService().findAll();
    }

    public T findById(Integer id){
        return this.getPersistenceService().findById(id);
    }

    public T save(T entity){
        return this.getPersistenceService().save(entity);
    }

    public void removeEntity(T entity){
        this.getPersistenceService().removeEntity(entity);
    }

    public void remove(Integer id) {
        this.getPersistenceService().remove(id);
    }

    public T update(T entity){
       return this.getPersistenceService().update(entity);
    }

    public TypedQuery<T> createTypeQuery(String query){return this.getPersistenceService().createTypeQuery(query);}



}
