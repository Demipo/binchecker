package com.example.binchecker.apiresponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponseAdmin<T> extends ApiResponse{

    private Integer start;
    private Integer limit;
    private Integer size;

    public ApiResponseAdmin(Integer start, Integer limit, Integer size) {
        this();
        this.start = start;
        this.limit = limit;
        this.size = size;
    }
}
