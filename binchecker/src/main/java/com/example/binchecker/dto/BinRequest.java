package com.example.binchecker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BinRequest {

    @Column(nullable = false)
    @Size(min = 6, max = 8, message = "You must enter the first eight digit of your PAN")
    private String bin;

}
