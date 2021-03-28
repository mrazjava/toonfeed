package com.github.mrazjava.toonfeed;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
public abstract class AbstractToonTrigger implements Trigger, LimitedFetching {

    public static final String DATE_PATTERN = "yyyy-MM-dd.HH.mm.ss.SS";
    
    protected int count = 0;
    
    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {

        count++;
        
        Date lastExecuted = triggerContext.lastCompletionTime();
        Date nextExecution;
        
        if(lastExecuted == null) {
            nextExecution = DateUtils.addMilliseconds(new Date(), getInitialDelayMs());
            if(log.isDebugEnabled()) {
                log.debug("[{}] first execution scheduled for: {}",
                        getClass().getSimpleName(), DateFormatUtils.format(nextExecution, DATE_PATTERN));
            }
            return nextExecution;
        }
        
        nextExecution = count > getFetchLimit() ? 
                DateUtils.addMinutes(lastExecuted, getPullDelayMin()) : 
                DateUtils.addMilliseconds(lastExecuted, getInitialDelayMs());            

        if(log.isDebugEnabled()) {
            log.debug("[{}] last executed: {} | next execution: {}", 
                    getClass().getSimpleName(), 
                    DateFormatUtils.format(lastExecuted, DATE_PATTERN), 
                    DateFormatUtils.format(nextExecution, DATE_PATTERN));
        }
        
        return nextExecution;
    }

    protected abstract int getInitialDelayMs();
    
    protected abstract int getPullDelayMin();
}
