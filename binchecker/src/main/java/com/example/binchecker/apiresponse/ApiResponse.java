package com.example.binchecker.apiresponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T> {

  private boolean success;
  private T payload;

}
