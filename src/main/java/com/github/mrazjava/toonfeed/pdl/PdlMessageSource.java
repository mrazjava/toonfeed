package com.github.mrazjava.toonfeed.pdl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.stereotype.Component;

import com.rometools.rome.feed.synd.SyndEntry;

import lombok.extern.slf4j.Slf4j;

/**
 * Custom PDL RSS msg source for Spring DSL integration mostly based on Spring's 
 * {@link FeedEntryMessageSource}. Fetches first n entries up to ${toon.pdl.fetch-limit} 
 * and disregards the rest. After that, feeds subsequent entries on the bus only if 
 * its published date is newer from the date of last processed entry.
 * 
 * @author mrazjava
 */
@Slf4j
@Component
public class PdlMessageSource extends FeedEntryMessageSource {

    @Value("${toon.pdl.fetch-limit}")
    private int fetchLimit;
    
    private int count = 0;
    
    private Date lastPublishedDate;


    public PdlMessageSource(@Value("${toon.pdl.fetch-url}") Resource pdlSource) {
        super(pdlSource, "pdl:inbound-channel-adapter");
    }

    @Override
    protected SyndEntry doReceive() {
        
        count++;
        SyndEntry entry = super.doReceive();
        
        if(entry != null) {
            Date entryPublishDate = entry.getPublishedDate();
            
            if(count <= fetchLimit || isNewEntry(entryPublishDate)) {
                lastPublishedDate = entryPublishDate;
                log.debug("[#{}] new PDL toon: {}", count, entry);
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
