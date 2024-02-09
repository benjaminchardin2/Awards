package com.bencha.db;

import com.bencha.services.configuration.ConfigurationService;
import org.flywaydb.core.Flyway;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

/**
 * Initialize the database with SQL scripts placed in src/main/resources/db/migration
 */
@Singleton
public class DatabaseInitializer {

	private final DataSource dataSource;
    private final String locationMigration;

	@Inject
	public DatabaseInitializer(DataSource dataSource, ConfigurationService configurationService) {
		this.dataSource = dataSource;
        this.locationMigration = configurationService.getMigrationFolder();
	}

	public void setup() {
		Flyway
			.configure()
            .locations(locationMigration)
			.dataSource(dataSource)
			.outOfOrder(true)
			.load()
            // use repair() when working locally on a migration file
            // => this enables Flyway to accept the changes made in the migration file
			.migrate();
	}
}
