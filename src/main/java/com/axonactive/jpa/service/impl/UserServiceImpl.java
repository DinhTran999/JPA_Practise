package com.axonactive.jpa.service.impl;

import com.axonactive.jpa.entities.User;
import com.axonactive.jpa.service.persistence.AbstractCRUDBean;
import com.axonactive.jpa.service.persistence.PersistenceService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Transactional
@RequestScoped
public class UserServiceImpl extends AbstractCRUDBean<User> {

    @Inject
    PersistenceService<User> persistenceService;

    @Override
    protected PersistenceService<User> getPersistenceService() {
        return persistenceService;
    }

    public User findUserByNameAndPassword(String name,String password) {
        TypedQuery<User> typeQuery = createTypeQuery("from User u where u.name=:name and u.password=:password");
        typeQuery.setParameter("name",name);
        typeQuery.setParameter("password",password);
//        String query = "from User u where u.name='"+name+"' and u.password='"+password+"'";
        try {
            return typeQuery.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }

}
