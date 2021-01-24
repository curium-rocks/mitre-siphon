package db.migration.helpers;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

/**
 * Simple helper to reduce replication in query execution in migrations
 */
public abstract class SimpleExecutionMigration extends BaseJavaMigration {
    private final boolean throwOnFalseReturn;


    /**
     * Creates a simple execution migration object with
     * the throw behavior on false return set to true
     */
    protected SimpleExecutionMigration(){
        throwOnFalseReturn = true;
    }

    /**
     * Creates a simple execution migration with the
     * throw behavior on false return specified as a parameter
     * @param throwOnFalse {boolean} whether the migration should throw and fail if
     *                     the query execution method returns false
     */
    protected SimpleExecutionMigration(boolean throwOnFalse){
        throwOnFalseReturn = throwOnFalse;
    }

    /**
     * @return {String} representing the SQL statement to execute for the migration
     */
    public abstract String getSql();

    /**
     *
     * @param context {Context} migration context that this migration will be applied
     * @throws Exception {Exception} throws if the query is malformed, and depending on configuration, if
     *  the execute method returns false
     */
    public void migrate(Context context) throws Exception {
        try (Statement statement = context.getConnection().createStatement()) {
            if(!statement.execute(getSql()) && throwOnFalseReturn){
                throw new Exception("Failed to execute migration");
            }
        }
    }
}
