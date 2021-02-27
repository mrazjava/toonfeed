package com.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Custom XKCD msg source for Spring DSL integration. Fetches 10 most recent 
 * JSON payloads starting with the current one.
 * 
 * @author mrazjava
 */
@Component
public class XkcdMessageSource extends AbstractMessageSource<XkcdSchema> {

    private static final String TOKEN_ID = "~ID~";
    
    private static final int MAX_ITEMS_TO_FETCH = 10;
    
    private int count;
    
    private int lastFetchedId;
    
    @Value("${toon.xkcd.url}")
    private String url;
    
    @Value("${toon.xkcd.url-by-id}")
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
        return lastFetchedId == 0 ? getFirst() : getNext(); 
    }
    
    private XkcdSchema getFirst() {
        XkcdSchema toon = restTemplate.getForObject(url, XkcdSchema.class);
        lastFetchedId = toon.getId();
        return toon;
    }
    
    private XkcdSchema getNext() {
        if(++count > MAX_ITEMS_TO_FETCH-1) {
            return null;
        }
        // XKCD toons seem to be ordered by ID ("num") in descending order
        XkcdSchema toon = restTemplate.getForObject(urlById.replace(TOKEN_ID, Integer.toString(lastFetchedId-1)), XkcdSchema.class);
        lastFetchedId = toon.getId();
        return toon;
    }

    public void reset() {
        lastFetchedId = count = 0;
    }
}
