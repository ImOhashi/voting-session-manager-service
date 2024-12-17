package com.app.voting_session_manager_service.application.controllers;

import com.app.voting_session_manager_service.application.dtos.requests.SessionRequestDTO;
import com.app.voting_session_manager_service.domain.services.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/v1/start")
    public void startSession(@RequestBody SessionRequestDTO sessionRequestDTO) {
        sessionService.execute(sessionRequestDTO);
    }
}
