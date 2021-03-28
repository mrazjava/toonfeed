package com.github.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.mrazjava.toonfeed.AbstractToonTrigger;

/**
 * Controls pulling duration from XKCD source. Initial set is fetched immediately with 
 * short in-between delay, then delay is substantially increased to only check for 
 * new data. See configuration.
 * 
 * @author mrazjava
 */
@Component
public class XkcdTrigger extends AbstractToonTrigger {

    @Value("${toon.xkcd.fetch-limit}")
    private int fetchLimit;

    @Value("${toon.xkcd.pull-delay-ms:500}")
    private int delayMs;

    @Value("${toon.xkcd.pull-delay-min:15}")
    private int delayMin;
        

    @Override
    protected int getInitialDelayMs() {
        return delayMs;
    }

    @Override
    protected int getPullDelayMin() {
        return delayMin;
    }

    @Override
    public int getFetchLimit() {
        return fetchLimit;
    }
}
