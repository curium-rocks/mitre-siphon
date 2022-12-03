package rocks.curium.mitresiphon.dao.interfaces;

import rocks.curium.mitresiphon.entities.ResourceStat;

import java.time.ZonedDateTime;

public interface ResourceStatDAO extends BaseDAO<ResourceStat> {
    ZonedDateTime getLastAccessTimeForResource(String resourceId);
    void recordAccessTime(String resourceId, ZonedDateTime accessTime);
    ResourceStat getResourceStat(String resourceId);
}
