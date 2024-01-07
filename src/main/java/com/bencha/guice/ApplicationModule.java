package com.bencha.guice;

import com.bencha.jersey.JerseyConfigProvider;
import com.coreoz.plume.admin.guice.GuiceAdminWsWithDefaultsModule;
import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.db.guice.DataSourceModule;
import com.coreoz.plume.db.querydsl.guice.GuiceQuerydslModule;
import com.coreoz.plume.jersey.monitoring.guice.GuiceJacksonWithMetricsModule;
import com.coreoz.plume.mail.guice.GuiceMailModule;
import com.coreoz.plume.scheduler.guice.GuiceSchedulerModule;
import com.google.inject.AbstractModule;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Group the Guice modules to install in the application
 */
public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new GuiceConfModule());
		install(new GuiceJacksonWithMetricsModule());
		// Database & Querydsl installation
        install(new GuiceQuerydslModule());
        // Data source exposure for Flyway
        install(new DataSourceModule());
        install(new GuiceAdminWsWithDefaultsModule());
        // Mail module
        install(new GuiceMailModule());

        // Scheduler
        install(new GuiceSchedulerModule());

        // Prepare Jersey configuration
		bind(ResourceConfig.class).toProvider(JerseyConfigProvider.class);
	}

}
