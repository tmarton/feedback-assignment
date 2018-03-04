package net.tmarton.assignment.feedbacks;

import javax.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * Created by tmarton on 08/02/2018.
 */
@Log
@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(final FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /* URL HANDLING */
    // TODO TMARTON use SWAGGER to document API

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public Flux<Feedback> findAll() {
        log.entering(FeedbackController.class.getName(), "findAll");
        return feedbackRepository.findAll();
    }

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public Mono<Feedback> save(@Valid @RequestBody Feedback feedback) {
        log.entering(FeedbackController.class.getName(), "save", feedback);
        return feedbackRepository.save(feedback);
    }

    @GetMapping(value = "/{author}", produces = APPLICATION_JSON_UTF8_VALUE)
    Flux<Feedback> findByAuthor(@PathVariable String author) {
        log.entering(FeedbackController.class.getName(), "findByAuthor", author);
        return feedbackRepository.findByAuthor(author);
    }
}
