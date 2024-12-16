package com.app.voting_session_manager_service.domain.services.impl;

import com.app.voting_session_manager_service.application.dtos.requests.RullingRegisterDTO;
import com.app.voting_session_manager_service.domain.entities.Rulling;
import com.app.voting_session_manager_service.domain.exceptions.RullingAlreadyExistsException;
import com.app.voting_session_manager_service.domain.exceptions.RullingTitleInvalidException;
import com.app.voting_session_manager_service.domain.services.RullingService;
import com.app.voting_session_manager_service.domain.utils.TextValidator;
import com.app.voting_session_manager_service.resources.repositories.RullingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RullingServiceImpl implements RullingService {

    private static final Logger logger = LoggerFactory.getLogger(RullingServiceImpl.class);

    private final RullingRepository rullingRepository;

    public RullingServiceImpl(RullingRepository rullingRepository) {
        this.rullingRepository = rullingRepository;
    }

    @Override
    public void register(RullingRegisterDTO rullingRegisterDTO) {
        rullingRepository.findByTitle(rullingRegisterDTO.title()).ifPresentOrElse(rulling -> {
            logger.error("Rulling with title={}, already exists!", rulling.getTitle());
            throw new RullingAlreadyExistsException("Rulling already exists!");
        }, () -> {
            logger.info("Creating rulling...");

            validateTitle(rullingRegisterDTO);

            var newRulling = new Rulling.Builder()
                    .setTitle(rullingRegisterDTO.title())
                    .setDescription(rullingRegisterDTO.description())
                    .setVotesList(new ArrayList<>())
                    .build();

            rullingRepository.save(newRulling);

            logger.info("Rulling created");
        });
    }



    private void validateTitle(RullingRegisterDTO rullingRegisterDTO) {
        if (!TextValidator.isValidText(rullingRegisterDTO.title())) {
            throw new RullingTitleInvalidException("Rulling title is invalid!");
        }
    }
}
