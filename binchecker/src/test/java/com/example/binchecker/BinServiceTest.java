package com.example.binchecker;

import com.example.binchecker.dto.BinResponse;
import com.example.binchecker.model.Bin;
import com.example.binchecker.repository.BinRepository;
import com.example.binchecker.service.BinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BinServiceTest {

    @Mock
    BinRepository binRepository;

    @InjectMocks
    BinService binService;

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

    @Test
    public void shouldSaveBinSuccessfully() throws Exception {
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
        BinResponse savedBin = binService.postBin("457173");
        Map<String, Integer> actualBins = binService.getBinsDetails();
        assertEquals(2, actualBins.size());
    }

}