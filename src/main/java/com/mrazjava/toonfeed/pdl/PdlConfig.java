package com.mrazjava.toonfeed.pdl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.messaging.MessageHandler;

/**
 * Configures DSL integration flow to pull PDL RSS feed. Initialized on startup.
 * 
 * @author mrazjava
 */
@EnableIntegration
@Configuration
public class PdlConfig { 
    
    @Bean
    public IntegrationFlow feedFlow(
            @Value("${toon.pdl.url}") Resource source,
            PdlTransformer transformer, 
            @Qualifier("PdlToonProvider") MessageHandler handler,
            @Value("${toon.pdl.pullDelayMs:500}") long delay) {

        return IntegrationFlows
                .from(Feed.inboundAdapter(source, "PDL"), e -> e.poller(p -> p.fixedDelay(delay)))
                .transform(transformer)
                .handle(handler)
                .get();
    }
}
