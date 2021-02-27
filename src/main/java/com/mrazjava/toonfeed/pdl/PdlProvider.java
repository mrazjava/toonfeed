package com.mrazjava.toonfeed.pdl;

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
 * Collects PDL transformed toon data and exposes it.
 * 
 * @author mrazjava
 */
@Slf4j
@Component("PdlProvider")
public class PdlProvider implements ToonProvider, MessageHandler {
    
    private List<ToonModel> toons;
    
    public PdlProvider() {
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
