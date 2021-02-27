package com.mrazjava.toonfeed.pdl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.ToonModel;
import com.mrazjava.toonfeed.ToonProvider;

/**
 * @author mrazjava
 * @see <a href="http://feeds.feedburner.com/PoorlyDrawnLines">(P)oorly (D)rawn (L)ines</a>
 */
@Component
public class PdlToonProvider implements ToonProvider {

    @Override
    public List<ToonModel> getToons() {
        return List.of(
                ToonModel.builder()
                    .title("foo")
                    .publishDate(DateUtils.addDays(new Date(), -3))
                    .build(),
                ToonModel.builder()
                    .title("bar")
                    .publishDate(new Date())
                    .pictureUrl("http://bar.com/baz.png")
                    .webUrl("http://bar.com/baz")
                    .build()
                    
        );        
    }

}
