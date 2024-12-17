package com.app.voting_session_manager_service.domain.exceptions;

public class AssociateNotFoundException extends RuntimeException {
    public AssociateNotFoundException(String message) {
        super(message);
    }
}
