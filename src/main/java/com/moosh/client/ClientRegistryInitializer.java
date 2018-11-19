package com.moosh.client;

import com.moosh.configuration.MooshConfiguration;
import com.moosh.server.dom.ClientRegistry;
import com.moosh.server.dom.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.net.URI;
import java.util.stream.Collectors;

import static com.moosh.server.controller.ClientRegistryServerController.CLIENT_REGISTRY_BASE_PATH;
import static org.springframework.http.RequestEntity.post;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Slf4j
@Configuration
@Import({MooshConfiguration.class})
public class ClientRegistryInitializer {

    @Autowired
    public ClientRegistryInitializer(MooshConfiguration configuration, RequestMappingHandlerMapping handlerMapping) {
        LOG.info("Registering client [{}]", configuration.getClient().getName());

        // Build registry request
        ClientRegistry body = new ClientRegistry();
        body.setId(configuration.getClient().getId());
        body.setRequestMappings(handlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .map(m -> RequestMapping.from(m.getKey()))
                .collect(Collectors.toSet())
        );

        // Post registry request
        String serverBaseUrl = configuration.getServer().getUrl();
        RequestEntity request = post(URI.create(serverBaseUrl + CLIENT_REGISTRY_BASE_PATH)).body(body);
        new RestTemplate().exchange(request, Void.class);
    }
}
