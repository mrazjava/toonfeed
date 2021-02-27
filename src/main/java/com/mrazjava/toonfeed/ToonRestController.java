package com.mrazjava.toonfeed;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ToonModel>> getToons() {
        
        List<ToonModel> toons = List.of(
                ToonModel.builder()
                    .title("foo")
                    .publishDate(DateUtils.addDays(new Date(), -3))
                    .build(),
                ToonModel.builder()
                    .title("bar")
                    .publishDate(new Date())
                    .pictureUrl("http://bar.com/baz.png")
                    .webUrl("http://bar.com/baz")
                    .build()
                    
        );
        log.info("fetched {} toons", toons.size());
        return ResponseEntity.ok(toons);
    }
}
