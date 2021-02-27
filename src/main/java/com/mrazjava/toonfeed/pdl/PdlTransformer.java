package com.mrazjava.toonfeed.pdl;

import java.util.Date;

import org.jsoup.Jsoup;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.stereotype.Component;

import com.mrazjava.toonfeed.ToonModel;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

/**
 * Transforms a syndicated payload (from PDL RSS feed) into a {@link ToonModel}.
 * 
 * @author mrazjava
 */
@Component
public class PdlTransformer extends AbstractPayloadTransformer<SyndEntry, ToonModel> {

    private static final String HREF_ATTR = "href";
    
    @Override
    protected ToonModel transformPayload(SyndEntry payload) {

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
            .map(doc -> doc.getElementsByAttribute(HREF_ATTR).first().absUrl(HREF_ATTR))
            .orElse(null);
    }

    private String extractWebUrl(SyndEntry entry) {

        return entry.getLink();
    }
}
