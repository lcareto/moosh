package com.moosh.registry.server;

import com.moosh.registry.configuration.MooshRegistryConfiguration;
import com.moosh.registry.dto.ClientRegistry;
import com.moosh.registry.server.service.ClientRegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Slf4j
@Configuration
@EnableZuulProxy
@EnableAutoConfiguration
@Import({MooshRegistryConfiguration.class})
@ComponentScan({"com.moosh.registry"})
public class ClientRegistryServerInitializer {

    @Value("${spring.application.name:unknown}")
    private String applicationName;

    private final ClientRegistryService clientRegistryService;

    @Autowired
    public ClientRegistryServerInitializer(ClientRegistryService clientRegistryService) {

        this.clientRegistryService = clientRegistryService;
    }

    @PostConstruct
    public void init() {
        LOG.info("Initializing Registry Server [{}]", applicationName);
        List<ClientRegistry> clients = clientRegistryService.findAll();
        if (!clients.isEmpty()) {
            clientRegistryService.refreshRoutes((ClientRegistry[]) clients.toArray());
        }
    }
}
