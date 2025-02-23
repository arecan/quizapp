package com.example.demo.service;

import com.example.demo.repositories.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.api.CandidatClient;
import com.example.demo.api.LevelClient;
import com.example.demo.api.RoleClient;
import com.example.demo.dto.CandidatDTO;
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


    public TestDTO createTest(TestDTO testDTO) {
        Test test = new Test();
        test.setName(testDTO.getName());
        test.setRoleId(testDTO.getRole().getId());  // Use RoleDTO ID
        test.setLevelId(testDTO.getLevel().getId()); // Use LevelDTO ID

        List<Long> candidateIds = new ArrayList<>();
        List<CandidatDTO> addedCandidates = testDTO.getCandidates().stream()
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
        Test savedTest = testRepository.save(test);

        return new TestDTO(
                savedTest.getId(),
                savedTest.getName(),
                addedCandidates,
                roleClient.getRoleById(savedTest.getRoleId()),
                levelClient.getLevelById(savedTest.getLevelId())
        );
    }

    public List<TestDTO> getAllTests() {
        List<Test> tests = testRepository.findAll();

        return tests.stream()
                .map(test -> {
                    List<CandidatDTO> candidates = test.getCandidateIds().stream()
                            .map(candidatClient::getCandidateById)
                            .collect(Collectors.toList());

                    return new TestDTO(
                            test.getId(),
                            test.getName(),
                            candidates,
                            roleClient.getRoleById(test.getRoleId()), // Fetch Role Details
                            levelClient.getLevelById(test.getLevelId()) // Fetch Level Details
                    );
                })
                .collect(Collectors.toList());
    }

    // Récupérer un test avec la liste des candidats associés
    public TestDTO getTestById(Long testId) {
        return testRepository.findById(testId)
                .map(test -> {
                    List<CandidatDTO> candidates = test.getCandidateIds().stream()
                            .map(candidatClient::getCandidateById)
                            .collect(Collectors.toList());

                    return new TestDTO(
                            test.getId(),
                            test.getName(),
                            candidates,
                            roleClient.getRoleById(test.getRoleId()), // Fetch Role Details
                            levelClient.getLevelById(test.getLevelId()) // Fetch Level Details
                    );
                })
                .orElse(null);
    }
}
