package com.app.voting_session_manager_service.factories;

import com.app.voting_session_manager_service.domain.entities.Rulling;

import java.util.ArrayList;

public class RullingFactory {
    public static Rulling sample() {
        return new Rulling.Builder()
                .setTitle("Rulling title")
                .setDescription("Rulling description")
                .setVotesList(new ArrayList<>())
                .build();
    }
}
