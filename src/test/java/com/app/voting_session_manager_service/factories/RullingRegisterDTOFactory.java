package com.app.voting_session_manager_service.factories;

import com.app.voting_session_manager_service.application.dtos.requests.RullingRegisterDTO;

public class RullingRegisterDTOFactory {
    public static RullingRegisterDTO sample() {
        return new RullingRegisterDTO("Rulling title", "Rulling description");
    }

    public static RullingRegisterDTO sampleWithInvalidTitle() {
        return new RullingRegisterDTO("", "Rulling description");
    }
}
