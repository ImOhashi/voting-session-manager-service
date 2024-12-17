package com.app.voting_session_manager_service.domain.exceptions;

public class InvalidVotingSessionException extends RuntimeException {
    public InvalidVotingSessionException(String message) {
        super(message);
    }
}
