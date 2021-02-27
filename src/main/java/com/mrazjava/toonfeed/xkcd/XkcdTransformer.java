package com.mrazjava.toonfeed.xkcd;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.ToonModel;

import lombok.extern.slf4j.Slf4j;

/**
 * Transforms XKCD cartoon record into application standardized {@link ToonModel}.
 * 
 * @author mrazjava
 * @see <a href="https://xkcd.com/json.html">XKCD</a>
 */
@Slf4j
@Component
public class XkcdTransformer extends AbstractPayloadTransformer<XkcdSchema, ToonModel> {

    private static final String DATE_PATTERN = "YYYY-MM-DD";
    
    @Value("${toon.xkcd.web-url}")
    private String webUrl;

    
    @Override
    protected ToonModel transformPayload(XkcdSchema payload) {

        log.debug("fetched XKCD payload: {}", payload);
        
        return ToonModel.builder()
            .publishDate(extractPublishedDate(payload))
            .title(extractTitle(payload))
            .pictureUrl(extractPictureUrl(payload))
            .webUrl(extractWebUrl(payload))
            .build();
    }

    private Date extractPublishedDate(XkcdSchema schema) {
        
        String dateAsStr = String.format("%s-%s-%s", schema.getYear(), schema.getMonth(), schema.getDay());
        Date date = null;
        
        try {
            date = DateUtils.parseDate(dateAsStr, DATE_PATTERN);
        } catch (ParseException e) {
            log.warn("cannot parse date: [{}]", dateAsStr);
        }
        
        return date;
    }

    private String extractTitle(XkcdSchema schema) {
        
        return schema.getTitle();
    }
    
    private String extractPictureUrl(XkcdSchema schema) {
        
        return schema.getPictureUrl();
    }
    
    private String extractWebUrl(XkcdSchema schema) {
        
        return webUrl.replace(XkcdMessageSource.TOKEN_ID, Integer.toString(schema.getId()));
    }    
}
