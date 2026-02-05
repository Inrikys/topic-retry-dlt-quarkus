package org.inrikys.adapters.message.kafka.in;

import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class ReviewDltConsumer {

    @Incoming("reviews-created-dlt-in")
    public CompletionStage<Void> consumeReview(Message<String> msg) {

        var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();

        System.out.println(metadata);

        // process the message payload.
        String payload = msg.getPayload();

        System.out.println(payload);

        // Acknowledge the incoming message (commit the offset)
        return msg.ack();
    }

}