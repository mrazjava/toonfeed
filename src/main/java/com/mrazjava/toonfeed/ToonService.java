package com.mrazjava.toonfeed;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

@Service
public class ToonService {

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
