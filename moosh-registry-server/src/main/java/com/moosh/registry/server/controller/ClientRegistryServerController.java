package com.moosh.registry.server.controller;

import com.moosh.registry.dto.ClientRegistry;
import com.moosh.registry.server.service.ClientRegistryService;
import com.moosh.registry.utils.MooshUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public ClientRegistry register(@RequestBody ClientRegistry source, HttpServletRequest request) {
        LOG.info("Registering client [{}]", source.getId());
        if (StringUtils.isBlank(source.getUrl())) {
            // TODO: 2018-11-20 retrieve client base-url using strategy based on header Origin, X-Forwarded-For or Referer
        }
        return service.save(source);
    }

    @DeleteMapping("/{id}")
    public void unmap(@PathVariable String id) {
        service.unmap(id);
    }
}
