package com.example.CompetenceService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CompetenceService.dto.CompetenceDTO;
import com.example.CompetenceService.service.CompetenceService;

@RestController
@RequestMapping("competences")
public class CompetenceController {

    @Autowired
    CompetenceService competenceService;

    @GetMapping("/role/{roleId}/level/{levelId}")
    public List<CompetenceDTO> getCompetencesByRoleAndLevel(
            @PathVariable Long roleId, @PathVariable Long levelId) {
        return competenceService.getCompetencesByRoleAndLevel(roleId, levelId);
    }

}
