package com.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageHandler;

/**
 * Configures DSL integration flow to pull XKCD data. Initialized on startup.
 * 
 * @author mrazjava
 */
@EnableIntegration
@Configuration
public class XkcdConfig {

    @Bean
    public IntegrationFlow xkcdFlow(
            XkcdMessageSourceSpec msgSourceSpec,
            XkcdTransformer transformer,
            @Qualifier("XkcdProvider") MessageHandler handler,
            @Value("${toon.xkcd.pullDelayMs:500}") long delay) {

        return IntegrationFlows
                .from(msgSourceSpec, e -> e.poller(p -> p.fixedDelay(delay)))
                .transform(transformer)
                .handle(handler)
                .get();
    }
}
