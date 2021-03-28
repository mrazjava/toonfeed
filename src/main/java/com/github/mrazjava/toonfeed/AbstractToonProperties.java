package com.github.mrazjava.toonfeed;

import org.springframework.core.io.Resource;

import lombok.Data;

@Data
public abstract class AbstractToonProperties {

    private Resource fetchUrl;
    private Integer fetchLimit = 10;
    private Integer pullDelayMs = 75;
    private Integer pullDelayMin = 60;
}
