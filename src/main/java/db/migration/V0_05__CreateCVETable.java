package db.migration;

import db.migration.helpers.SimpleExecutionMigration;

public class V0_05__CreateCVETable extends SimpleExecutionMigration {
    private static final String UP_SQL = """
               CREATE TABLE IF NOT EXISTS cve (
                    id varchar(64) PRIMARY KEY,
                    "ref_url" varchar(512),
                    description text
                );
               CREATE INDEX idx_references ON cve("ref_url");
               CREATE INDEX idx_description ON cve(description);
            """;

    /**
     * Create test table in database
     */
    public V0_05__CreateCVETable(){
        super(false);
    }

    @Override
    public String getSql() {
        return UP_SQL;
    }
}
