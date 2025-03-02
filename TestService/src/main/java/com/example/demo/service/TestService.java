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
import com.example.demo.api.QuestionClient;
import com.example.demo.api.RoleClient;
import com.example.demo.dto.CandidatDTO;
import com.example.demo.dto.CompetenceDTO;
import com.example.demo.dto.QuestionDTO;
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
    @Autowired
    private QuestionClient questionClient;


   public TestDTO createTest(TestDTO testDTO) {
    Test test = new Test();
    test.setName(testDTO.getName());
    test.setRoleId(testDTO.getRole().getId());
    test.setLevelId(testDTO.getLevel().getId());

    List<Long> candidateIds = testDTO.getCandidats().stream()
            .map(c -> candidatClient.addCandidate(c).getId())
            .collect(Collectors.toList());
    test.setCandidateIds(candidateIds);

    List<Long> competenceIds = testDTO.getCompetences().stream()
            .map(c -> competenceClient.addCompetence(c).getId())
            .collect(Collectors.toList());
    test.setCompetenceIds(competenceIds);

    // Ajouter seulement des IDs de questions existantes
    List<Long> questionIds = testDTO.getQuestions().stream()
            .map(QuestionDTO::getId)
            .collect(Collectors.toList());
    test.setQuestionIds(questionIds);

    Test savedTest = testRepository.save(test);

    return new TestDTO(
            savedTest.getId(),
            savedTest.getName(),
            candidateIds.stream().map(candidatClient::getCandidateById).collect(Collectors.toList()),
            roleClient.getRoleById(savedTest.getRoleId()),
            levelClient.getLevelById(savedTest.getLevelId()),
            competenceIds.stream().map(competenceClient::getCompetenceById).collect(Collectors.toList()),
            questionIds.stream().map(questionClient::getQuestionById).collect(Collectors.toList())
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

                List<QuestionDTO> questions = test.getQuestionIds().stream()
                        .map(questionClient::getQuestionById)
                        .collect(Collectors.toList());

                return new TestDTO(
                        test.getId(),
                        test.getName(),
                        candidates,
                        roleClient.getRoleById(test.getRoleId()),
                        levelClient.getLevelById(test.getLevelId()),
                        competences,
                        questions
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
    
                    List<QuestionDTO> questions = test.getQuestionIds().stream()
                            .map(questionClient::getQuestionById)
                            .collect(Collectors.toList());
    
                    return new TestDTO(
                            test.getId(),
                            test.getName(),
                            candidates,
                            roleClient.getRoleById(test.getRoleId()),
                            levelClient.getLevelById(test.getLevelId()),
                            competences,
                            questions
                    );
                })
                .orElse(null);
    }
    
}
