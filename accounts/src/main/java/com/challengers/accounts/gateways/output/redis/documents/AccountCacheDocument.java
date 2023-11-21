package com.challengers.accounts.gateways.output.redis.documents;

import com.challengers.accounts.domains.Account;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@RedisHash("AccountCacheDocument")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountCacheDocument implements Serializable {
  @Serial private static final long serialVersionUID = 7975304665014914869L;

  @Id private Long id;
  @Indexed
  private String documentNumber;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public AccountCacheDocument(final Account account) {
    this.id = account.getId();
    this.documentNumber = account.getDocumentNumber();
    this.createdDate = account.getCreatedDate();
    this.lastModifiedDate = account.getLastModifiedDate();
  }

  public Account toDomain() {
    return Account.builder()
        .id(this.id)
        .documentNumber(this.documentNumber)
        .createdDate(this.createdDate)
        .lastModifiedDate(this.lastModifiedDate)
        .build();
  }
}
