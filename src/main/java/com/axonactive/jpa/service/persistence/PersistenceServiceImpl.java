package com.axonactive.jpa.service.persistence;

import javax.ejb.Stateful;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

@Stateful
public class PersistenceServiceImpl<T extends IEntity> implements PersistenceService<T> {
    @PersistenceContext(name = "jpa")
    protected EntityManager em;

    private Class<T> persistenceClass;

    @Override
    public void setPersistenceClass(Class<T> persistenceClass) {
        this.persistenceClass = persistenceClass;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getPersistentClass() {
        if (this.persistenceClass == null) {
            this.persistenceClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }
        return this.persistenceClass;
    }

    @SuppressWarnings("unchecked")
    private Class<T> resolveEntity(InjectionPoint ip) {
        ParameterizedType type = (ParameterizedType) ip.getType();
        Type[] typeArgs = type.getActualTypeArguments();
        return (Class<T>) typeArgs[0];
    }

    @Inject
    public void inject(InjectionPoint ip) {
        try {
            this.persistenceClass = resolveEntity(ip);
        } catch (Exception e) {
            throw new IllegalArgumentException("Entity class at injection point is invalid", e);
        }
    }

    @Override
    public T save(T entity) {
        this.em.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        this.em.merge(entity);
        return entity;
    }

    @Override
    public void removeEntity(T entity) {
        this.em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public void remove(Integer id) {
        T entity = findById(id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    @Override
    public List<T> findAll() {
        String className = getPersistentClass().getSimpleName();
        return em.createQuery("from "+className,getPersistentClass()).getResultList();
    }

    @Override
    public T findById(Integer id) {
        return em.find(getPersistentClass(), id);
    }

    @Override
    public TypedQuery<T> createTypeQuery(String query) {
        return this.em.createQuery(query,getPersistentClass());
    }

}
