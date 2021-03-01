package com.github.mrazjava.toonfeed.pdl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
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
            PdlMessageSourceSpec msgSourceSpec,
            PdlTrigger trigger,
            PdlTransformer transformer, 
            @Qualifier("PdlProvider") MessageHandler handler) {

        return IntegrationFlows
                .from(msgSourceSpec, e -> e.poller(p -> p.trigger(trigger)))
                .transform(transformer)
                .handle(handler)
                .get();
    }
}
