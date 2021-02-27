package com.mrazjava.toonfeed;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides access to cartoon data from various sources.
 * 
 * @author mrazjava
 */
@Service
public class ToonService {

    @Autowired
    private List<ToonProvider> toonProviders;


    public List<ToonModel> getToons() {
        
        return toonProviders.stream()
                .flatMap(tp -> tp.getToons().stream())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
