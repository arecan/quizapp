package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CandidatDTO;
import com.example.demo.model.Candidat;
import com.example.demo.repository.candidatRepository;

@Service
public class CandidatImpl implements CandidatService{

    @Autowired
    private candidatRepository candidatRepo;

    @Override
    public void createCandidat(Candidat candidat) {
        candidatRepo.save(candidat);
    }

    @Override
    public List<Candidat> getAllCandidat() {
        return (List<Candidat>) candidatRepo.findAll();
    }

    public CandidatDTO entityToDTO(Candidat candidat) {
        if (candidat == null) {
            return null;
        }

        CandidatDTO dto = new CandidatDTO();
        dto.setId(candidat.getId());
        dto.setName(candidat.getName());
        dto.setEmail(candidat.getEmail());

        return dto;
    }

    public CandidatDTO addCandidat(CandidatDTO candidateDTO) {
        Candidat candidat = new Candidat();
        candidat.setName(candidateDTO.getName());
        candidat.setEmail(candidateDTO.getEmail());

        Candidat savedCandidat = candidatRepo.save(candidat);

        // Retourner le DTO avec l'ID généré
        return new CandidatDTO(savedCandidat.getId(), savedCandidat.getName(), savedCandidat.getEmail());
    }

    @Override
    public CandidatDTO getCandidatById(Long id) {
        return this.entityToDTO(candidatRepo.findById(id).get()) ;
    }
    
}
