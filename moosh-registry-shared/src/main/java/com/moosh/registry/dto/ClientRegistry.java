package com.moosh.registry.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

/**
 * Created on 16/08/2018
 *
 * @author lyrold
 */
@Data
@EqualsAndHashCode(of = {"id", "version"})
public class ClientRegistry {

    protected String id;
    protected String name;
    protected String version;
    protected String url;
    protected Set<RequestMapping> requestMappings = new HashSet<>();

    public String getName() {
        return isNull(name) || name.isEmpty() ? id : name;
    }
}
