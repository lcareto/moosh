package com.moosh.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static java.util.Objects.isNull;

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
    private server server;

    @Data
    public static class Client {
        private String id;
        private String name;
        private String version;

        public String getName() {
            return isNull(name) || name.isEmpty() ? id + "-" + version : name;
        }
    }

    @Data
    public static class server {
        private String id;
        private String url;
        private String name;
        private Store store;
    }

    @Data
    public static class Store {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }
}
