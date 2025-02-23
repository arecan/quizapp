package com.example.demo.dto;

import java.util.List;

public class TestDTO {
    private Long id;
    private String name;
    private List<CandidatDTO> candidates;
    private RoleDTO role;
    private LevelDTO level;

    public TestDTO() {
    }

    public TestDTO(Long id, String name, List<CandidatDTO> candidates, RoleDTO role, LevelDTO level) {
        this.id = id;
        this.name = name;
        this.candidates = candidates;
        this.role = role;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CandidatDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidatDTO> candidates) {
        this.candidates = candidates;
    }

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public LevelDTO getLevel() {
        return level;
    }

    public void setLevel(LevelDTO level) {
        this.level = level;
    }

}
