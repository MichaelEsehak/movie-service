package com.bloompartners.services.movieservice.document;

import com.bloompartners.services.movieservice.model.Genre;
import com.bloompartners.services.movieservice.model.Rate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author melyas
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Movie {

    @Id
    private String id;
    private Long version;
    @Indexed
    private String name;
    @Indexed
    private Integer releaseYear;
    @Indexed
    private Long duration;
    private List<Actor> actors;
    private String thumbnailUrl;
    private String language;
    private String country;
    private Rate rate;
    private List<Genre> genres;


}
