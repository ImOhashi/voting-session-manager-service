package com.app.voting_session_manager_service.domain.entities.enums;

public enum VoteClassification {
    SIM("Sim"),
    NAO("Não");

    private final String description;

    VoteClassification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

