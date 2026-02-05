package org.inrikys.adapters.message.kafka.in;

import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;
import org.inrikys.adapters.CreateNewUserAdapter;
import org.inrikys.adapters.message.kafka.out.ReviewProducer;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.util.Optional;
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
            var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();

            System.out.println(metadata);

            // process the message payload.
            String payload = msg.getPayload();

            System.out.println(payload);

            throw new Exception("Teste");

//            return msg.ack();

        } catch (Exception e) {
            LOG.warn("Fail to process");
            sendToRetry(msg);
            return msg.nack(e);
        }

    }

    private void sendToRetry(Message<String> message) {

        int retryCount = getRetryCount(message);

        Emitter<String> retryEmitter = reviewProducer.getReviewRetryEmitter();

        RecordHeader recordHeader = new RecordHeader("retry-count", String.valueOf(retryCount).getBytes());
        retryEmitter.send(
                Message.of(message.getPayload())
                        .addMetadata(
                                OutgoingKafkaRecordMetadata.builder()
                                        .addHeaders(recordHeader)
                                        .build()
                        )
        );
    }

    private int getRetryCount(Message<?> message) {
        return message.getMetadata(IncomingKafkaRecordMetadata.class)
                .flatMap(metadata ->
                        StreamSupport.stream(metadata.getHeaders().spliterator(), false)
                                .filter(h -> h.key().equals("retry-count"))
                                .findFirst()
                )
                .map(header -> Integer.parseInt(new String(header.value())))
                .orElse(0);
    }

}