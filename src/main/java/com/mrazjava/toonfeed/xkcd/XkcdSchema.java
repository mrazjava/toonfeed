package com.mrazjava.toonfeed.xkcd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class XkcdSchema {

    @JsonProperty("num")
    private Integer id;
    
    @JsonProperty("safe_title")
    private String safeTitle;
    
    private String title;
    
    @JsonProperty("alt")
    private String description;
    
    @JsonProperty("img")
    private String pictureUrl;
    
    private String link;
    
    private String news;
    
    private String transcript;
    
    private String day;
    
    private String month;
    
    private String year;
}
