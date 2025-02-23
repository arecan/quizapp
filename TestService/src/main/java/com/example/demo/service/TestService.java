package com.example.demo.service;

import com.example.demo.repositories.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.CandidatClient;
import com.example.demo.api.CompetenceClient;
import com.example.demo.api.LevelClient;
import com.example.demo.api.RoleClient;
import com.example.demo.dto.CandidatDTO;
import com.example.demo.dto.CompetenceDTO;
import com.example.demo.dto.TestDTO;
import com.example.demo.model.*;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private CandidatClient candidatClient;
    @Autowired
    private RoleClient roleClient;
    @Autowired
    private LevelClient levelClient;
    @Autowired
    private CompetenceClient competenceClient;

    public TestDTO createTest(TestDTO testDTO) {
        Test test = new Test();
        test.setName(testDTO.getName());
        test.setRoleId(testDTO.getRole().getId());
        test.setLevelId(testDTO.getLevel().getId());

        List<Long> candidateIds = new ArrayList<>();
        List<CandidatDTO> addedCandidates = testDTO.getCandidats().stream()
                .map(candidatDTO -> {
                    CandidatDTO addedCandidate = candidatClient.addCandidate(candidatDTO);
                    if (addedCandidate != null && addedCandidate.getId() != null) {
                        candidateIds.add(addedCandidate.getId());
                        return addedCandidate;
                    } else {
                        throw new RuntimeException("Erreur lors de l'ajout du candidat : " + candidatDTO.getName());
                    }
                })
                .collect(Collectors.toList());

        test.setCandidateIds(candidateIds);

        List<Long> competenceIds = new ArrayList<>();
        List<CompetenceDTO> addedCompetences = testDTO.getCompetences().stream()
                .map(competenceDTO -> {
                    CompetenceDTO addedCompetence = competenceClient.addCompetence(competenceDTO);
                    if (addedCompetence != null && addedCompetence.getId() != null) {
                        competenceIds.add(addedCompetence.getId());
                        return addedCompetence;
                    } else {
                        throw new RuntimeException("Erreur lors de l'ajout de la compétence : " + competenceDTO.getName());
                    }
                })
                .collect(Collectors.toList());

        test.setCompetenceIds(competenceIds);

        Test savedTest = testRepository.save(test);

        return new TestDTO(
                savedTest.getId(),
                savedTest.getName(),
                addedCandidates,
                roleClient.getRoleById(savedTest.getRoleId()),
                levelClient.getLevelById(savedTest.getLevelId()),
                addedCompetences
        );
    }

    public List<TestDTO> getAllTests() {
        List<Test> tests = testRepository.findAll();

        return tests.stream()
                .map(test -> {
                    List<CandidatDTO> candidates = test.getCandidateIds().stream()
                            .map(candidatClient::getCandidateById)
                            .collect(Collectors.toList());

                    List<CompetenceDTO> competences = test.getCompetenceIds().stream()
                            .map(competenceClient::getCompetenceById)
                            .collect(Collectors.toList());

                    return new TestDTO(
                            test.getId(),
                            test.getName(),
                            candidates,
                            roleClient.getRoleById(test.getRoleId()),
                            levelClient.getLevelById(test.getLevelId()),
                            competences
                    );
                })
                .collect(Collectors.toList());
    }

    public TestDTO getTestById(Long testId) {
        return testRepository.findById(testId)
                .map(test -> {
                    List<CandidatDTO> candidates = test.getCandidateIds().stream()
                            .map(candidatClient::getCandidateById)
                            .collect(Collectors.toList());

                    List<CompetenceDTO> competences = test.getCompetenceIds().stream()
                            .map(competenceClient::getCompetenceById)
                            .collect(Collectors.toList());

                    return new TestDTO(
                            test.getId(),
                            test.getName(),
                            candidates,
                            roleClient.getRoleById(test.getRoleId()),
                            levelClient.getLevelById(test.getLevelId()),
                            competences
                    );
                })
                .orElse(null);
    }
}
