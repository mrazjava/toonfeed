package com.github.mrazjava.toonfeed.pdl;

import org.springframework.integration.dsl.MessageSourceSpec;
import org.springframework.stereotype.Component;

@Component
public class PdlMessageSourceSpec extends MessageSourceSpec<PdlMessageSourceSpec, PdlMessageSource> {

    public PdlMessageSourceSpec(PdlMessageSource pdlMessageSource) {
        this.target = pdlMessageSource;
    }
}
