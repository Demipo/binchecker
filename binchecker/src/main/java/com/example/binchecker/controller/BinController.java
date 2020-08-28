package com.example.binchecker.controller;

import com.example.binchecker.apiresponse.ApiResponse;
import com.example.binchecker.apiresponse.ApiResponseAdmin;
import com.example.binchecker.dto.BinRequest;
import com.example.binchecker.dto.BinResponse;
import com.example.binchecker.service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class BinController {

    @Autowired
    private BinService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> home(){
        String msg = "Welcome to BinChecker";
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(path = "/bin", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<BinResponse>> postBin(@Valid @RequestBody BinRequest request) throws Exception {
        ApiResponse<BinResponse> ar = new ApiResponse<BinResponse>();
        BinResponse br = service.postBin(request.getBin());
        ar.setPayload(br);
        ar.setSuccess(true);
        return new ResponseEntity<>(ar, HttpStatus.CREATED);
    }

    @RequestMapping(path = "bin/admin/bins-details", method =  RequestMethod.GET)
    public ResponseEntity<?> getBinsDetails(){
        ApiResponseAdmin< Map<String, Integer>> ar = new ApiResponseAdmin<>();
        Map<String, Integer> br = service.getBinsDetails();
        ar.setPayload(br);
        ar.setSuccess(true);
        ar.setSize(br.size());
        ar.setLimit(3);
        ar.setStart(1);
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
