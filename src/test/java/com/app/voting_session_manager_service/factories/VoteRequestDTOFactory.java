package com.app.voting_session_manager_service.factories;

import com.app.voting_session_manager_service.application.dtos.requests.VoteRequestDTO;
import com.app.voting_session_manager_service.domain.entities.enums.VoteClassification;

public class VoteRequestDTOFactory {
    public static VoteRequestDTO sample() {
        return new VoteRequestDTO(
                "548.527.100-66",
                "Teste",
                VoteClassification.SIM
        );
    }
}
