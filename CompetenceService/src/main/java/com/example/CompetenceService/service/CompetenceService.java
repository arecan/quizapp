package com.example.CompetenceService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CompetenceService.api.LevelClient;
import com.example.CompetenceService.api.RoleClient;
import com.example.CompetenceService.dto.CompetenceDTO;
import com.example.CompetenceService.model.Competence;
import com.example.CompetenceService.repository.CompetenceRepository;

@Service
public class CompetenceService {
    @Autowired
    CompetenceRepository competenceRepository;
    @Autowired
    private RoleClient roleClient;
    @Autowired
    private LevelClient levelClient;

    public List<CompetenceDTO> getCompetencesByRoleAndLevel(Long roleId, Long levelId) {
        List<Competence> competences = competenceRepository.findByRoleIdAndLevelId(roleId, levelId);
        return competences.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private CompetenceDTO convertToDTO(Competence competence) {
        CompetenceDTO dto = new CompetenceDTO();
        dto.setId(competence.getId());
        dto.setName(competence.getName());

        dto.setRole(roleClient.getRoleById(competence.getRoleId()));
        dto.setLevel(levelClient.getLevelById(competence.getLevelId()));

        return dto;
    }

}
