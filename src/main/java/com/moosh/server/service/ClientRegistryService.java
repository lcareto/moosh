package com.moosh.server.service;

import com.moosh.server.dom.ClientRegistry;

import java.util.List;

public interface ClientRegistryService {

    List<ClientRegistry> findAll();

    ClientRegistry save(ClientRegistry clientRegistry);

    void unmap(String id);

}
