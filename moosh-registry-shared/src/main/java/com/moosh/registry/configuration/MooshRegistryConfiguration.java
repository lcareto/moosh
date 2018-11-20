package com.moosh.registry.configuration;

import com.moosh.registry.dto.ClientRegistry;
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
public class MooshRegistryConfiguration {
    private ClientRegistry client;
    private Server server;

    @Data
    public static class Server {
        private String id;
        private String url;
        private String name;
    }
}
