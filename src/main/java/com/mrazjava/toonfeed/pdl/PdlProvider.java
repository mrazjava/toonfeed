package com.mrazjava.toonfeed.pdl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.AbstractToonProvider;

/**
 * Collects PDL transformed toon data and exposes it.
 * 
 * @author mrazjava
 */
@Component("PdlProvider")
public class PdlProvider extends AbstractToonProvider {

    @Value("${toon.pdl.fetch-limit}")
    private int fetchLimit;

    
    @Override
    public int getFetchLimit() {
        return fetchLimit;
    }
}
