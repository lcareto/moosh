package com.moosh.registry.server.service;

import com.moosh.registry.dto.ClientRegistry;

import java.util.List;

public interface ClientRegistryService {

    List<ClientRegistry> findAll();

    ClientRegistry save(ClientRegistry clientRegistry);

    void unmap(String id);

    void refreshRoutes(ClientRegistry... clients);

}
