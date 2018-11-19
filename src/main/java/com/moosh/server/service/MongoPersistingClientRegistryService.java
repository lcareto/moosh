package com.moosh.server.service;

import com.moosh.server.dom.ClientRegistry;
import com.moosh.server.repository.MongoClientRegistryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MongoPersistingClientRegistryService implements ClientRegistryService {
    private final MongoClientRegistryRepository repository;

    @Autowired
    public MongoPersistingClientRegistryService(MongoClientRegistryRepository repository) {
        LOG.info("Using {}", MongoPersistingClientRegistryService.class.getName());
        this.repository = repository;
    }

    @Override
    public ClientRegistry save(ClientRegistry clientRegistry) {
        return repository.findById(clientRegistry.getId())
                .orElseGet(() -> repository.save(clientRegistry));
    }

    @Override
    public List<ClientRegistry> findAll() {
        return repository.findAll();
    }

    @Override
    public void unmap(String id) {
        repository.deleteById(id);
    }
}
