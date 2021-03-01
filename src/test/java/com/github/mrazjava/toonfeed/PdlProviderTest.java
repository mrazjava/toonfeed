package com.github.mrazjava.toonfeed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.mrazjava.toonfeed.pdl.PdlProvider;

@TestPropertySource(properties = {
        "toon.pdl.fetch-limit: 1",
        "toon.pdl.pull-delay-ms: 5",
        "toon.pdl.pull-delay-min: 1"
})
@SpringJUnitConfig
@Import({
    PdlProvider.class
})
public class PdlProviderTest {

    @Autowired
    private PdlProvider pdlProvider;
    
    
    @Test
    public void shouldAccept() {
        
        @SuppressWarnings("unchecked")
        Message<ToonModel> toonMsg = mock(Message.class);
        ToonModel toonPayload = mock(ToonModel.class);
        
        when(toonMsg.getPayload()).thenReturn(toonPayload);
        
        assertTrue(pdlProvider.getToons().isEmpty());
        
        pdlProvider.handleMessage(toonMsg);
        
        assertEquals(1, pdlProvider.getToons().size());
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void shouldAcceptAndEjectOldest() {

        Message<ToonModel> toonMsg1 = mock(Message.class);
        Message<ToonModel> toonMsg2 = mock(Message.class);
        Message<ToonModel> toonMsg3 = mock(Message.class);

        ToonModel toonPayload1 = mock(ToonModel.class);
        ToonModel toonPayload2 = mock(ToonModel.class);
        ToonModel toonPayload3 = mock(ToonModel.class);
        
        when(toonMsg1.getPayload()).thenReturn(toonPayload1);
        when(toonMsg2.getPayload()).thenReturn(toonPayload2);
        when(toonMsg3.getPayload()).thenReturn(toonPayload3);
        when(toonPayload1.getTitle()).thenReturn("toon-1");
        when(toonPayload2.getTitle()).thenReturn("toon-2");
        when(toonPayload3.getTitle()).thenReturn("toon-3");
        
        pdlProvider.handleMessage(toonMsg1);
        pdlProvider.handleMessage(toonMsg2);
        pdlProvider.handleMessage(toonMsg3);
        
        assertEquals(1, pdlProvider.getToons().size());
        assertEquals("toon-3", pdlProvider.getToons().get(0).getTitle());
    }
}
