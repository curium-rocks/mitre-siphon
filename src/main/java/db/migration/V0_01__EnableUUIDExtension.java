package db.migration;

import db.migration.helpers.SimpleExecutionMigration;

/**
 * Enable the UUID extensions in the postgresql database if they aren't already
 */
public class V0_01__EnableUUIDExtension extends SimpleExecutionMigration {

    private static final String UP_SQL = """
       CREATE EXTENSION IF NOT EXISTS  "uuid-ossp";
    """;

    /**
     * Create a new instance of the migration and specify in the parent SimpleExecutionMigration
     * not to throw on false return of the execute method
     */
    public V0_01__EnableUUIDExtension(){
        super(false);
    }

    @Override
    public String getSql(){
        return UP_SQL;
    }
}
