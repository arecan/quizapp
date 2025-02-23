package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.RoleDTO;

@FeignClient(name = "LevelService", url = "http://localhost:8083")
public interface LevelClient {

    @GetMapping("/level/{id}")
    RoleDTO getLevelById(@PathVariable Long id);
    
}