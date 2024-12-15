package com.app.voting_session_manager_service.domain.entities;

import com.app.voting_session_manager_service.domain.entities.enums.VoteClassification;

import java.util.UUID;

public record Vote(UUID id, String associate, VoteClassification voteClassification) {
}
