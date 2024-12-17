package com.app.voting_session_manager_service.domain.services.impl;

import com.app.voting_session_manager_service.application.dtos.requests.RullingRegisterDTO;
import com.app.voting_session_manager_service.application.dtos.requests.VoteRequestDTO;
import com.app.voting_session_manager_service.application.dtos.responses.VoteResponseDTO;
import com.app.voting_session_manager_service.domain.entities.Associate;
import com.app.voting_session_manager_service.domain.entities.Rulling;
import com.app.voting_session_manager_service.domain.entities.Vote;
import com.app.voting_session_manager_service.domain.exceptions.*;
import com.app.voting_session_manager_service.domain.services.RullingService;
import com.app.voting_session_manager_service.domain.services.SessionService;
import com.app.voting_session_manager_service.domain.utils.TextValidator;
import com.app.voting_session_manager_service.resources.repositories.AssociateRepository;
import com.app.voting_session_manager_service.resources.repositories.RullingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class RullingServiceImpl implements RullingService {

    private static final Logger logger = LoggerFactory.getLogger(RullingServiceImpl.class);

    private final RullingRepository rullingRepository;
    private final AssociateRepository associateRepository;
    private final SessionService sessionService;

    public RullingServiceImpl(RullingRepository rullingRepository, AssociateRepository associateRepository, SessionService sessionService) {
        this.rullingRepository = rullingRepository;
        this.associateRepository = associateRepository;
        this.sessionService = sessionService;
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

    @Override
    public VoteResponseDTO voting(VoteRequestDTO voteRequestDTO) {

        validateSessionRulling(voteRequestDTO);

        return associateRepository.findByCpf(voteRequestDTO.cpf()).map(associate -> {
            logger.info("Validate rulling title...");

            var rulling = validateRullingTitleAndGetRulling(voteRequestDTO.rullingTitle());

            validateIfVoteExists(rulling, associate);

            logger.info("Adding new vote to rulling={}", rulling.getTitle());

            rulling.addNewVote(
                    new Vote(
                            UUID.randomUUID().toString(),
                            associate.getCpf(),
                            voteRequestDTO.voteClassification()
                    )
            );

            rullingRepository.save(rulling);

            logger.info("Vote added with success!");

            return new VoteResponseDTO(associate.getName(), voteRequestDTO.voteClassification());
        }).orElseThrow(() -> {
            logger.error("Associate with cpf={} not found!", voteRequestDTO.cpf());
            return new AssociateNotFoundException("Associate with cpf=" + voteRequestDTO.cpf() + " not found!");
        });
    }

    private void validateIfVoteExists(Rulling rulling, Associate associate) {
        var result = rulling.getVotesList().stream().filter(vote -> vote.associateCpf().equals(associate.getCpf()));

        if (!result.toList().isEmpty()) {
            logger.error("Vote already exists to associate with cpf={}", associate.getCpf());
            throw new VoteAlreadyExistsException("Vote already exists to associate with cpf=" + associate.getCpf());
        }
    }

    private Rulling validateRullingTitleAndGetRulling(String rullingTitle) {
        var rullingOptional = rullingRepository.findByTitle(rullingTitle);

        if (rullingOptional.isEmpty()) {
            logger.error("Rulling title is invalid!");
            throw new RullingTitleInvalidException("Rulling title is invalid!");
        }

        return rullingOptional.get();
    }

    private void validateSessionRulling(VoteRequestDTO voteRequestDTO) {
        if (!sessionService.getIsCounting() || !sessionService.getRullingTitle().equals(voteRequestDTO.rullingTitle())) {
            throw new InvalidVotingSessionException("Voting session has closed.");
        }
    }

    private void validateTitle(RullingRegisterDTO rullingRegisterDTO) {
        if (!TextValidator.isValidText(rullingRegisterDTO.title())) {
            throw new RullingTitleInvalidException("Rulling title is invalid!");
        }
    }
}
