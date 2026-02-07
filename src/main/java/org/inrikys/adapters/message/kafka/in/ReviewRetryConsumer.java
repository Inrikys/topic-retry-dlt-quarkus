package org.inrikys.adapters.message.kafka.in;

import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.inrikys.adapters.CreateNewUserAdapter;
import org.jboss.logging.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class ReviewRetryConsumer {

    private static final Logger LOG = Logger.getLogger(ReviewRetryConsumer.class);
    private final ReviewConsumer reviewConsumer;

    public ReviewRetryConsumer(ReviewConsumer reviewConsumer) {
        this.reviewConsumer = reviewConsumer;
    }

    @Incoming("reviews-created-retry-in")
    public CompletionStage<Void> consumeReviewRetry(Message<String> msg) throws Exception {

        LOG.info("Executing retry for create review");
        var metadata = msg.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        LOG.info("Retry number: " + getRetryCount(metadata));

        LOG.info("Payload: " + msg.getPayload());
        reviewConsumer.processEvent(msg);

        return msg.ack();
    }

    private Integer getRetryCount(IncomingKafkaRecordMetadata metadata) {
        Header retryHeader = metadata.getHeaders().lastHeader("retry-count");
        return retryHeader != null
                ? Integer.parseInt(new String(retryHeader.value(), StandardCharsets.UTF_8))
                : 0;

    }

}