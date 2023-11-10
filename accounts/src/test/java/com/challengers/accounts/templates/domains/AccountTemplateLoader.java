package com.challengers.accounts.templates.domains;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.challengers.accounts.domains.Account;

import java.time.LocalDateTime;

import static com.challengers.accounts.templates.FixtureTemplate.VALID;

public class AccountTemplateLoader implements TemplateLoader {
  @Override
  public void load() {
    Fixture.of(Account.class)
        .addTemplate(
            VALID.name(),
            new Rule() {
              {
                add("id", random(Long.class, range(10L, 1000L)));
                add("documentNumber", regex("\\d{5}"));
                add("createdDate", LocalDateTime.now());
                add("lastModifiedDate", LocalDateTime.now());
              }
            });
  }
}
