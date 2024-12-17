package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.application.dtos.requests.SessionRequestDTO;

public interface SessionService {

    void execute(SessionRequestDTO sessionRequestDTO);

    Boolean getIsCounting();

    String getRullingTitle();

    void setRullingTitle(String rullingTitle);

    void setSessionTime(int sessionTime);

    void setIsCounting(boolean isCounting);
}
