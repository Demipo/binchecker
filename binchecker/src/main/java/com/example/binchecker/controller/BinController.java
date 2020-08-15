package com.example.binchecker.controller;

import com.example.binchecker.dto.BinRequest;
import com.example.binchecker.dto.BinResponse;
import com.example.binchecker.model.Bin;
import com.example.binchecker.service.BinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    @RequestMapping(path = "/", method = RequestMethod.POST)
    public ResponseEntity<?> postBin(@Valid @RequestBody BinRequest request){
        System.out.println(request);
        BinResponse br = service.postBin(request.getBin());
        return new ResponseEntity<>(br, HttpStatus.OK);
    }

}
