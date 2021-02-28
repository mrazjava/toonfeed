package com.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.AbstractToonProvider;

/**
 * Collects XKCD transformed toon data and exposes it.
 * 
 * @author mrazjava
 */
@Component("XkcdProvider")
public class XkcdProvider extends AbstractToonProvider {

    @Value("${toon.xkcd.fetch-limit}")
    private int fetchLimit;    

    
    @Override
    public int getFetchLimit() {
        return fetchLimit;
    }
}
