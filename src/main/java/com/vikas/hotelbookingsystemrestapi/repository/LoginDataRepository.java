package com.vikas.hotelbookingsystemrestapi.repository;

import com.vikas.hotelbookingsystemrestapi.entity.LoginData;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Repository
public class LoginDataRepository{

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public boolean registerNewUser(String uname, String pass) {
        try {
            LoginData newUser = new LoginData(uname, pass);
            manager.persist(newUser);
            return true;
        }
        catch(Exception e) {
            return false;
        }
        
    }

    public LoginData getUserDetails(String username) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<LoginData> query = builder.createQuery(LoginData.class);
        Root<LoginData> root = query.from(LoginData.class);
        Predicate usernameMatch = builder.equal(root.get("username"), username);
       // Predicate passwordMatch = builder.equal(root.get("password"), password);
        query.select(root).where(usernameMatch);
        TypedQuery<LoginData> getUserdetailQuery = manager.createQuery(query);
        return getUserdetailQuery.getSingleResult();
    }
}
