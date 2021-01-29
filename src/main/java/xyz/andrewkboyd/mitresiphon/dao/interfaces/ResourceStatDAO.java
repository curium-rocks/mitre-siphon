package xyz.andrewkboyd.mitresiphon.dao.interfaces;

import xyz.andrewkboyd.mitresiphon.entities.ResourceStat;

import java.time.ZonedDateTime;

public interface ResourceStatDAO extends BaseDAO<ResourceStat> {
    ZonedDateTime getLastAccessTimeForResource(String resourceId);
    void recordAccessTime(String resourceId, ZonedDateTime accessTime);
    ResourceStat getResourceStat(String resourceId);
}
