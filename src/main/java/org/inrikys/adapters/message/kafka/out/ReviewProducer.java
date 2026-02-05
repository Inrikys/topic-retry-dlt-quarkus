package org.inrikys.adapters.message.kafka.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class ReviewProducer {

    @Inject
    @Channel("reviews-created-out")
    Emitter<String> reviewEmitter;

    @Inject
    @Channel("reviews-created-retry-out")
    Emitter<String> reviewRetryEmitter;

    @Inject
    @Channel("reviews-created-dlt-out")
    Emitter<String> reviewDltEmitter;

    public Emitter<String> getReviewEmitter() {
        return reviewEmitter;
    }

    public Emitter<String> getReviewRetryEmitter() {
        return reviewRetryEmitter;
    }

    public Emitter<String> getReviewDltEmitter() {
        return reviewDltEmitter;
    }
}
