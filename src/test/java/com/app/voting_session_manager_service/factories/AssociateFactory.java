package com.app.voting_session_manager_service.factories;

import com.app.voting_session_manager_service.domain.entities.Associate;

public class AssociateFactory {
    public static Associate sample() {
        return new Associate.Builder()
                .setName("teste")
                .setCpf("495.589.800-99")
                .build();
    }
}
