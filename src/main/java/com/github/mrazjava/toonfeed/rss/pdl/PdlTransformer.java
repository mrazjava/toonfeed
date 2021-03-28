package com.github.mrazjava.toonfeed.rss.pdl;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import com.github.mrazjava.toonfeed.ToonModel;
import com.github.mrazjava.toonfeed.rss.AbstractRssTransformer;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;

/**
 * Transforms a syndicated payload (from PDL RSS feed) into application 
 * standardized {@link ToonModel}.
 * 
 * @author mrazjava
 * @see <a href="http://feeds.feedburner.com/PoorlyDrawnLines">(P)oorly (D)rawn (L)ines</a>
 */
@Component
public class PdlTransformer extends AbstractRssTransformer {

    static final String SRC_ATTR = "src";
    
    
    @Override
    protected String extractPictureUrl(SyndEntry entry) {
        
        return entry.getContents().stream()
            .findFirst()
            .map(SyndContent::getValue)
            .map(Jsoup::parse)
            .map(doc -> doc.getElementsByAttribute(SRC_ATTR).first().absUrl(SRC_ATTR))
            .orElse(null);
    }
}
