package com.mrazjava.toonfeed.xkcd;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.ToonModel;
import com.mrazjava.toonfeed.ToonProvider;

/**
 * @author mrazjava
 * @see <a href="https://xkcd.com/json.html">XKCD</a>
 */
@Component
public class XkcdToonProvider implements ToonProvider {

    @Override
    public List<ToonModel> getToons() {
        return List.of(
                ToonModel.builder()
                    .title("xyz")
                    .publishDate(DateUtils.addDays(new Date(), -1))
                    .build(),
                ToonModel.builder()
                    .title("abc")
                    .publishDate(DateUtils.addHours(new Date(), 8))
                    .pictureUrl("http://abc.com/abc.png")
                    .build()
                    
        );        
    }

}
