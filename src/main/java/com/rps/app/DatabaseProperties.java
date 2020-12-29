package com.rps.app;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Data
@Configuration
@ConfigurationProperties(prefix = "database")
public class DatabaseProperties {

  String username;
  String password;
  String url;
  String driver;

  @Getter
  @Setter
  public static class HikariSettings {

    int maximumPoolSize;
    boolean autoCommit;
  }
}
