package com.mrazjava.toonfeed;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides collected cartoon data from some source for further consumption. The 
 * data is collected up to {@link #getFetchLimit()}. Once limit is reached but  
 * more data is consumed, the oldest data will be removed to make room for the 
 * newly consumed data while respecting the {@code #getFetchLimit()}.
 * 
 * Data is collected by consumption of Spring {@link Message} that can be fed by 
 * any means supported by the framework (eg: Spring DSL integration).
 * 
 * @author mrazjava
 */
@Slf4j
public abstract class AbstractToonProvider implements ToonProvider, LimitedFetching, MessageHandler {

    protected LinkedList<ToonModel> toons = new LinkedList<>();
    
    @Override
    public List<ToonModel> getToons() {
        return Collections.unmodifiableList(toons);
    }

    @Override
    public synchronized void handleMessage(Message<?> message) throws MessagingException {
        
        ToonModel toon = (ToonModel)message.getPayload();
        
        if(log.isInfoEnabled()) {
            log.info("[{}] handling: {}", getClass().getSimpleName(), toon);
        }
        
        if(toons.size() == getFetchLimit()) {
            ToonModel ejectedToon = toons.removeFirst();
            log.info("fetch-limit[{}] reached; ejected oldest toon: {}", getFetchLimit(), ejectedToon);
        }
        
        toons.add(toon);
    }
}
