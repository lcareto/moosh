package com.moosh.registry.server.service;

import com.moosh.registry.dto.ClientRegistry;
import com.moosh.registry.dto.RequestMapping;
import com.moosh.registry.server.repository.MongoClientRegistryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class MongoClientRegistryService implements ClientRegistryService {

    private final MongoClientRegistryRepository repository;
    private final ZuulProperties zuulProperties;
    private final ZuulHandlerMapping zuulHandlerMapping;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public MongoClientRegistryService(MongoClientRegistryRepository repository,
                                      ZuulProperties zuulProperties,
                                      ZuulHandlerMapping zuulHandlerMapping,
                                      DiscoveryClient discoveryClient) {

        LOG.info("Using {}", MongoClientRegistryService.class.getName());
        this.repository = repository;
        this.zuulProperties = zuulProperties;
        this.zuulHandlerMapping = zuulHandlerMapping;
        this.discoveryClient = discoveryClient;
    }

    @Override
    public ClientRegistry save(ClientRegistry clientRegistry) {
        // TODO: 2018-11-26 Check if routes are not already mapped by another client

        Optional<ClientRegistry> target = repository.findById(clientRegistry.getId());
        if (target.isPresent()) {
            // TODO: 2018-11-26 Check client version to replace old version
            return target.get();
        }
        ClientRegistry saved = repository.save(clientRegistry);
        refreshRoutes(saved);
        return saved;
    }


    @Override
    public List<ClientRegistry> findAll() {
        return repository.findAll();
    }

    @Override
    public void unmap(String id) {
        repository.deleteById(id);
    }

    @Override
    public void refreshRoutes(ClientRegistry... clients) {
        for (ClientRegistry client : clients) {
            for (RequestMapping mapping : client.getRequestMappings()) {
                String uuid = UUID.randomUUID().toString();
                mapping.getPatterns().forEach(p -> {
                    ZuulProperties.ZuulRoute route = new ZuulProperties.ZuulRoute();
                    route.setId(uuid);
                    route.setPath(p);
                    route.setServiceId(client.getId());
                    route.setUrl(client.getUrl());
                    route.setStripPrefix(true);
                    route.setRetryable(true);
                    this.zuulProperties.getRoutes().put(uuid, route);
                });
            }
        }
        zuulHandlerMapping.setDirty(true);
    }
}
