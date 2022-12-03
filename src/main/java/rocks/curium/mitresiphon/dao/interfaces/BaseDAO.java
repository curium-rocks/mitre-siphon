package rocks.curium.mitresiphon.dao.interfaces;

public interface BaseDAO<T> {
    void save(T obj);
}
