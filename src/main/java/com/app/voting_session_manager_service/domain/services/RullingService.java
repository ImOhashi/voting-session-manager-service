package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.application.dtos.requests.RullingRegisterDTO;
import com.app.voting_session_manager_service.application.dtos.requests.VoteRequestDTO;
import com.app.voting_session_manager_service.application.dtos.responses.VoteResponseDTO;

public interface RullingService {

    void register(RullingRegisterDTO rullingRegisterDTO);

    VoteResponseDTO voting(VoteRequestDTO voteRequestDTO);
}
