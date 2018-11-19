package com.moosh.server.repository;


import com.moosh.server.dom.ClientRegistry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoClientRegistryRepository extends MongoRepository<ClientRegistry, String> {

}
