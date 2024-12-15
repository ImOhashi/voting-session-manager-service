package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.application.dtos.requests.CreateAssociateRequestDTO;

public interface AssociateService {
    void create(CreateAssociateRequestDTO createAssociateRequestDTO);
}
