package com.app.voting_session_manager_service.application.dtos.requests;

import com.app.voting_session_manager_service.domain.entities.enums.VoteClassification;

public record VoteRequestDTO(String cpf, String rullingTitle, VoteClassification voteClassification) {
}
