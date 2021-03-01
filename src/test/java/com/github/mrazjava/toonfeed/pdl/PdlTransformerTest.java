package com.github.mrazjava.toonfeed.pdl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.mrazjava.toonfeed.ToonModel;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

@SpringJUnitConfig
@Import({
    PdlTransformer.class
})
public class PdlTransformerTest {

    @Autowired
    private PdlTransformer pdlTransformer;
    
    
    @Test
    public void shouldTranformSyndicatedPayload() {
        
        @SuppressWarnings("unchecked")
        Message<SyndEntry> message = mock(Message.class);
        SyndEntry payload = mock(SyndEntry.class);
        SyndContent content = mock(SyndContent.class);
        
        final String title = "toons 'r fun";
        final Date publishedDate = new Date();
        final String pictureUrl = "http://foo.com/bar.png";
        final String webUrl = "http://brown-fox.com/jumps/over/lazy/dog";
        final String html = String.format("<a href=\"#\"><img src=\"%s\"/></a>", pictureUrl);
        
        when(message.getPayload()).thenReturn(payload);
        when(payload.getTitle()).thenReturn(title);
        when(payload.getPublishedDate()).thenReturn(publishedDate);
        when(content.getValue()).thenReturn(html);
        when(payload.getContents()).thenReturn(List.of(content));
        when(payload.getLink()).thenReturn(webUrl);
        
        ToonModel model = pdlTransformer.doTransform(message);
        
        assertEquals(title, model.getTitle());
        assertEquals(publishedDate, model.getPublishDate());
        assertEquals(pictureUrl, model.getPictureUrl().toString());
        assertEquals(webUrl, model.getWebUrl().toString());
    }
}
