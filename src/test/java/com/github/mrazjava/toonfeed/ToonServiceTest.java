package com.github.mrazjava.toonfeed;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author mrazjava
 */
@SpringJUnitConfig
@Import({
    ToonService.class
})
public class ToonServiceTest {
    
    @Autowired
    private ToonService toonService;
    
    @MockBean
    private List<ToonProvider> toonProviders;


    @SuppressWarnings("serial")
    @Test
    public void shouldReturnToons() {

        ToonProvider pdlProvider = mock(ToonProvider.class);
        ToonProvider xkcdProvider = mock(ToonProvider.class);
        
        List<ToonModel> pdlToons = new LinkedList<>() {{
            add(mock(ToonModel.class));
        }};
        List<ToonModel> xkcdToons = new LinkedList<>() {{
            add(mock(ToonModel.class));
            add(mock(ToonModel.class));
        }};
        
        when(toonProviders.stream()).thenReturn(Arrays.stream(new ToonProvider[]{pdlProvider, xkcdProvider}));
        when(pdlProvider.getToons()).thenReturn(pdlToons);
        when(xkcdProvider.getToons()).thenReturn(xkcdToons);
        
        assertEquals(3, toonService.getToons().size());
    }
}
