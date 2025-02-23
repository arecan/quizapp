package com.example.demo.service;

import com.example.demo.repositories.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.CandidatClient;
import com.example.demo.dto.CandidatDTO;
import com.example.demo.dto.TestDTO;
import com.example.demo.model.*;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private CandidatClient candidatClient;

    public TestDTO createTest(TestDTO testDTO) {
        Test test = new Test();
        test.setName(testDTO.getName());
        test.setRoleId(testDTO.getRoleId());
        test.setLevelId(testDTO.getLevelId());      

        // Liste pour stocker les IDs des candidats ajoutés
        List<Long> candidateIds = new ArrayList<>();

        // Parcourir la liste des candidats et les ajouter via le service candidat
        List<CandidatDTO> addedCandidates = testDTO.getCandidates().stream()
                .map(candidatDTO -> {
                    CandidatDTO addedCandidate = candidatClient.addCandidate(candidatDTO);
                    if (addedCandidate != null && addedCandidate.getId() != null) {
                        candidateIds.add(addedCandidate.getId()); // Ajouter l'ID récupéré
                        return addedCandidate;
                    } else {
                        throw new RuntimeException("Erreur lors de l'ajout du candidat : " + candidatDTO.getName());
                    }
                })
                .collect(Collectors.toList());

        // Associer les IDs récupérés au test
        test.setCandidateIds(candidateIds);

        // Sauvegarde du test avec les IDs des candidats
        Test savedTest = testRepository.save(test);

        // Retourner le TestDTO avec les candidats mis à jour
        return new TestDTO(
                savedTest.getId(),
                savedTest.getName(),
                addedCandidates,
                savedTest.getRoleId(),
                savedTest.getLevelId());
    }

    public List<TestDTO> getAllTests() {
        List<Test> tests = testRepository.findAll();
    
        // Convert List<Test> to List<TestDTO>
        return tests.stream()
                .map(test -> {
                    List<CandidatDTO> candidates = test.getCandidateIds().stream()
                            .map(candidatClient::getCandidateById)
                            .collect(Collectors.toList());
                    return new TestDTO(test.getId(), test.getName(), candidates, test.getRoleId(), test.getLevelId());
                })
                .collect(Collectors.toList());
    }

    // Récupérer un test avec la liste des candidats associés
    public TestDTO getTestById(Long testId) {
        return testRepository.findById(testId)
                .map(test -> {
                    List<CandidatDTO> candidates = test.getCandidateIds().stream()
                            .map(candidatClient::getCandidateById) // Appel direct
                            .collect(Collectors.toList());

                    return new TestDTO(test.getId(), test.getName(), candidates, test.getRoleId(), test.getLevelId());
                })
                .orElse(null);
    }
}
