package com.github.mrazjava.toonfeed.rss.pdl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.Trigger;

import com.github.mrazjava.toonfeed.AbstractToonConfiguration;
import com.github.mrazjava.toonfeed.AbstractToonProperties;
import com.github.mrazjava.toonfeed.ToonProviderImpl;
import com.github.mrazjava.toonfeed.ToonTrigger;
import com.github.mrazjava.toonfeed.rss.RssMessageSource;
import com.github.mrazjava.toonfeed.rss.RssMessageSourceSpec;

/**
 * Configures DSL integration flow to pull PDL RSS feed. Initialized on startup, then pulling with 
 * slower frequency (see {@link PdlTrigger}).
 * 
 * @author mrazjava
 */
@ConditionalOnProperty("toon.pdl.enabled")
@Configuration
public class PdlConfig extends AbstractToonConfiguration { 

    static final String BEAN_PROVIDER = "PdlProvider";
    static final String BEAN_MSGSRC = "PdlMsgSrc";
    static final String BEAN_TRIGGER = "PdlTrigger";
    
    @Autowired
    private PdlProperties properties;

    
    @Bean("PdlFlow")
    public IntegrationFlow flow(
            @Qualifier(BEAN_MSGSRC) RssMessageSource msgSource,
            @Qualifier(BEAN_TRIGGER) Trigger trigger,
            PdlTransformer transformer,
            @Qualifier(BEAN_PROVIDER) MessageHandler handler) {

        return integrationFlow(new RssMessageSourceSpec(msgSource), trigger, transformer, handler);
    }

    @Bean(BEAN_PROVIDER)
    ToonProviderImpl provider() {
        return new ToonProviderImpl(properties.getFetchLimit(), BEAN_PROVIDER);
    }
    
    @Bean(BEAN_MSGSRC)
    RssMessageSource messageSource() {
        return new RssMessageSource(
                properties.getFetchUrl(), properties.getFetchLimit(), "pdl:inbound-channel-adapter");
    }
    
    @Bean(BEAN_TRIGGER)
    ToonTrigger trigger() {
        return new ToonTrigger(properties);
    }
    
    @ConfigurationProperties("toon.pdl")
    static class PdlProperties extends AbstractToonProperties {
    }
}
