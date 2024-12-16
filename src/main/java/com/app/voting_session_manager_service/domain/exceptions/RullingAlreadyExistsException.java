package com.app.voting_session_manager_service.domain.exceptions;

public class RullingAlreadyExistsException extends RuntimeException {
    public RullingAlreadyExistsException(String message) {
        super(message);
    }
}
