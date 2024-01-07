package com.bencha.services.configuration;

import com.typesafe.config.Config;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConfigurationService {
	private final Config config;

	@Inject
	public ConfigurationService(Config config) {
		this.config = config;
	}

	public String hello() {
		return config.getString("hello");
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

    public String getVerificationLinkWebPath() {
        return config.getString("verification-link.web.path");
    }
}
