package xyz.andrewkboyd.mitresiphon.entities;

import lombok.Data;
import org.hibernate.Session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(name = "resource_stats")
public @Data class ResourceStat {
    @Id
    @Column(name = "resource")
    private String resource;

    @Column(name = "last_accessed")
    private ZonedDateTime lastAccessed;

    public static void updateLastAccessedTime(String resourceId, ZonedDateTime time, Session dbSession){
        ResourceStat stat = dbSession.find(ResourceStat.class, resourceId);
        if(stat != null){
            stat.setLastAccessed(time);
            dbSession.update(stat);
        } else {
            ResourceStat newStat = new ResourceStat();
            newStat.setResource(resourceId);
            newStat.setLastAccessed(time);
            dbSession.save(newStat);
        }
    }
}
