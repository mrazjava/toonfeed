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
 * Configures DSL integration flow to pull PDL RSS feed. Initialized on startup, then pulling with 
 * slower frequency (see {@link PdlTrigger}).
 * 
 * @author mrazjava
 */
@EnableIntegration
@Configuration
public class PdlConfig { 
    
    @Bean
    public IntegrationFlow pdlFlow(
            @Value("${toon.pdl.fetch-url}") Resource source,
            PdlTrigger trigger,
            PdlTransformer transformer, 
            @Qualifier("PdlProvider") MessageHandler handler) {

        return IntegrationFlows
                .from(Feed.inboundAdapter(source, "pdl:inbound-channel-adapter"), e -> e.poller(p -> p.trigger(trigger)))
                .transform(transformer)
                .handle(handler)
                .get();
    }
}
