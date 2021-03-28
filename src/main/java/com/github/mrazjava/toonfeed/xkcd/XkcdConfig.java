package com.github.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.Trigger;

import com.github.mrazjava.toonfeed.AbstractToonConfiguration;
import com.github.mrazjava.toonfeed.AbstractToonProperties;
import com.github.mrazjava.toonfeed.ToonProviderImpl;
import com.github.mrazjava.toonfeed.ToonTrigger;

/**
 * Configures DSL integration flow to pull XKCD data. Initialized on startup, then pulling with 
 * slower frequency (see {@link XkcdTrigger}).
 * 
 * @author mrazjava
 */
@ConditionalOnProperty("toon.xkcd.enabled")
@EnableIntegration
@Configuration
public class XkcdConfig extends AbstractToonConfiguration {

    static final String BEAN_PROVIDER = "XkcdProvider";
    static final String BEAN_TRIGGER = "XkcdTrigger";
    
    @Autowired
    private XkcdProperties properties;
    
    @Bean("XkcdFlow")
    public IntegrationFlow flow(
            XkcdMessageSourceSpec msgSourceSpec,
            @Qualifier(BEAN_TRIGGER) Trigger trigger,
            XkcdTransformer transformer,
            @Qualifier(BEAN_PROVIDER) MessageHandler handler) {

        return integrationFlow(msgSourceSpec, trigger, transformer, handler);
    }
    
    @Bean(BEAN_PROVIDER)
    ToonProviderImpl provider() {
        return new ToonProviderImpl(properties.getFetchLimit(), BEAN_PROVIDER);
    }
    
    @Bean(BEAN_TRIGGER)
    ToonTrigger trigger() {
        return new ToonTrigger(properties);
    }
    
    @ConfigurationProperties("toon.xkcd")
    static class XkcdProperties extends AbstractToonProperties {
    }
}
