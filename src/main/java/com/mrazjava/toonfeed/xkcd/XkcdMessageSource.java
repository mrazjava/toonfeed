package com.mrazjava.toonfeed.xkcd;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom XKCD msg source for Spring DSL integration. Fetches ${toon.xkcd.fetch-limit} 
 * most recent JSON payloads starting with the current one. After that, only pulls 
 * the most recent one (if it had changed).
 * 
 * @author mrazjava
 */
@Slf4j
@Component
public class XkcdMessageSource extends AbstractMessageSource<XkcdSchema> {

    public static final String TOKEN_ID = "~ID~";
    
    @Value("${toon.xkcd.fetch-limit}")
    private int fetchLimit;
    
    private int count;
    
    private int firstFetchedId;
    
    private int lastFetchedId;
    
    @Value("${toon.xkcd.fetch-url}")
    private String url;
    
    @Value("${toon.xkcd.fetch-url-by-id}")
    private String urlById;
        
    private RestTemplate restTemplate;

    
    public XkcdMessageSource(RestTemplateBuilder restTemplateBuilder) {
        reset();
        restTemplate = restTemplateBuilder.build();
    }
    
    @Override
    public String getComponentType() {
        return "xkcd:inbound-channel-adapter";
    }

    @Override
    protected XkcdSchema doReceive() {
        count++;
        return lastFetchedId == 0 || isInitialFetchComplete()  ? getFirst() : getNext();
    }
    
    private XkcdSchema getFirst() {

        XkcdSchema toon = restTemplate.getForObject(url, XkcdSchema.class);
        if(firstFetchedId != Optional.ofNullable(toon).map(XkcdSchema::getId).orElse(0)) {
            log.debug("[#{}] new XKCD toon: {}", count, toon);
            // this is a new current toon, either first pull or was updated
            firstFetchedId = lastFetchedId = toon.getId();
            return toon;
        }
        // we've seen this top toon, so don't push the msg
        log.trace("[#{}] no new toon published yet", count);
        return null;
    }
    
    private XkcdSchema getNext() {
        
        // XKCD toons seem to be ordered by ID ("num") in descending order
        XkcdSchema toon = restTemplate.getForObject(urlById.replace(TOKEN_ID, Integer.toString(lastFetchedId-1)), XkcdSchema.class);
        lastFetchedId = toon.getId();
        log.debug("[#{}] new (next) XKCD toon: {}", count, toon);
        return toon;
    }
    
    /**
     * @return {@code true} if initial batch of toons has been fetched
     */
    private boolean isInitialFetchComplete() {
        return count > fetchLimit;
    }

    public void reset() {
        lastFetchedId = count = 0;
    }
}
