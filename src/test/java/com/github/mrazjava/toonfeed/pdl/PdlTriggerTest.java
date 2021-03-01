package com.github.mrazjava.toonfeed.pdl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TriggerContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.mrazjava.toonfeed.pdl.PdlTrigger;

/**
 * @author mrazjava
 */
@TestPropertySource(properties = {
        "toon.pdl.fetch-limit: 1",
        "toon.pdl.pull-delay-ms: 5",
        "toon.pdl.pull-delay-min: 1"
})
@SpringJUnitConfig
@Import({
    PdlTrigger.class
})
public class PdlTriggerTest {

    @Autowired
    private PdlTrigger trigger;

    @MockBean
    private TriggerContext context;

    
    @Test
    public void shouldTriggerUnderFetchLimit() {
        
        Date now = new Date();
        
        when(context.lastCompletionTime()).thenReturn(now);
        
        Date nextExecDate = trigger.nextExecutionTime(context);
        
        assertNotNull(nextExecDate);
        assertEquals(nextExecDate.getTime()-5, now.getTime());
    }
    
    @Test
    public void shouldTriggerAboveFetchLimit() {
        
        Date now = new Date();
        
        when(context.lastCompletionTime()).thenReturn(now);
        
        trigger.nextExecutionTime(context); // 1st exec under fetch limit
        
        Date nextExecDate = trigger.nextExecutionTime(context);
        
        assertNotNull(nextExecDate);
        assertEquals(nextExecDate.getTime()-60000, now.getTime());
    }
}
