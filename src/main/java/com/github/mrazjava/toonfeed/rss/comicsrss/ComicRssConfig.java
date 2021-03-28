package com.github.mrazjava.toonfeed.rss.comicsrss;

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

@Configuration
public class ComicRssConfig extends AbstractToonConfiguration {
       
    @Autowired
    private ComicsRssTransformer transformer;

    
    @ConditionalOnProperty("toon.comicsrss.annie.enabled")
    @ConfigurationProperties("toon.comicsrss.annie")
    class AnnieConfiguration extends AbstractToonProperties {

        static final String BEAN_PROVIDER = "ComicRssAnnieProvider";
        static final String BEAN_MSGSRC = "ComicRssAnnieMsgSrc";
        static final String BEAN_TRIGGER = "ComicRssAnnieTrigger";
        
        @Bean("ComicRssAnnieFlow")
        public IntegrationFlow flow(
                @Qualifier(BEAN_MSGSRC) RssMessageSource msgSource,
                @Qualifier(BEAN_TRIGGER) Trigger trigger,
                @Qualifier(BEAN_PROVIDER) MessageHandler handler
                ) {
            
            return integrationFlow(new RssMessageSourceSpec(msgSource), trigger, transformer, handler);
        }

        @Bean(BEAN_PROVIDER)
        ToonProviderImpl provider() {
            return new ToonProviderImpl(getFetchLimit(), BEAN_PROVIDER);
        }

        @Bean(BEAN_MSGSRC)
        RssMessageSource messageSource() {
            return new RssMessageSource(getFetchUrl(), getFetchLimit(), "comicsrss-annie:inbound-channel-adapter");
        }
        
        @Bean(BEAN_TRIGGER)
        ToonTrigger trigger() {
            return new ToonTrigger(this);
        }
    }
    
    @ConditionalOnProperty("toon.comicsrss.archie.enabled")
    @ConfigurationProperties("toon.comicsrss.archie")
    class ArchieConfiguration extends AbstractToonProperties {
        
        static final String BEAN_PROVIDER = "ComicRssArchieProvider";
        static final String BEAN_TRIGGER = "ComicRssArchieTrigger";
        static final String BEAN_MSGSRC = "ComicRssArchieMsgSrc";

        @Bean("ComicRssArchieFlow")
        public IntegrationFlow flow(
                @Qualifier(BEAN_MSGSRC) RssMessageSource msgSource,
                @Qualifier(BEAN_TRIGGER) Trigger trigger,
                @Qualifier(BEAN_PROVIDER) MessageHandler handler) {
            
            return integrationFlow(new RssMessageSourceSpec(msgSource), trigger, transformer, handler);
        }

        @Bean(BEAN_PROVIDER)
        ToonProviderImpl provider() {
            return new ToonProviderImpl(getFetchLimit(), BEAN_PROVIDER);
        }
        
        @Bean(BEAN_MSGSRC)
        RssMessageSource messageSource() {
            return new RssMessageSource(getFetchUrl(), getFetchLimit(), "comicsrss-archie:inbound-channel-adapter");
        }
        
        @Bean(BEAN_TRIGGER)
        ToonTrigger trigger() {
            return new ToonTrigger(this);
        }
    }
}
