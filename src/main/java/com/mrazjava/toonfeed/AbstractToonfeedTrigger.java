package com.mrazjava.toonfeed;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractToonfeedTrigger implements Trigger {

    protected int count = 0;
    
    @Value("${toon.init-fetch-size}")
    private int initFetchSize;
    
    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {

        count++;
        
        Date lastExecuted = triggerContext.lastCompletionTime();
        
        log.debug("[{}] last executed: {}", getTriggerName(), lastExecuted);
        
        if(lastExecuted == null) {
            return DateUtils.addMilliseconds(new Date(), getInitialDelayMs());
        }
        
        return count > initFetchSize ? 
                DateUtils.addMinutes(lastExecuted, getPullDelayMin()) : 
                DateUtils.addMilliseconds(lastExecuted, getInitialDelayMs());
    }
    
    protected abstract String getTriggerName();

    protected abstract int getInitialDelayMs();
    
    protected abstract int getPullDelayMin();
}
