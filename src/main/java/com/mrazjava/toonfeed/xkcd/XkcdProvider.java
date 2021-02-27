package com.mrazjava.toonfeed.xkcd;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.ToonModel;
import com.mrazjava.toonfeed.ToonProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * Collects XKCD transformed toon data and exposes it.
 * 
 * @author mrazjava
 */
@Slf4j
@Component("XkcdProvider")
public class XkcdProvider implements ToonProvider, MessageHandler {
    
    private List<ToonModel> toons;
    
    
    public XkcdProvider() {
        toons = new LinkedList<>();
    }
    
    @Override
    public List<ToonModel> getToons() {
        return Collections.unmodifiableList(toons);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        ToonModel toon = (ToonModel)message.getPayload();
        log.info("handled: {}", toon);
        toons.add(toon);        
    }

}
