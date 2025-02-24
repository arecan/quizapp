package com.example.answer.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.answer.dto.CandidatDTO;



@FeignClient(name = "CandidatService", url = "http://localhost:8081")
public interface CandidatClient {

    @GetMapping("/candidat/{id}")
    CandidatDTO getCandidatById(@PathVariable Long id);
    
    
}
