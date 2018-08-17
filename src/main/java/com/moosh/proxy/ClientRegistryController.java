package com.moosh.proxy;

import com.moosh.dto.ClientRegistryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@RestController
@RequestMapping("/clients")
public class ClientRegistryController {


  @PostMapping
  public ResponseEntity register(@RequestBody ClientRegistryRequest clientRegistryRequest) {
    return ResponseEntity.ok("Service registered");
  }
}
