package com.moosh.server;

import com.moosh.configuration.MooshConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Slf4j
@Configuration
@EnableAutoConfiguration
@Import({MooshConfiguration.class})
@ComponentScan({"com.moosh.server"})
public class ClientRegistryServerInitializer {

    @Value("${spring.application.name:unknown}")
    private String applicationName;

    public ClientRegistryServerInitializer() {
        LOG.info("Initialize Registry Server [{}]", applicationName);
    }
}
