package com.moosh.registry.client;

import com.moosh.registry.configuration.MooshRegistryConfiguration;
import com.moosh.registry.dto.ClientRegistry;
import com.moosh.registry.dto.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.stream.Collectors;

import static com.moosh.registry.utils.MooshUtils.REGISTRY_SERVER_BASE_PATH;
import static org.springframework.http.RequestEntity.post;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Slf4j
@Configuration
//@EnableDiscoveryClient // TODO: 2018-11-20 Enable when adding a service discovery in classpath
@Import({MooshRegistryConfiguration.class})
public class ClientRegistryInitializer implements ApplicationListener<ServletWebServerInitializedEvent> {

    private MooshRegistryConfiguration configuration;
    private RequestMappingHandlerMapping handlerMapping;

    @Value("${server.port}")
    private int port;

    @Autowired
    public ClientRegistryInitializer(MooshRegistryConfiguration configuration,
                                     RequestMappingHandlerMapping handlerMapping) {
        this.configuration = configuration;
        this.handlerMapping = handlerMapping;
    }

    @PostConstruct
    public void check() {
        // TODO: 2018-11-21 Check if required configuration is present
    }

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        ClientRegistry client = configuration.getClient();
        LOG.info("Registering client [{}]", client.getName());

        // Prepare Registry request
        String clientBaseUrl = null;
        if (event.getWebServer() instanceof TomcatWebServer) {
            Tomcat tomcat = ((TomcatWebServer) event.getWebServer()).getTomcat();
            String scheme = tomcat.getConnector().getScheme();
            clientBaseUrl = scheme + "://" + tomcat.getServer().getAddress() + ":" + port;
        }
        client.setUrl(clientBaseUrl);
        client.setRequestMappings(handlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .map(m -> RequestMapping.from(m.getKey()))
                .collect(Collectors.toSet())
        );

        // Post registry request
        String serverBaseUrl = configuration.getServer().getUrl();
        RequestEntity request = post(URI.create(serverBaseUrl + REGISTRY_SERVER_BASE_PATH)).body(client);
        new RestTemplate().exchange(request, Void.class);
    }
}
