package com.github.mrazjava.toonfeed.rss;

import java.util.Date;

import org.springframework.integration.transformer.AbstractPayloadTransformer;

import com.github.mrazjava.toonfeed.ToonModel;
import com.rometools.rome.feed.synd.SyndEntry;

import lombok.extern.slf4j.Slf4j;

/**
 * Transforms a syndicated payload into application standardized {@link ToonModel}.
 * 
 * @author mrazjava
 * @see <a href="http://feeds.feedburner.com/PoorlyDrawnLines">(P)oorly (D)rawn (L)ines</a>
 */
@Slf4j
public abstract class AbstractRssTransformer extends AbstractPayloadTransformer<SyndEntry, ToonModel> {
    
    @Override
    protected ToonModel transformPayload(SyndEntry payload) {

        log.debug("fetched RSS payload: {}", payload);
        
        return ToonModel.builder()
            .publishDate(extractPublishDate(payload))
            .title(extractTitle(payload))
            .pictureUrl(extractPictureUrl(payload))
            .webUrl(extractWebUrl(payload))
            .build();
    }
    
    protected Date extractPublishDate(SyndEntry entry) {
        
        return entry.getPublishedDate();
    }
    
    protected String extractTitle(SyndEntry entry) {
       
        return entry.getTitle();
    }
    
    protected abstract String extractPictureUrl(SyndEntry entry);

    private String extractWebUrl(SyndEntry entry) {

        return entry.getLink();
    }
}
