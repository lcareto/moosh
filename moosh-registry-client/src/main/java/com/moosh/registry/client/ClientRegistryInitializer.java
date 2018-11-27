package com.moosh.registry.client;

import static com.moosh.registry.utils.MooshUtils.REGISTRY_SERVER_BASE_PATH;
import static java.util.Objects.isNull;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.isEmpty;
import com.moosh.registry.configuration.MooshRegistryConfiguration;
import com.moosh.registry.dto.ClientRegistry;
import com.moosh.registry.dto.RequestMapping;
import java.net.InetAddress;
import java.net.URI;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.RequestEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Slf4j
@Configuration
@Import({MooshRegistryConfiguration.class})
public class ClientRegistryInitializer implements ApplicationListener<ServletWebServerInitializedEvent> {

    private final MooshRegistryConfiguration configuration;
    private final RequestMappingHandlerMapping handlerMapping;
    private final CompositeDiscoveryClient discoveryClient;

    @Value("${server.port}")
    private int port;

    @Autowired
    public ClientRegistryInitializer(MooshRegistryConfiguration configuration,
                                     RequestMappingHandlerMapping handlerMapping,
                                     CompositeDiscoveryClient discoveryClient) {
        this.configuration = configuration;
        this.handlerMapping = handlerMapping;
        this.discoveryClient = discoveryClient;
    }

    @PostConstruct
    public void check() {
        ClientRegistry client = configuration.getClient();
        Assert.notNull(client, "Client configuration is missing");
        Assert.hasText(client.getId(), "Client id is missing in configuration");
    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        ClientRegistry client = configuration.getClient();
        LOG.info("Registering client [{}]", client.getName());

        MooshRegistryConfiguration.Server server = configuration.getServer();
        if (isNull(server) || isEmpty(server.getUrl()) && !hasDiscoveryInstance(server.getId())) {
            LOG.info("Skipping client {} registration due to missing server configuration {}", client.getName(), server);
        } else {
            // Prepare Registry request
            // Build Url only if service cannot be discovered by Id; exclude instance of SimpleDiscoveryClient
            if (discoveryClient.getDiscoveryClients().stream().allMatch(c -> c instanceof SimpleDiscoveryClient)) {
                String scheme = "http";
                String hostname = InetAddress.getLoopbackAddress().getHostName();
                if (event.getWebServer() instanceof TomcatWebServer) {
                    Tomcat tomcat = ((TomcatWebServer) event.getWebServer()).getTomcat();
                    scheme = tomcat.getConnector().getScheme();
                }

                client.setUrl(scheme + "://" + hostname + ":" + port);
            }

            client.setRequestMappings(handlerMapping.getHandlerMethods()
                    .entrySet()
                    .stream()
                    .map(m -> RequestMapping.from(m.getKey()))
                    .collect(Collectors.toSet())
            );

            // Post registry request
            String requestUrl = !isEmpty(server.getUrl()) ?
                    server.getUrl() :
                    discoveryClient.getInstances(server.getId()).get(0).getUri().toString();
            RequestEntity request = post(URI.create(requestUrl + REGISTRY_SERVER_BASE_PATH)).body(client);
            try {
                new RestTemplate().exchange(request, Void.class);
                LOG.info("Client successfully registered");
            } catch (HttpClientErrorException e) {
                throw new RuntimeException("An error has occurred service registration", e);
            }
        }
    }

    private boolean hasDiscoveryInstance(String id) {
        return !isEmpty(id) && !isEmpty(discoveryClient.getInstances(id));
    }
}
