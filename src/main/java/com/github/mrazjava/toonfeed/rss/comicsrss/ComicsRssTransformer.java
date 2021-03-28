package com.github.mrazjava.toonfeed.rss.comicsrss;

import java.util.Optional;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import com.github.mrazjava.toonfeed.rss.AbstractRssTransformer;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
public class ComicsRssTransformer extends AbstractRssTransformer {

    static final String SRC_ATTR = "src";
    

    @Override
    protected String extractPictureUrl(SyndEntry entry) {
        
        return Optional.ofNullable(entry.getDescription().getValue())
                .map(Jsoup::parse)
                .map(doc -> doc.getElementsByAttribute(SRC_ATTR).first().absUrl(SRC_ATTR))
                .orElse(null);                
    }
}
