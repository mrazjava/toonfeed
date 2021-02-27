package com.mrazjava.toonfeed.xkcd;

import org.springframework.integration.dsl.MessageSourceSpec;
import org.springframework.stereotype.Component;

/**
 * A msg spec for XKCD based flow. 
 * 
 * @author mrazjava
 */
@Component
public class XkcdMessageSourceSpec extends MessageSourceSpec<XkcdMessageSourceSpec, XkcdMessageSource> {

    public XkcdMessageSourceSpec(XkcdMessageSource xkcdMessageSource) {
        this.target = xkcdMessageSource;
    }
}
