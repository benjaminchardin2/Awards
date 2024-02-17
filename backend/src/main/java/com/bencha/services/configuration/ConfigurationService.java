package com.bencha.services.configuration;

import com.bencha.webservices.beans.FrontendConfiguration;
import com.typesafe.config.Config;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class ConfigurationService {
	private final Config config;

	@Inject
	public ConfigurationService(Config config) {
		this.config = config;
	}

	public String internalApiAuthUsername() {
		return config.getString("internal-api.auth-username");
	}

	public String internalApiAuthPassword() {
		return config.getString("internal-api.auth-password");
	}

    public Integer httpGrizzlyWorkerThreadsPoolSize() {
        if (!config.hasPath("http-grizzly.worker-threads-pool-size")) {
            return null;
        }
        return config.getInt("http-grizzly.worker-threads-pool-size");
    }

    public String getVerificationLinkHtmlPath() {
        return config.getString("verification-link.html.path");
    }

    public String getResetPasswordHtmlPath() {
        return config.getString("reset-password.html.path");
    }

    public String getResetPasswordWebPath() {
        return config.getString("reset-password.web.path");
    }


    public String getWebUrl() {
        return config.getString("web.url");
    }
    public String getWebHost() {
        return config.getString("web.host");
    }

    public String getShareUrl() {
        return config.getString("web.share-url");
    }

    public String getVerificationLinkWebPath() {
        return config.getString("verification-link.web.path");
    }

    public String getGoogleClientId() { return config.getString("google.client.id"); }

    public String getGoogleReCaptchaPublicKey() { return config.getString("google.recaptcha.public"); }
    public String getGoogleReCaptchaPrivateKey() { return config.getString("google.recaptcha.private"); }
    public String getGoogleReCaptchaVerificationBaseUrl() { return config.getString("google.recaptcha.verify.url"); }
    public String getTmdbApiKey() { return config.getString("tmdb.api.key"); }

    public String getMigrationFolder() { return config.getString("flyway.migration.folder"); }

    private Boolean getIsAccountEnabled() { return config.getBoolean("account.enabled"); }

    public FrontendConfiguration getFrontendConfiguration() {
        return FrontendConfiguration.of(
            getIsAccountEnabled(),
            getLocalStorageResetDate()
        );
    }

    private LocalDateTime getLocalStorageResetDate() {
        String localStorageDate = config.getString("local-storage.reset.date");
        return LocalDateTime.parse(localStorageDate, DateTimeFormatter.ISO_DATE_TIME);
    }
}
