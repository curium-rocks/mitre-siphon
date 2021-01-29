package xyz.andrewkboyd.mitresiphon.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public abstract class BaseDaoImpl<T> implements xyz.andrewkboyd.mitresiphon.dao.interfaces.BaseDAO<T> {

    @PersistenceContext
    private EntityManager entityManager;


    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public void save(T obj) {
        Session session = getSession();
        session.saveOrUpdate(obj);
    }
}
