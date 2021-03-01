package com.github.mrazjava.toonfeed.pdl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.mrazjava.toonfeed.AbstractToonfeedTrigger;

/**
 * Controls pulling duration from PDL feed. Initial set is fetched immediately with 
 * short in-between delay, then delay is substantially increased to only check for 
 * new data. See configuration.
 * 
 * @author mrazjava
 */
@Component
public class PdlTrigger extends AbstractToonfeedTrigger {

    @Value("${toon.pdl.fetch-limit}")
    private int fetchLimit;

    @Value("${toon.pdl.pull-delay-ms:500}")
    private int delayMs;

    @Value("${toon.pdl.pull-delay-min:15}")
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