package com.challengers.accounts.configs.kafka;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListenerConfiguration {
  private int concurrency;
}
