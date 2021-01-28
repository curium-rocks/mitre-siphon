package xyz.andrewkboyd.mitresiphon.dao.interfaces;

import org.springframework.transaction.annotation.Transactional;
import xyz.andrewkboyd.mitresiphon.entities.CVE;
import xyz.andrewkboyd.mitresiphon.entities.ResourceStat;

import java.time.ZonedDateTime;

public interface ResourceStatDAO extends BaseDAO<ResourceStat> {
    ZonedDateTime getLastAccessTimeForResource(String resourceId);
    void recordAccessTime(String resourceId, ZonedDateTime accessTime);
    ResourceStat getResourceStat(String resourceId);
}
