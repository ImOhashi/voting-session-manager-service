package com.app.voting_session_manager_service.factories;

import com.app.voting_session_manager_service.application.dtos.requests.CreateAssociateRequestDTO;

public class CreateAssociateRequestDTOFactory {
    public static CreateAssociateRequestDTO sample() {
        return new CreateAssociateRequestDTO("Teste", "495.589.800-99");
    }

    public static CreateAssociateRequestDTO sampleWithInvalidCpf() {
        return new CreateAssociateRequestDTO("Teste", "495.589.800-10");
    }

    public static CreateAssociateRequestDTO sampleWithInvalidName() {
        return new CreateAssociateRequestDTO("", "495.589.800-99");
    }
}
