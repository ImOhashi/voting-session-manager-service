package com.app.voting_session_manager_service.factories;

import com.app.voting_session_manager_service.application.dtos.requests.SessionRequestDTO;

public class SessionRequestDTOFactory {
    public static SessionRequestDTO sample() {
        return new SessionRequestDTO("Teste");
    }
}
