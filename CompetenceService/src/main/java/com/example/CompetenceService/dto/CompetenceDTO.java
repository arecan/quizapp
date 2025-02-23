package com.example.CompetenceService.dto;

public class CompetenceDTO {
    private Long id;
    private String name;
    private RoleDTO role;
    private LevelDTO level;

    public CompetenceDTO() {
    }

    public CompetenceDTO(Long id, String name, RoleDTO role, LevelDTO level) {
        this.id = id;
        this.name = name;
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