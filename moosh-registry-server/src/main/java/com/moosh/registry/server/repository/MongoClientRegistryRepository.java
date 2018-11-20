package com.moosh.registry.server.repository;


import com.moosh.registry.dto.ClientRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoClientRegistryRepository extends MongoRepository<ClientRegistry, String> {

}
