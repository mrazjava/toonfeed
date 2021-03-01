package com.github.mrazjava.toonfeed;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * A simple struct for cartoon information.
 * 
 * @author mrazjava
 */
@JsonInclude(Include.NON_NULL)
@Builder
@Data
@Slf4j
public class ToonModel implements Comparable<ToonModel> {

    private Date publishDate;
    
    private URL pictureUrl;
    
    private URL webUrl;
    
    private String title;
    
    public static class ToonModelBuilder {
        
        public ToonModelBuilder pictureUrl(String pictureUrl) {
            try {
                this.pictureUrl = new URL(pictureUrl);
            } catch (MalformedURLException e) {
                log.warn("cannot set [{}] to pictureUrl: {}", pictureUrl, e.getMessage());
            }
            return this;            
        }
        
        public ToonModelBuilder webUrl(String webUrl) {
            try {
                this.webUrl = new URL(webUrl);
            } catch (MalformedURLException e) {
                log.warn("cannot set [{}] to webUrl: {}", webUrl, e.getMessage());
            }
            return this;            
        }
    }

    @Override
    public int compareTo(ToonModel that) {

        if(this.publishDate == null && that.publishDate == null)
            return 0;
        
        if(this.publishDate == null && that.publishDate != null)
            return 1;
        
        if(this.publishDate != null && that.publishDate == null)
            return -1;

        return NumberUtils.compare(this.publishDate.getTime(), that.publishDate.getTime());
    }
}
