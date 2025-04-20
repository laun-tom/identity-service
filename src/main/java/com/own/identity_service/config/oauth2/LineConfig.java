package com.own.identity_service.config.oauth2;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "line.login")
public class LineConfig extends BaseConfig{
}
