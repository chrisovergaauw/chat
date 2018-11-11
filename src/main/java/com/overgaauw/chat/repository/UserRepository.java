package com.overgaauw.chat.repository;

import com.overgaauw.chat.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

@Repository
public class UserRepository {
    private final EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Transactional
    public User createUser(String name) {
        final User user = new User(name);
        entityManager.persist(user);
        return user;
    }

    public User findUserByName(String name) {
        try {
            return entityManager.createNamedQuery("users.getUserByName", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findUserById(String id) {
        try {
            return entityManager.createNamedQuery("users.getUserById", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
