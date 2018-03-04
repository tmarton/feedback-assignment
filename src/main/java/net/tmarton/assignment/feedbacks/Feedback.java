package net.tmarton.assignment.feedbacks;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Feedback domain model
 *
 * Created by tmarton on 08/02/2018.
 */
@Document(collection = "feedbacks")
@Data
@NoArgsConstructor
public class Feedback {

    @Id
    private String id;

    /**
     * Feedback author
     */
    @NotBlank
    @Size(max = 255)
    private String author;

    /**
     * Feedback message
     */
    @NotBlank
    private String message;

    /**
     * Feedback time of creation
     */
    @NotNull
    private Date createdAt = new Date();
}
