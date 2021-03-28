package com.github.mrazjava.toonfeed.rss;

import java.util.Date;

import org.springframework.core.io.Resource;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;

import com.github.mrazjava.toonfeed.LimitedFetching;
import com.rometools.rome.feed.synd.SyndEntry;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom toon RSS msg source for Spring DSL integration mostly based on Spring's 
 * {@link FeedEntryMessageSource}. Fetches first n entries up to ${toon.pdl.fetch-limit} 
 * and disregards the rest. After that, feeds subsequent entries on the bus only if 
 * its published date is newer from the date of last processed entry.
 * 
 * @author mrazjava
 */
@Slf4j
public abstract class AbstractRssMessageSource extends FeedEntryMessageSource implements LimitedFetching {
    
    private int count = 0;
    
    private Date lastPublishedDate;

    
    public AbstractRssMessageSource(Resource feedResource, String metadataKey) {
        super(feedResource, metadataKey);
    }

    @Override
    protected SyndEntry doReceive() {
        
        count++;
        SyndEntry entry = super.doReceive();
        
        if(entry != null) {
            Date entryPublishDate = entry.getPublishedDate();
            
            if(count <= getFetchLimit() || isNewEntry(entryPublishDate)) {
                lastPublishedDate = entryPublishDate;
                log.debug("[{}] - #{} new PDL toon: {}", getClass().getSimpleName(), count, entry);
                return entry;
            }
        }
        
        log.trace("[#{}] no new toon published yet", count);
        
        return null;
    }
    
    private boolean isNewEntry(Date entryPublishDate) {
        return lastPublishedDate != null && lastPublishedDate.before(entryPublishDate);
    }
}
