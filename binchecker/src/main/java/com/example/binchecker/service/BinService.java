package com.example.binchecker.service;

import com.example.binchecker.dto.BinDetailedResponse;
import com.example.binchecker.dto.BinResponse;
import com.example.binchecker.exception.ResourceNotFoundException;
import com.example.binchecker.model.Bin;
import com.example.binchecker.repository.BinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BinService {

    @Autowired
    private BinRepository repository;

    public BinResponse postBin(String bin) throws Exception {
        ResponseEntity<BinDetailedResponse> re;
        String sixDigitBin = bin.substring(0,6);

        Map<String, String> uriVariable = new HashMap<>();
        uriVariable.put("bin", bin);
        try{
            re = new RestTemplate().getForEntity("https://lookup.binlist.net/{bin}", BinDetailedResponse.class, uriVariable);
        }catch(Exception ex){
            throw new ResourceNotFoundException("Invalid BIN/IIN");
        }
        if(re.getStatusCode().is2xxSuccessful()){
            Bin b = repository.findByBin(sixDigitBin);
            if(b != null){
                b.setCheckCounter(b.getCheckCounter() + 1);
                b.setTime(ZonedDateTime.now());
                repository.save(b);
            }
            else{
                Bin newBin = new Bin();
                newBin.setCheckCounter(1);
                newBin.setBin(sixDigitBin);
                newBin.setTime(ZonedDateTime.now());
                repository.save(newBin);
            }
        }

        BinResponse br = new BinResponse();
        br.setScheme(re.getBody().getScheme());
        br.setBank(re.getBody().getBank().get("name"));
        br.setType(re.getBody().getType());

        return br;
    }

    public Map<String, Integer> getBinDetails() {
        List<Bin> binList = repository.findAll();
        Map<String, Integer> payload = new HashMap<>();
        for(Bin bin : binList){
            payload.put(bin.getBin(), bin.getCheckCounter());
        }
        return payload;
    }
}
