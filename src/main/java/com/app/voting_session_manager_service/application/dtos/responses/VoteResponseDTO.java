package com.app.voting_session_manager_service.application.dtos.responses;

import com.app.voting_session_manager_service.domain.entities.enums.VoteClassification;

public record VoteResponseDTO(String associate, VoteClassification voteClassification) {
}
