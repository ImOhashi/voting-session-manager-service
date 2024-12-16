package com.app.voting_session_manager_service.domain.services.impl;

import com.app.voting_session_manager_service.application.dtos.requests.CreateAssociateRequestDTO;
import com.app.voting_session_manager_service.domain.entities.Associate;
import com.app.voting_session_manager_service.domain.exceptions.AssociateAlreadyExistsException;
import com.app.voting_session_manager_service.domain.exceptions.AssociateInvalidException;
import com.app.voting_session_manager_service.domain.services.AssociateService;
import com.app.voting_session_manager_service.domain.utils.CpfValidator;
import com.app.voting_session_manager_service.domain.utils.TextValidator;
import com.app.voting_session_manager_service.resources.repositories.AssociateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AssociateServiceImpl implements AssociateService {

    private static final Logger logger = LoggerFactory.getLogger(AssociateServiceImpl.class);

    private final AssociateRepository associateRepository;

    public AssociateServiceImpl(AssociateRepository associateRepository) {
        this.associateRepository = associateRepository;
    }

    @Override
    public void create(CreateAssociateRequestDTO createAssociateRequestDTO) {
        associateRepository.findByCpf(createAssociateRequestDTO.cpf())
                .ifPresentOrElse(associate -> {
                    logger.error("Associate with cpf={}, already exists!", associate.getCpf());
                    throw new AssociateAlreadyExistsException("Associate with cpf=" + associate.getCpf() + " already exists!");
                }, () -> {
                    logger.info("Trying create a new associate with cpf={}", createAssociateRequestDTO.cpf());

                    validateAssociateArguments(createAssociateRequestDTO);

                    var newAssociate = new Associate.Builder()
                            .setName(createAssociateRequestDTO.name())
                            .setCpf(createAssociateRequestDTO.cpf())
                            .build();

                    associateRepository.save(newAssociate);

                    logger.info("Associate created with cpf={}", newAssociate.getCpf());
                });
    }

    private void validateAssociateArguments(CreateAssociateRequestDTO createAssociateRequestDTO) {
        logger.info("Validating associate arguments...");

        if (!CpfValidator.isValidCPF(createAssociateRequestDTO.cpf()) || !TextValidator.isValidText(createAssociateRequestDTO.name())) {
            logger.error("Associate invalid arguments!");
            throw new AssociateInvalidException("Associate invalid arguments!");
        }
    }
}
