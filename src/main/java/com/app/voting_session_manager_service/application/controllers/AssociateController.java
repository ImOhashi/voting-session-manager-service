package com.app.voting_session_manager_service.application.controllers;

import com.app.voting_session_manager_service.application.dtos.requests.CreateAssociateRequestDTO;
import com.app.voting_session_manager_service.domain.services.AssociateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/associate")
public class AssociateController {

    private final AssociateService associateService;

    public AssociateController(AssociateService associateService) {
        this.associateService = associateService;
    }

    @PostMapping("/v1")
    public ResponseEntity<Void> createAssociate(@RequestBody CreateAssociateRequestDTO createAssociateRequestDTO) {
        associateService.create(createAssociateRequestDTO);
        return ResponseEntity.accepted().build();
    }
}
