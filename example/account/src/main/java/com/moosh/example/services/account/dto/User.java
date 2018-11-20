package com.moosh.example.services.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 25/07/2018
 *
 * @author lyrold
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private String id;
  private String firstName;
  private String lastName;
}
