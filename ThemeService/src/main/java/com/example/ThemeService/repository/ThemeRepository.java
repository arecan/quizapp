package com.example.ThemeService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.ThemeService.model.Theme;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, Long>{
    
}
