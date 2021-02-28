package com.mrazjava.toonfeed.xkcd;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.AbstractToonfeedTrigger;

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
