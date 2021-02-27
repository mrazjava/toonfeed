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
 * @author mrazjava
 * @see <a href="http://feeds.feedburner.com/PoorlyDrawnLines">(P)oorly (D)rawn (L)ines</a>
 */
@Slf4j
@Component("PdlToonProvider")
public class PdlToonProvider implements ToonProvider, MessageHandler {
    
    private List<ToonModel> toons;
    
    public PdlToonProvider() {
        toons = new LinkedList<>();
    }

    @Override
    public List<ToonModel> getToons() {
        return Collections.unmodifiableList(toons);
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        ToonModel toon = (ToonModel)message.getPayload();
        log.info("received: {}", toon);
        toons.add(toon);
    }

}
