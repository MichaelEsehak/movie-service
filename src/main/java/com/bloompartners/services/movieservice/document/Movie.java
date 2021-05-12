package com.bloompartners.services.movieservice.document;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author melyas
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Movie {

    private String id;
    private String name;
    private Integer releaseYear;
    private Long duration;
    private String thumbnailUrl;
    private String language;
    private String country;
    private Rate rate;
    private List<Genre> genres;




}
