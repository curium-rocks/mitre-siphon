package db.migration;

import db.migration.helpers.SimpleExecutionMigration;

/**
 * Enable the case insensitive text extension on the postgresql server if not already enable
 */
public class V0_02__EnableCITextExtension extends SimpleExecutionMigration {
    private static final String UP_SQL = """
       CREATE EXTENSION IF NOT EXISTS  "citext";
    """;
    @Override
    public String getSql() {
        return UP_SQL;
    }
}
