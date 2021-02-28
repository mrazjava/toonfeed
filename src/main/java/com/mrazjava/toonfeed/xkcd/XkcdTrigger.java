package com.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.AbstractToonfeedTrigger;

/**
 * Controls pulling duration from XKCD source. Initial set is fetched immediately with 
 * short in-between delay, then delay is substantially increased to only check for 
 * new data. See configuration.
 * 
 * @author mrazjava
 */
@Component
public class XkcdTrigger extends AbstractToonfeedTrigger {

    @Value("${toon.xkcd.pull-delay-ms:500}")
    private int delayMs;

    @Value("${toon.xkcd.pull-delay-min:15}")
    private int delayMin;

    @Override
    protected String getTriggerName() {
        return XkcdTrigger.class.getSimpleName();
    }

    @Override
    protected int getInitialDelayMs() {
        return delayMs;
    }

    @Override
    protected int getPullDelayMin() {
        return delayMin;
    }

}
