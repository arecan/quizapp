package com.example.ThemeService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ThemeService.dto.ThemeDTO;
import com.example.ThemeService.service.ThemeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/theme")
public class ThemeController {
    @Autowired
    ThemeService themeService;

    @GetMapping("/{id}")
    public ThemeDTO getThemeById(@PathVariable Long id) {
        return themeService.getThemeById(id);
    }
   
    
}
