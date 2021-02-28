# Toonfeed
A goofy aggregator of cartoon information that demonstrates some practical concepts from 
Spring's built-in messaging capabilities.

## Origins
This project gave me opportunity to experiment with [Spring Integration](https://spring.io/projects/spring-integration), and its messaging and control bus concepts.

## Overview
Aggregates cartoon data from two sources:

* [PoorlyDrawnLines](http://feeds.feedburner.com/PoorlyDrawnLines) (RSS feed)
* [XKCD](https://xkcd.com/) ([JSON](https://xkcd.com/json.html))

On startup each source is pulled according to configuration (see `application.yml`), then 
pulled data is combined into internal application model and sorted by publication date with 
most recent records first. Result is exposed over a simple REST endpoint on a default 
path @ localhost:8080.

Both sources are pulled using `org.springframework.integration.dsl.IntegrationFlow`.