package com.example.binchecker;

import com.example.binchecker.dto.BinResponse;
import com.example.binchecker.service.BinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = com.example.binchecker.controller.BinController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        WebSecurityConfigurer.class, Filter.class }), excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class BinControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BinService binService;

    private ObjectMapper mapper = new ObjectMapper();
    private String url = "/bin";

    public BinResponse getBinResponse(){
        BinResponse br = new BinResponse();
        br.setType("debit");
        br.setBank("FIRST");
        br.setScheme("visa");
        return br;
    }

    @Test
    public void shouldFetchBinDetails() throws Exception{
        BinResponse br = getBinResponse();
        when(binService.postBin(any())).thenReturn(br);
        String brString = mapper.writeValueAsString(br);
        this.mvc
                .perform((post("/bin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brString))
                .andExpect((jsonPath("$.payload.bank", is("FIRST"))))
                .andExpect(jsonPath("$.payload.type", is("debit")))
                .andExpect(jsonPath("$.payload.scheme", is("visa")))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldFetchBinsFrequency() throws Exception{
        Map<String, Integer> payload = new HashMap<>();
        payload.put("539923", 2);
        payload.put("457173", 4);
        when(binService.getBinsDetails()).thenReturn(payload);
        String payloadString = mapper.writeValueAsString(payload);
        this.mvc
                .perform((get("/bin/admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadString))
                .andExpect((jsonPath("$.payload.539923", is(2))))
                .andExpect(jsonPath("$.payload.457173", is(4)))
                .andExpect(jsonPath("$.start", is(1)))
                .andExpect(jsonPath("$.limit", is(3)))
                .andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(status().isOk());
    }

}