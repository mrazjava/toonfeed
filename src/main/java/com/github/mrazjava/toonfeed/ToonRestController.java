package com.github.mrazjava.toonfeed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
public class ToonRestController {

    @Autowired
    private ToonService toonService;

    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ToonModel>> getToons() {
        
        List<ToonModel> toons = toonService.getToons();
        log.info("found {} 'toons", toons.size());
        return ResponseEntity.ok(toons);
    }
}
