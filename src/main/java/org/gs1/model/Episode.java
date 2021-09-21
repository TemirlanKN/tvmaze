package org.gs1.model;

import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.HashMap;

@Getter @Setter
public class Episode {
    private Long id;
    private URL url;
    private String name;
    private Integer season;
    private Integer number;
    private String type;
    private String airdate;
    private HashMap<String, URL> image;
    private String summary;
}
