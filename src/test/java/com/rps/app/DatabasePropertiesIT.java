package com.rps.app;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = App.class)
@TestPropertySource("classpath:application-prod.yml")
public class DatabasePropertiesIT {

  @Autowired
  private DatabaseProperties databaseProperties;

  @SneakyThrows
  @Test
  public void whenSimplePropertyQueriedThenReturnsPropertyValue()
      throws Exception {
    System.out.println(databaseProperties.getDriver());
  }

}