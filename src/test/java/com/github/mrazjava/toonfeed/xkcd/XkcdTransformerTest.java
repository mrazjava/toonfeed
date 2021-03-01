package com.github.mrazjava.toonfeed.xkcd;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.mrazjava.toonfeed.ToonModel;

@TestPropertySource(properties = {
        "toon.xkcd.web-url: https://xkcd.com/~ID~/",
})
@SpringJUnitConfig
@Import({
    XkcdTransformer.class
})
public class XkcdTransformerTest {

    @Autowired
    private XkcdTransformer xkcdTransformer;
    
    
    @Test
    public void shouldTransformXkcdJson() throws ParseException {
        
        @SuppressWarnings("unchecked")
        Message<XkcdSchema> message = mock(Message.class);
        XkcdSchema payload = mock(XkcdSchema.class);

        final int id = 9999;
        final String year = "2021";
        final String month = "3";
        final String day = "1";
        final String title = "XKCD can do toons!";
        final String pictureUrl = "http://xkcd.com/pic.png";
        final String webUrl = String.format("https://xkcd.com/%d/", id);
        
        when(message.getPayload()).thenReturn(payload);
        when(payload.getId()).thenReturn(id);
        when(payload.getYear()).thenReturn(year);
        when(payload.getMonth()).thenReturn(month);
        when(payload.getDay()).thenReturn(day);
        when(payload.getTitle()).thenReturn(title);
        when(payload.getPictureUrl()).thenReturn(pictureUrl);
        
        ToonModel model = xkcdTransformer.doTransform(message);
        
        assertEquals(DateUtils.parseDate("2021-03-01", XkcdTransformer.DATE_PATTERN), model.getPublishDate());
        assertEquals(title, model.getTitle());
        assertEquals(pictureUrl, model.getPictureUrl().toExternalForm());
        assertEquals(webUrl, model.getWebUrl().toExternalForm());
    }
}
