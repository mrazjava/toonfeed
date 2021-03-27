package com.github.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageHandler;

/**
 * Configures DSL integration flow to pull XKCD data. Initialized on startup, then pulling with 
 * slower frequency (see {@link XkcdTrigger}).
 * 
 * @author mrazjava
 */
@EnableIntegration
@Configuration
public class XkcdConfig {

    @ConditionalOnProperty("toon.xkcd.enabled")
    @Bean
    public IntegrationFlow xkcdFlow(
            XkcdMessageSourceSpec msgSourceSpec,
            XkcdTrigger trigger,
            XkcdTransformer transformer,
            @Qualifier("XkcdProvider") MessageHandler handler) {

        return IntegrationFlows
                .from(msgSourceSpec, e -> e.poller(p -> p.trigger(trigger)))
                .transform(transformer)
                .handle(handler)
                .get();
    }
}
