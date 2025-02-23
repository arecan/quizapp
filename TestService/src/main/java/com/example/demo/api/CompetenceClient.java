package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "CompetenceService", url = "http://localhost:8089")
public interface CompetenceClient {

    
}
