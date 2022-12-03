package rocks.curium.mitresiphon.dao;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;
import rocks.curium.mitresiphon.dao.interfaces.ResourceStatDAO;
import rocks.curium.mitresiphon.entities.ResourceStat;

import java.time.ZonedDateTime;

public class ResourceStatDAOImpl extends BaseDaoImpl<ResourceStat> implements ResourceStatDAO {

    @Transactional
    public ZonedDateTime getLastAccessTimeForResource(String resourceId) {
        Session session= this.getSession();
        ResourceStat stat = session.find(ResourceStat.class, resourceId);
        if(stat != null) return stat.getLastAccessed();
        return null;
    }

    @Transactional
    public void recordAccessTime(String resourceId, ZonedDateTime accessTime) {
        ResourceStat stat = getSession().find(ResourceStat.class, resourceId);
        if(stat == null) stat = new ResourceStat();
        stat.setResource(resourceId);
        stat.setLastAccessed(accessTime);
        this.save(stat);
    }

    @Transactional
    public ResourceStat getResourceStat(String resourceId) {
        Session session= this.getSession();
        return session.find(ResourceStat.class, resourceId);
    }
}
