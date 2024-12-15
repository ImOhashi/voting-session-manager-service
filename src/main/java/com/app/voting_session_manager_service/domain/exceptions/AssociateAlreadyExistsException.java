package com.app.voting_session_manager_service.domain.exceptions;

public class AssociateAlreadyExistsException extends RuntimeException {
    public AssociateAlreadyExistsException(String message) {
        super(message);
    }
}
