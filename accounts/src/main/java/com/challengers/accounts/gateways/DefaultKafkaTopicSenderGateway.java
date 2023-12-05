package com.challengers.accounts.gateways;

import com.challengers.accounts.domains.Transaction;

public interface DefaultKafkaTopicSenderGateway {
  void send(final Transaction transaction);
}
