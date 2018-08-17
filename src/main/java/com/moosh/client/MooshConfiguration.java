package com.moosh.client;

import static java.util.Objects.isNull;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 16/08/2018
 *
 * @author lyrold
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "moosh")
public class MooshConfiguration {


  private Client client;
  private Gateway gateway;

  @Data
  public static class Client {
    private String id;
    private String name;
    private String version;

    public String getName() {
      return isNull(name) || name.isEmpty() ? id + " - v" + version : name;
    }
  }

  @Data
  public static class Gateway {
    private String url;
    private String name;
  }
}
