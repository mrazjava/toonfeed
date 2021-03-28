package com.github.mrazjava.toonfeed.rss;

import org.springframework.integration.dsl.MessageSourceSpec;

public class RssMessageSourceSpec extends MessageSourceSpec<RssMessageSourceSpec, RssMessageSource> {

    public RssMessageSourceSpec(RssMessageSource annieMessageSource) {
        this.target = annieMessageSource;
    }
}
