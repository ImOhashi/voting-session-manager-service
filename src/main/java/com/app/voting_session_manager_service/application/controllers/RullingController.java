package com.app.voting_session_manager_service.application.controllers;

import com.app.voting_session_manager_service.application.dtos.requests.RullingRegisterDTO;
import com.app.voting_session_manager_service.domain.services.RullingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rulling")
public class RullingController {

    private final RullingService rullingService;

    public RullingController(RullingService rullingService) {
        this.rullingService = rullingService;
    }

    @PostMapping("/v1")
    public ResponseEntity<Void> register(@RequestBody RullingRegisterDTO rullingRegisterDTO) {
        rullingService.register(rullingRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
