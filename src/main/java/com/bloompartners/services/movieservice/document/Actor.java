package com.bloompartners.services.movieservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author melyas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document (collation = "actors")
public class Actor {

    @Id
    private String id;
    private String name;
}
