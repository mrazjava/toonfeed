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
pulled data is converted into internal application model, combined and sorted by publication 
date with most recent records first. Result is exposed over a simple REST endpoint on a default 
path @ localhost:8080.

Once initial fetch is complete on application startup, periodic check for latest update 
is issued and results are updated. So if this app is ran for a long enough period of time 
that remote sources publish new data, it will eventually fetch the latest records and 
update its endpoint by removing the oldest publications to make room for the new ones.

Both sources are pulled using `org.springframework.integration.dsl.IntegrationFlow`.

## Quick Start
```
mvn clean spring-boot:run
```

or

```
docker-compose up
```