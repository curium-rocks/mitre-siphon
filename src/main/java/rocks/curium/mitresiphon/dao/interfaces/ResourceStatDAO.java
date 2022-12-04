package rocks.curium.mitresiphon.dao.interfaces;

import java.time.ZonedDateTime;
import rocks.curium.mitresiphon.entities.ResourceStat;

public interface ResourceStatDAO extends BaseDAO<ResourceStat> {
  ZonedDateTime getLastAccessTimeForResource(String resourceId);

  void recordAccessTime(String resourceId, ZonedDateTime accessTime);

  ResourceStat getResourceStat(String resourceId);
}
