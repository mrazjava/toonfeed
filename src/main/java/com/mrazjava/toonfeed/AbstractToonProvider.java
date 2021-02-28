package com.mrazjava.toonfeed;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractToonProvider implements ToonProvider, LimitedFetching, MessageHandler {

    protected List<ToonModel> toons = new LinkedList<>();
    
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
