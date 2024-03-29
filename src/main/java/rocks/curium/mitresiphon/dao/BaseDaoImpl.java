package rocks.curium.mitresiphon.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rocks.curium.mitresiphon.dao.interfaces.BaseDAO;

@Repository
public abstract class BaseDaoImpl<T> implements BaseDAO<T> {

  @PersistenceContext private EntityManager entityManager;

  protected Session getSession() {
    return entityManager.unwrap(Session.class);
  }

  @Transactional
  public void save(T obj) {
    Session session = getSession();
    session.saveOrUpdate(obj);
  }
}
