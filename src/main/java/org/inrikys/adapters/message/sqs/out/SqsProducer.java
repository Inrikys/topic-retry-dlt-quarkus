package org.inrikys.adapters.message.sqs.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@ApplicationScoped
public class SqsProducer {

    @Inject
    SqsClient sqsClient;

    public void send(String queueUrl, String payload) {

        SendMessageRequest request = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(payload)
                .build();

        sqsClient.sendMessage(request);
    }
}

