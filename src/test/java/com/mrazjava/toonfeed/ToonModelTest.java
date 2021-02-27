package com.mrazjava.toonfeed;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

public class ToonModelTest {

    @Test
    public void shouldSortToonsBothDatesSet() {
        
        @SuppressWarnings("serial")
        List<ToonModel> toons = new ArrayList<ToonModel>() {{
                add(ToonModel.builder()
                    .title("foo")
                    .publishDate(DateUtils.addDays(new Date(), 3))
                    .build());
                add(ToonModel.builder()
                    .title("bar")
                    .publishDate(new Date())
                    .pictureUrl("http://bar.com/baz.png")
                    .webUrl("http://bar.com/baz")
                    .build());
        }};
        
        Collections.sort(toons);

        assertEquals("bar", toons.get(0).getTitle());
        assertEquals("foo", toons.get(1).getTitle());
    }
    
    @Test
    public void shouldSortToonsWithNullDate() {
        
        @SuppressWarnings("serial")
        List<ToonModel> toons = new ArrayList<ToonModel>() {{
                add(ToonModel.builder()
                    .title("foo")
                    .build());
                add(ToonModel.builder()
                    .title("bar")
                    .publishDate(new Date())
                    .pictureUrl("http://bar.com/baz.png")
                    .webUrl("http://bar.com/baz")
                    .build());
        }};
        
        Collections.sort(toons);

        assertEquals("bar", toons.get(0).getTitle());
        assertEquals("foo", toons.get(1).getTitle());
    }
}
