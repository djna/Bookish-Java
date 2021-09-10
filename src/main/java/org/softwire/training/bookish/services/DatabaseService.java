package org.softwire.training.bookish.services;

import org.jdbi.v3.core.Jdbi;

import java.util.Properties;

public abstract class DatabaseService {

    private final String hostname = "localhost";
    private final String database = System.getenv("DB_NAME");
    private final String user = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASS");
    private final String connectionString = "jdbc:mysql://" + hostname + "/" + database;

    private Properties makeConnectiohPropertions() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", password);
        connectionProps.setProperty("useSSL", "false");
        connectionProps.setProperty("useJDBCCompliantTimezoneShift", "true");
        connectionProps.setProperty("useLegacyDatetimeCode", "false");
        connectionProps.setProperty("serverTimezone=GMT&useSSL", "false");
        return connectionProps;
    }

    protected final Jdbi jdbi = Jdbi.create(connectionString, makeConnectiohPropertions());
}
