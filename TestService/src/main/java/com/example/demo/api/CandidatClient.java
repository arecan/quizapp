package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.CandidatDTO;

@FeignClient(name = "CandidatService", url = "http://localhost:8071")
public interface CandidatClient {

    @GetMapping("/candidat/{id}")
    CandidatDTO getCandidateById(@PathVariable Long id);

    @PostMapping("/candidat")
    CandidatDTO addCandidate(@RequestBody CandidatDTO CandidatDTO);
    
    
}
