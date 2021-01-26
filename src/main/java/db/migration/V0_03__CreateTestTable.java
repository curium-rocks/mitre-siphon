package db.migration;

import db.migration.helpers.SimpleExecutionMigration;

/**
 * Create the test table on the database
 */
public class V0_03__CreateTestTable extends SimpleExecutionMigration {
    private static final String UP_SQL = """
       CREATE TABLE IF NOT EXISTS test (
                   id uuid PRIMARY KEY default gen_random_uuid(),
                   title citext
               );
               CREATE INDEX idx_title ON test(title);
    """;

    /**
     * Create test table in database
     */
    public V0_03__CreateTestTable(){
        super(false);
    }

    @Override
    public String getSql() {
        return UP_SQL;
    }
}
