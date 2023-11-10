package com.challengers.accounts.support;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.challengers.accounts.templates.FixtureTemplate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {MockServletContext.class})
@WebAppConfiguration
public abstract class ControllerTestSupport {

  protected static MockMvc mvc;

  @BeforeAll
  public static void initFixtureFactory() {
    FixtureFactoryLoader.loadTemplates("com.challengers.accounts.templates");
  }

  @BeforeEach
  public abstract void init();

  public <T> T createObject(final Class<T> clazz, final FixtureTemplate template) {
    return Fixture.from(clazz).gimme(template.name());
  }

  public <T> List<T> createObjects(
      final Class<T> clazz, int quantity, final FixtureTemplate template) {
    return Fixture.from(clazz).gimme(quantity, template.name());
  }
}
