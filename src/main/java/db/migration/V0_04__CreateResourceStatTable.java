package db.migration;

import db.migration.helpers.SimpleExecutionMigration;

/** Create the test table on the database */
public class V0_04__CreateResourceStatTable extends SimpleExecutionMigration {
  private static final String UP_SQL =
      """
               CREATE TABLE IF NOT EXISTS resource_stats (
                    resource varchar(256) PRIMARY KEY,
                    last_accessed TIMESTAMPTZ
                );
            """;

  /** Create test table in database */
  public V0_04__CreateResourceStatTable() {
    super(false);
  }

  @Override
  public String getSql() {
    return UP_SQL;
  }
}
