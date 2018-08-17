package com.moosh.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

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
    LOG.info("Initialize client [{}]", configuration.getClient().getName());

    // Service identity
    //// id
    //// name
    //// hostname
    //// controllers


    // Post registry request
    /*ClientRegistryRequest body = new ClientRegistryRequest();
    String gatewayBaseUrl = configuration.getGateway().getUrl();
    RequestEntity request = RequestEntity.post(URI.create(gatewayBaseUrl + "/clients")).body(body);
    ResponseEntity<Object> response = new RestTemplate().exchange(request, Object.class);

    LOG.info("Client successfully registered on Gateway [{}]", gatewayBaseUrl);*/
  }
}
