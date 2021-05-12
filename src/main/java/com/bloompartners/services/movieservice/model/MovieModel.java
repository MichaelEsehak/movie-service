package com.bloompartners.services.movieservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author melyas
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieModel {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("releaseYear")
    private Integer releaseYear;

    @JsonProperty("duration")
    private Long duration;

    @JsonProperty("thumbnailUrl")
    private String thumbnailUrl;

    @JsonProperty("language")
    private String language;

    @JsonProperty("country")
    private String country;

    @JsonProperty("rate")
    private Rate rate;

    @JsonProperty("genres")
    private List<Genre> genres;




}
