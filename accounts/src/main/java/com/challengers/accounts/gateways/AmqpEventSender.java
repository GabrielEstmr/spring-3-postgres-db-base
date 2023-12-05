package com.challengers.accounts.gateways;

import com.challengers.accounts.domains.Transaction;

public interface AmqpEventSender {
  void send(final Transaction transaction);
}
