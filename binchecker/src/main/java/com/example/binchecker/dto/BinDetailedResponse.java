package com.example.binchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BinDetailedResponse {
    private Map<String, Object> number;
    private String brand;
    private Boolean prepaid;
    private Map<String, Object> country;
    private Map<String, String> bank;
    private String scheme;
    private String type;
}
