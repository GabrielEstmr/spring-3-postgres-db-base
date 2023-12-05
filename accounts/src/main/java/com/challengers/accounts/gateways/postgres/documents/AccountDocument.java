package com.challengers.accounts.gateways.postgres.documents;

import com.challengers.accounts.domains.Account;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "tb_account",
    schema = "public",
    indexes = {@Index(name = "idx_document_number", unique = true, columnList = "document_number")})
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDocument implements Serializable {
  @Serial private static final long serialVersionUID = -7893278923542070844L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "document_number", nullable = false, length = 50)
  private String documentNumber;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false)
  private LocalDateTime createdDate;

  @UpdateTimestamp
  @Column(name = "last_modified_date", nullable = false)
  private LocalDateTime lastModifiedDate;

  public AccountDocument(final Account account) {
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
