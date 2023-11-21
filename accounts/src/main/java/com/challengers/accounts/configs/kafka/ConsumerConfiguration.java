package com.challengers.accounts.configs.kafka;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsumerConfiguration {
  private String bootstrapServers;
  private String groupId;
  private String keyDeserializer;
  private String valueDeserializer;
  private String autoOffsetReset;
  private int maxPollRecords;
  private int sessionTimeoutMs;
  private int maxPollIntervalMs;
  private int maxRetries = Integer.MAX_VALUE;
}
