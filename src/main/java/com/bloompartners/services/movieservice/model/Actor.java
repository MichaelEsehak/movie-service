package com.bloompartners.services.movieservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author melyas
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
