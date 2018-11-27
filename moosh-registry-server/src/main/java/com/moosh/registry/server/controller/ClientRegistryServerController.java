package com.moosh.registry.server.controller;

import com.moosh.registry.dto.ClientRegistry;
import com.moosh.registry.server.service.ClientRegistryService;
import com.moosh.registry.utils.MooshUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Slf4j
@RestController
@RequestMapping(MooshUtils.REGISTRY_SERVER_BASE_PATH)
public class ClientRegistryServerController {
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
