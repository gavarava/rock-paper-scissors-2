package com.rps.app.adapters.postgres;

import liquibase.configuration.LiquibaseConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

@ActiveProfiles({"liquibase", "test"})
@ExtendWith(SpringExtension.class)
@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = {DatabaseConfiguration.class, LiquibaseConfiguration.class})
abstract class RepositoryTestsBase {

  static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

  static {
    POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:11.2");
    POSTGRE_SQL_CONTAINER
        .withDatabaseName("postgres")
        .withPassword("postgres")
        .withUsername("postgres")
        .start();

    System.setProperty("POSTGRES_URL", POSTGRE_SQL_CONTAINER.getJdbcUrl());
    System.setProperty("POSTGRES_USER", POSTGRE_SQL_CONTAINER.getUsername());
    System.setProperty("POSTGRES_PWD", POSTGRE_SQL_CONTAINER.getPassword());
  }
}
