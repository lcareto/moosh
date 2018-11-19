package com.moosh.server.controller;

import com.moosh.server.dom.ClientRegistry;
import com.moosh.server.service.ClientRegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Slf4j
@RestController
@RequestMapping(ClientRegistryServerController.CLIENT_REGISTRY_BASE_PATH)
public class ClientRegistryServerController {
    public static final String CLIENT_REGISTRY_BASE_PATH = "/moosh/clients";
    private final ClientRegistryService service;

    @Autowired
    public ClientRegistryServerController(ClientRegistryService service) {
        this.service = service;
    }

    @GetMapping
    public List<ClientRegistry> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ClientRegistry register(@RequestBody ClientRegistry source) {
        LOG.info("Registering client [{}]", source.getId());
        return service.save(source);
    }

    @DeleteMapping("/{id}")
    public void unmap(@PathVariable String id) {
        service.unmap(id);
    }
}
