package com.github.mrazjava.toonfeed;

import org.apache.commons.lang3.StringUtils;

public class ToonProviderImpl extends AbstractToonProvider {

    public static final int DEFAULT_FETCH_LIMIT = 5;
    
    private int fetchLimit;
    
    private String name;
    
    public ToonProviderImpl() {
        fetchLimit = DEFAULT_FETCH_LIMIT;
    }

    public ToonProviderImpl(String name) {
        this();
        this.name = name;
    }

    public ToonProviderImpl(int fetchLimit) {
        this(fetchLimit, null);
    }

    public ToonProviderImpl(int fetchLimit, String name) {
        this.fetchLimit = fetchLimit;
        this.name = name;
    }
    
    @Override
    public int getFetchLimit() {
        return fetchLimit;
    }

    @Override
    protected String getProviderName() {
        return StringUtils.isEmpty(name) ? super.getProviderName() : name;
    }
}
