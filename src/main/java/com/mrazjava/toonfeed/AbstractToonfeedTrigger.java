package com.mrazjava.toonfeed;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import lombok.extern.slf4j.Slf4j;

/**
 * Base for a custom spring integration polling trigger. Useful for 
 * toon feed implementations pulling with Spring DSL.
 * 
 * This base understands initial fetch size from a data feed. Deriving classes 
 * only need to provide delays for intial fetch and subsequent fetch of 
 * new data.
 * 
 * @author mrazjava
 */
@Slf4j
public abstract class AbstractToonfeedTrigger implements Trigger {

    public static final String DATE_PATTERN = "yyyy-MM-dd.HH.mm.ss.SS";
    
    protected int count = 0;
    
    @Value("${toon.init-fetch-size}")
    private int initFetchSize;
    
    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {

        count++;
        
        Date lastExecuted = triggerContext.lastCompletionTime();
        Date nextExecution;
        
        if(lastExecuted == null) {
            nextExecution = DateUtils.addMilliseconds(new Date(), getInitialDelayMs());
            log.debug("[{}] first execution scheduled for: {}",
                    getTriggerName(), DateFormatUtils.format(nextExecution, DATE_PATTERN));
            return nextExecution;
        }
        
        nextExecution = count > initFetchSize ? 
                DateUtils.addMinutes(lastExecuted, getPullDelayMin()) : 
                DateUtils.addMilliseconds(lastExecuted, getInitialDelayMs());            

        log.debug("[{}] last executed: {} | next execution: {}", 
                getTriggerName(), 
                DateFormatUtils.format(lastExecuted, DATE_PATTERN), 
                DateFormatUtils.format(nextExecution, DATE_PATTERN));
        
        return nextExecution;
    }
    
    protected abstract String getTriggerName();

    protected abstract int getInitialDelayMs();
    
    protected abstract int getPullDelayMin();
}
