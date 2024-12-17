package com.app.voting_session_manager_service.factories;

import com.app.voting_session_manager_service.domain.entities.Vote;
import com.app.voting_session_manager_service.domain.entities.enums.VoteClassification;

import java.util.UUID;

public class VoteFactory {

    public static Vote sample() {
        return new Vote(
                UUID.randomUUID().toString(),
                "548.527.100-66",
                VoteClassification.SIM
        );
    }
}
