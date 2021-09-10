package org.gs1.model;

import java.net.URL;
import java.util.HashMap;
import java.util.Set;

public class TvSerie<MAP> {
    private Long id;
    private String name;

    private String type;
    private String status;
    private Long runtime;
    private Long averageRuntime;
    private String premiered;
    private HashMap<String, Float> rating;

    private URL url;
    private String summary;
    private String language;
    private Set<String> genres;
    private URL officialSite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public URL getOfficialSite() {
        return officialSite;
    }

    public void setOfficialSite(URL officialSite) {
        this.officialSite = officialSite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Long getAverageRuntime() {
        return averageRuntime;
    }

    public void setAverageRuntime(Long averageRuntime) {
        this.averageRuntime = averageRuntime;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }


    public HashMap<String, Float> getRating() {
        return rating;
    }

    public void setRating(HashMap<String, Float> rating) {
        this.rating = rating;
    }
}
