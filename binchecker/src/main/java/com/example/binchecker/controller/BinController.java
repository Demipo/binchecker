package com.example.binchecker.controller;

import com.example.binchecker.apiresponse.ApiResponse;
import com.example.binchecker.dto.BinRequest;
import com.example.binchecker.dto.BinResponse;
import com.example.binchecker.model.Bin;
import com.example.binchecker.service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bin")
public class BinController {

    @Autowired
    private BinService service;

    /**
     * End point that retrieves the details of bin/iin
     *
     * @return Response entity contains of vehicles with same model
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<BinResponse>> postBin(@Valid @RequestBody BinRequest request) throws Exception {
        ApiResponse<BinResponse> ar = new ApiResponse<BinResponse>();
        BinResponse br = service.postBin(request.getBin());
        ar.setPayload(br);
        ar.setSuccess(true);
        return new ResponseEntity<>(ar, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/admin", method =  RequestMethod.GET)
    public ResponseEntity<?> getBinDetails(){
        ApiResponse<List<Bin>> ar = new ApiResponse<>();
        List<Bin> br = service.getBinDetails();
        ar.setPayload(br);
        ar.setSuccess(true);
        return new ResponseEntity<>(ar, HttpStatus.OK);
    }

}
