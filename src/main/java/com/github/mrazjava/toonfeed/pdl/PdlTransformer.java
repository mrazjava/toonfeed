package com.github.mrazjava.toonfeed.pdl;

import java.util.Date;

import org.jsoup.Jsoup;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.stereotype.Component;

import com.github.mrazjava.toonfeed.ToonModel;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

import lombok.extern.slf4j.Slf4j;

/**
 * Transforms a syndicated payload (from PDL RSS feed) into application 
 * standardized {@link ToonModel}.
 * 
 * @author mrazjava
 * @see <a href="http://feeds.feedburner.com/PoorlyDrawnLines">(P)oorly (D)rawn (L)ines</a>
 */
@Slf4j
@Component
public class PdlTransformer extends AbstractPayloadTransformer<SyndEntry, ToonModel> {

    static final String HREF_ATTR = "href";
    static final String SRC_ATTR = "src";
    
    @Override
    protected ToonModel transformPayload(SyndEntry payload) {

        log.debug("fetched PDL payload: {}", payload);
        
        return ToonModel.builder()
            .publishDate(extractPublishDate(payload))
            .title(extractTitle(payload))
            .pictureUrl(extractPictureUrl(payload))
            .webUrl(extractWebUrl(payload))
            .build();
    }
    
    private Date extractPublishDate(SyndEntry entry) {
        
        return entry.getPublishedDate();
    }
    
    private String extractTitle(SyndEntry entry) {
       
        return entry.getTitle();
    }
    
    private String extractPictureUrl(SyndEntry entry) {
        
        return entry.getContents().stream()
            .findFirst()
            .map(SyndContent::getValue)
            .map(Jsoup::parse)
            .map(doc -> doc.getElementsByAttribute(SRC_ATTR).first().absUrl(SRC_ATTR))
            .orElse(null);
    }

    private String extractWebUrl(SyndEntry entry) {

        return entry.getLink();
    }
}
