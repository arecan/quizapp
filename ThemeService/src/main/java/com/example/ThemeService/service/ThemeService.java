package com.example.ThemeService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ThemeService.dto.ThemeDTO;
import com.example.ThemeService.model.Theme;
import com.example.ThemeService.repository.ThemeRepository;

@Service
public class ThemeService {
    @Autowired
    ThemeRepository themeRepository;

    public ThemeDTO entityToDTO(Theme theme) {
        if (theme == null) {
            return null;
        }

        ThemeDTO dto = new ThemeDTO();
        dto.setId(theme.getId());
        dto.setName(theme.getName());

        return dto;
    }

    public ThemeDTO getThemeById(Long id){
        return entityToDTO(themeRepository.findById(id).get());
    }

}
