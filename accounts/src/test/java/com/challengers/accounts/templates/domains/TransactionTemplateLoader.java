package com.challengers.accounts.templates.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.challengers.accounts.domains.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.challengers.accounts.templates.FixtureTemplate.VALID;

public class TransactionTemplateLoader implements TemplateLoader {
  @Override
  public void load() {
    Fixture.of(Transaction.class)
        .addTemplate(
            VALID.name(),
            new Rule() {
              {
                add("id", random(Long.class, range(10L, 1000L)));
                add("accountId", random(Long.class, range(10L, 1000L)));
                add("operationTypeId", random(Integer.class, range(1, 4)));
                add("amount", random(BigDecimal.class, range(BigDecimal.ONE, BigDecimal.TEN)));
                add("createdDate", LocalDateTime.now());
                add("lastModifiedDate", LocalDateTime.now());
              }
            });
  }
}
