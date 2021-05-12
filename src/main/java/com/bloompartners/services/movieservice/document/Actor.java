package com.bloompartners.services.movieservice.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * @author melyas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    @Id
    private String id;
    private String name;
}
