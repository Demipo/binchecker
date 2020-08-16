package com.example.binchecker;

import com.example.binchecker.dto.BinDetailedResponse;
import com.example.binchecker.dto.BinResponse;
import com.example.binchecker.model.Bin;
import com.example.binchecker.repository.BinRepository;
import com.example.binchecker.service.BinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BinServiceTest {

    @Mock
    BinRepository binRepository;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    BinService binService;
    private String url = "/bin";

    public BinResponse getBr(){
        BinResponse br = new BinResponse();
        br.setType("debit");
        br.setBank("FIRST");
        br.setScheme("visa");
        return br;
    }

    public Bin getBin(){
        Bin bin = new Bin();
        bin.setId(1L);
        bin.setCheckCounter(2);
        bin.setBin("457173");
        bin.setTime(ZonedDateTime.now());
        return bin;
    }

    public Bin getBin2(){
        Bin bin = new Bin();
        bin.setId(2L);
        bin.setCheckCounter(3);
        bin.setBin("357173");
        bin.setTime(ZonedDateTime.now());
        return bin;
    }

//    public BinDetailedResponse getBdr(){
//        BinDetailedResponse bdr = new BinDetailedResponse();
//        Map<String, Object> number = new HashMap<>();
//        Map<String, Object> country = new HashMap<>();
//        Map<String, String> bank = new HashMap<>();
//        number.put("length", 16);
//        number.put("luhn", true);
//        country.put("numeric", "208");
//        country.put("alpha2", "DK");
//        country.put("name", "Denmark");
//        country.put("emoji", "🇩🇰");
//        country.put("currency", "DKK");
//        country.put("latitude", 56);
//        country.put("longitude", 10);
//        bank.put("name", "Jyske Bank");
//        bank.put("url", "www.jyskebank.dk");
//        bank.put("phone", "+4589893300");
//        bank.put("city", "Hjørring");
//        bdr.setBank(bank);
//        bdr.setBrand("Visa/Dankort");
//        bdr.setCountry(country);
//        bdr.setNumber(number);
//        bdr.setPrepaid(false);
//        bdr.setScheme("visa");
//        bdr.setType("debit");
//        return bdr;
//    }

    @Test
    public void shouldSaveBinSuccessfully() throws Exception {
//        when(restTemplate.getForEntity(eq("https://lookup.binlist.net/457173"), any(), anyIterable())).thenReturn(new ResponseEntity(getBdr(), HttpStatus.OK));
        when(binRepository.findByBin(anyString())).thenReturn(getBin());
        when(binRepository.save(any(Bin.class))).thenAnswer(invocation -> invocation.getArgument(0));
        BinResponse savedBin = binService.postBin("457173");
        assertNotNull(savedBin);
        verify(binRepository).save(any(Bin.class));
    }

    @Test
    public void shouldFetchBinsDetailsSuccessfully() throws Exception {
        List<Bin> binList = new ArrayList<>();
        binList.add(getBin());
        binList.add(getBin2());

        when(binRepository.findAll()).thenReturn(binList);
//        when(binRepository.save(any(Bin.class))).thenAnswer(invocation -> invocation.getArgument(0));
        BinResponse savedBin = binService.postBin("457173");
        Map<String, Integer> actualBins = binService.getBinsDetails();
        assertEquals(2, actualBins.size());
//        assertNotNull(savedBin);
//        verify(binRepository).save(any(Bin.class));
    }

}