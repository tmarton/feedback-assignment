package net.tmarton.assignment.feedbacks;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Repository for CRUD operation over {@link Feedback} class
 *
 * Created by tmarton on 08/02/2018.
 */
@Repository
public interface FeedbackRepository extends ReactiveMongoRepository<Feedback, String> {

    /**
     * Retrieves all feedbacks from given author
     *
     * @param author name
     * @return feedbacks
     */
    Flux<Feedback> findByAuthor(String author);
}
