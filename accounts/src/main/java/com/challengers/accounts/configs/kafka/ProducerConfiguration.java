package com.challengers.accounts.configs.kafka;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProducerConfiguration {
    private String bootstrapServers;
    private String keySerializer;
    private String valueSerializer;
}
