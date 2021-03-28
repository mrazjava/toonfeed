package com.github.mrazjava.toonfeed.rss;

import org.springframework.core.io.Resource;

import com.github.mrazjava.toonfeed.ToonProviderImpl;

public class RssMessageSource extends AbstractRssMessageSource {

    private int fetchLimit;

    public RssMessageSource(Resource feedResource, String channelName) {
        this(feedResource, ToonProviderImpl.DEFAULT_FETCH_LIMIT, channelName);
    }
    
    public RssMessageSource(Resource feedResource, int fetchLimit, String channelName) {
        super(feedResource, channelName);
        this.fetchLimit = fetchLimit;
    }

    @Override
    public int getFetchLimit() {
        return fetchLimit;
    }
}
