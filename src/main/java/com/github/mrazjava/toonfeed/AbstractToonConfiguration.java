package com.github.mrazjava.toonfeed;

import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageSourceSpec;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.Trigger;

@EnableIntegration
public abstract class AbstractToonConfiguration {

    protected IntegrationFlow integrationFlow(
            MessageSourceSpec<?, ?> messageSourceSpec, 
            Trigger trigger, 
            GenericTransformer<?, ?> transformer, 
            MessageHandler handler) {
        
        return IntegrationFlows
                .from(messageSourceSpec, e -> e.poller(p -> p.trigger(trigger)))
                .transform(transformer)
                .handle(handler)
                .get();
    }
}
