package com.app.voting_session_manager_service.domain.exceptions;

public class VoteAlreadyExistsException extends RuntimeException {
    public VoteAlreadyExistsException(String message) {
        super(message);
    }
}
