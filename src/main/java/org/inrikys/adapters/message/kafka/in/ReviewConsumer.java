package org.inrikys.adapters.message.kafka.in;

import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.inrikys.adapters.message.kafka.out.ReviewProducer;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class ReviewConsumer {

    private final ReviewProducer reviewProducer;

    private static final Logger LOG = Logger.getLogger(ReviewConsumer.class);

    public ReviewConsumer(ReviewProducer reviewProducer) {
        this.reviewProducer = reviewProducer;
    }

    @Incoming("reviews-created-in")
    public CompletionStage<Void> consumeReview(Message<String> msg) {
        try {
            processEvent(msg);
        } catch (Exception e) {
            LOG.warn("Fail to process");
            handleEventException(msg);
        }
        return msg.ack();
    }

    public void processEvent(Message<String> msg) throws Exception {

        // process the message payload.
        String payload = msg.getPayload();

        System.out.println(payload);

        throw new Exception("Teste");
    }

    private void handleEventException(Message<String> message) {

        int retryCount = getRetryCount(message);

        if (retryCount <= 2) {
            retryCount++;
            sendToRetry(message, retryCount);
        } else {
            sendToDlt(message);
        }
    }

    private void sendToRetry(Message<String> message, Integer retryCount) {
        Emitter<String> retryEmitter = reviewProducer.getReviewRetryEmitter();

        Headers recordHeader = new RecordHeaders();
        recordHeader.add("retry-count", String.valueOf(retryCount).getBytes(StandardCharsets.UTF_8));

        retryEmitter.send(
                Message.of(message.getPayload())
                        .addMetadata(
                                OutgoingKafkaRecordMetadata.builder()
                                        .withHeaders(recordHeader)
                                        .build()
                        )
        );
    }

    private int getRetryCount(Message<String> message) {
        return message.getMetadata(IncomingKafkaRecordMetadata.class)
                .flatMap(metadata ->
                        StreamSupport.stream(metadata.getHeaders().spliterator(), false)
                                .filter(h -> h.key().equals("retry-count"))
                                .findFirst()
                )
                .map(header -> Integer.parseInt(new String(header.value())))
                .orElse(0);
    }

    private void sendToDlt(Message<String> message) {
        reviewProducer.getReviewDltEmitter().send(message);
    }

}