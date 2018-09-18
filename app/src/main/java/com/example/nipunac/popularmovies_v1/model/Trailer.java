package com.example.nipunac.popularmovies_v1.model;

public class Trailer {

//    id": "5b2115be9251416e2100b155",
////            "iso_639_1": "en",
////            "iso_3166_1": "US",
////            "key": "pzD9zGcUNrw",
////            "name": "THE NUN - Official Teaser Trailer [HD]",
////            "site": "YouTube",
////            "size": 1080,
////            "type": "Teaser"

    String id;
    String url;
    String trailerDescription;
    String site;
    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrailerDescription() {
        return trailerDescription;
    }

    public void setTrailerDescription(String trailerDescription) {
        this.trailerDescription = trailerDescription;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
