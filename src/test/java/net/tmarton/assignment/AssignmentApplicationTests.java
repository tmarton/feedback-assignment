package net.tmarton.assignment;

import net.tmarton.assignment.feedbacks.Feedback;
import net.tmarton.assignment.feedbacks.FeedbackRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssignmentApplicationTests {

    private static final String API_URL = "/feedbacks";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Before
    public void clear() {
        feedbackRepository.deleteAll().block();
    }


    @Test
    public void testEmptyDatabase() {
        webTestClient.get().uri(API_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Feedback.class).hasSize(0);
    }

    @Test
    public void testNotEmptyDatabase() {
        Feedback feedback = createFeedback("John Doe", "Some good positive feedback");
        feedbackRepository.save(feedback).block();

        webTestClient.get().uri(API_URL)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Feedback.class).hasSize(1).contains(feedback);
    }

    @Test
    public void testCreateFeedback() {
        Feedback feedback = createFeedback("John Doe", "Some good positive feedback");

        webTestClient.post().uri(API_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(feedback), Feedback.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.author").isEqualTo("John Doe")
                .jsonPath("$.message").isEqualTo("Some good positive feedback")
                .jsonPath("$.createdAt").isNotEmpty();
    }

    @Test
    public void testCreateFeedbackNoAuthor() {
        Feedback feedback = createFeedback("", "Some good positive feedback");

        webTestClient.post().uri(API_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(feedback), Feedback.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testCreateFeedbackNoMessage() {
        Feedback feedback = createFeedback("John Doe", "");

        webTestClient.post().uri(API_URL)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(feedback), Feedback.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testFindByAuthor() {
        Feedback feedback = createFeedback("John Doe", "Some good positive feedback");
        feedbackRepository.save(feedback).block();

        webTestClient.get().uri(String.format("%s/%s", API_URL, "John Doe"))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Feedback.class).hasSize(1);
    }

    @Test
    public void testFindByAuthorNoMatch() {
        Feedback feedback = createFeedback("John Doe", "Some good positive feedback");
        feedbackRepository.save(feedback).block();

        webTestClient.get().uri(String.format("%s/%s", API_URL, "Jane Doe"))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Feedback.class).hasSize(0);
    }

    @Test
    public void testFindByAuthorAdvanced() {
        Feedback feedback = createFeedback("John Doe", "Some good positive feedback");
        feedbackRepository.save(feedback).block();

        webTestClient.get().uri(String.format("%s/%s", API_URL, "Jane Doe"))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Feedback.class).hasSize(0);
    }

    private Feedback createFeedback(String author, String message) {
        Feedback feedback = new Feedback();
        feedback.setAuthor(author);
        feedback.setMessage(message);
        return feedback;
    }

}
