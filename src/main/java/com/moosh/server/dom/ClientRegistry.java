package com.moosh.server.dom;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 16/08/2018
 *
 * @author lyrold
 */
@Data
@Entity
@EqualsAndHashCode(of = "id")
public class ClientRegistry {

    @Id
    private String id;

    @Embedded
    private Set<RequestMapping> requestMappings = new HashSet<>();

}
