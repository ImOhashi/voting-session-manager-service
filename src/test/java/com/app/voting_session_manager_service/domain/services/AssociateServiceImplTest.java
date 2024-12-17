package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.domain.exceptions.AssociateAlreadyExistsException;
import com.app.voting_session_manager_service.domain.exceptions.AssociateInvalidException;
import com.app.voting_session_manager_service.factories.AssociateFactory;
import com.app.voting_session_manager_service.factories.CreateAssociateRequestDTOFactory;
import com.app.voting_session_manager_service.resources.repositories.AssociateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
        "app.kafka.topic.result-topic=teste-topic"
})
public class AssociateServiceImplTest {

    @Autowired
    private AssociateService associateService;

    @MockitoBean
    private AssociateRepository associateRepositoryMock;

    @Test
    void testCreate_whenHasValidAssociateArguments_shouldBeCreate() {
        var createAssociateRequestDTOMock = CreateAssociateRequestDTOFactory.sample();
        var associateMock = AssociateFactory.sample();

        doReturn(Optional.empty()).when(associateRepositoryMock).findByCpf(any());
        doReturn(associateMock).when(associateRepositoryMock).save(any());

        associateService.create(createAssociateRequestDTOMock);

        verify(associateRepositoryMock, times(1)).save(any());
        verify(associateRepositoryMock, times(1)).findByCpf(any());
    }

    @Test
    void testCreate_whenReturnAssociateByCpfFind_shouldBeAnException() {
        var associateMock = AssociateFactory.sample();
        var createAssociateRequestDTOMock = CreateAssociateRequestDTOFactory.sample();

        doReturn(Optional.of(associateMock)).when(associateRepositoryMock).findByCpf(any());

        assertThrows(AssociateAlreadyExistsException.class, () -> {
            associateService.create(createAssociateRequestDTOMock);
        });
    }

    @Test
    void testCreate_whenAssociateInvalidCpf_shouldBeAnException() {
        var createAssociateRequestDTOMock = CreateAssociateRequestDTOFactory.sampleWithInvalidCpf();

        doReturn(Optional.empty()).when(associateRepositoryMock).findByCpf(any());

        assertThrows(AssociateInvalidException.class, () -> {
            associateService.create(createAssociateRequestDTOMock);
        });
    }

    @Test
    void testCreate_whenAssociateInvalidName_shouldBeAnException() {
        var createAssociateRequestDTOMock = CreateAssociateRequestDTOFactory.sampleWithInvalidName();

        doReturn(Optional.empty()).when(associateRepositoryMock).findByCpf(any());

        assertThrows(AssociateInvalidException.class, () -> {
            associateService.create(createAssociateRequestDTOMock);
        });
    }
}
