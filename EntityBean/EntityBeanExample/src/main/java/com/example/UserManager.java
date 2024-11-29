package com.example;

import com.example.entities.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserManager {
    @PersistenceContext(unitName = "EntityBeanPU")
    private EntityManager entityManager;

    public void createUser(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        entityManager.persist(user);
    }

    public List<UserEntity> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity.class).getResultList();
    }
}
