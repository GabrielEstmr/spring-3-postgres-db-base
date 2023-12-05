package com.challengers.accounts.gateways.kafka.producers;

import com.challengers.accounts.domains.Event;
import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.gateways.DefaultKafkaTopicSenderGateway;
import com.challengers.accounts.gateways.kafka.resources.KafkaTransactionMessage;
import com.challengers.accounts.utils.JsonUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static com.challengers.accounts.configs.kafka.Topics.APP_COMMON_DEFAULT_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultKafkaTopicSenderGatewayImpl implements DefaultKafkaTopicSenderGateway {

  private static final String MSG_FAIL_TO_SEND_MESSAGE_TO_TOPIC =
      "Fail to send message to topic {}";
  private static final String MSG_SENT_SUCCESSFULLY = "Request sent to topic {} successfully";

  @Qualifier("kafkaDefaultProducerFactory")
  private final KafkaTemplate<String, String> kafkaTemplate;

  private final JsonUtils jsonUtils;

  @Override
  public void send(@NotNull final Transaction transaction) {
    final String message =
        jsonUtils.convertObjectToJSON(
            Event.builder()
                .payload(new KafkaTransactionMessage(transaction))
                .eventDateTime(LocalDateTime.now())
                .build());
    final CompletableFuture<SendResult<String, String>> future =
        kafkaTemplate.send(APP_COMMON_DEFAULT_TOPIC, transaction.getId().toString(), message);
    future.whenComplete(
        (result, ex) -> {
          if (ex == null) {
            log.info(
                "Sent message=["
                    + message
                    + "] with offset=["
                    + result.getRecordMetadata().offset()
                    + "]");
          } else {
            log.error("Unable to send message=[" + message + "] due to : " + ex.getMessage());
          }
        });
  }
}
