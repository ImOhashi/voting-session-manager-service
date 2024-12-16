package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.application.dtos.requests.RullingRegisterDTO;

public interface RullingService {
    void register(RullingRegisterDTO rullingRegisterDTO);

}
