package com.moosh.server.repository;

import com.moosh.server.dom.ClientRegistry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRegistryRepository extends JpaRepository<ClientRegistry, String> {
// TODO: 2018-11-19 Add support for SQL store
}
