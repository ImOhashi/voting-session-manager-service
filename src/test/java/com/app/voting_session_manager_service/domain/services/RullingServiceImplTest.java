package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.domain.entities.Associate;
import com.app.voting_session_manager_service.domain.exceptions.*;
import com.app.voting_session_manager_service.factories.*;
import com.app.voting_session_manager_service.resources.repositories.AssociateRepository;
import com.app.voting_session_manager_service.resources.repositories.RullingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(properties = {
        "app.kafka.topic.result-topic=teste-topic"
})
public class RullingServiceImplTest {

    @Autowired
    private RullingService rullingService;

    @MockitoBean
    private RullingRepository rullingRepository;

    @MockitoBean
    private SessionService sessionService;

    @MockitoBean
    private AssociateRepository associateRepository;

    @Test
    void testRegister_whenRegisterAValidTitle_shouldBeRegister() {
        var rullingRegisterDTOMock = RullingRegisterDTOFactory.sample();
        var rullingMock = RullingFactory.sample();

        doReturn(Optional.empty()).when(rullingRepository).findByTitle(any());
        doReturn(rullingMock).when(rullingRepository).save(any());

        rullingService.register(rullingRegisterDTOMock);

        verify(rullingRepository, times(1)).findByTitle(any());
        verify(rullingRepository, times(1)).save(any());
    }

    @Test
    void testRegister_whenRullingTitleAlreadyExists_shouldBeAnException() {
        var rullingRegisterDTOMock = RullingRegisterDTOFactory.sample();
        var rullingMock = RullingFactory.sample();

        doReturn(Optional.of(rullingMock)).when(rullingRepository).findByTitle(any());

        assertThrows(RullingAlreadyExistsException.class, () -> {
            rullingService.register(rullingRegisterDTOMock);
        });
    }

    @Test
    void testRegister_whenRullingTitleIsInvalid_shouldBeAnException() {
        var rullingRegisterDTOMock = RullingRegisterDTOFactory.sampleWithInvalidTitle();
        var rullingMock = RullingFactory.sample();

        doReturn(Optional.empty()).when(rullingRepository).findByTitle(any());

        assertThrows(RullingTitleInvalidException.class, () -> {
            rullingService.register(rullingRegisterDTOMock);
        });
    }

    @Test
    void testVoting_whenSessionAndRullingOk_shouldBeVotingWithSuccess() {
        var voteRequestDTOMock = VoteRequestDTOFactory.sample();
        var associateMock = AssociateFactory.sample();
        var rullingMock = RullingFactory.sample();
        var voteMock = VoteFactory.sample();

        rullingMock.addNewVote(voteMock);

        sessionService.setRullingTitle("Teste");

        doReturn(Boolean.TRUE).when(sessionService).getIsCounting();
        doReturn("Teste").when(sessionService).getRullingTitle();
        doReturn(Optional.of(associateMock)).when(associateRepository).findByCpf(any());
        doReturn(Optional.of(rullingMock)).when(rullingRepository).findByTitle(any());
        doReturn(rullingMock).when(rullingRepository).save(any());

        var response = rullingService.voting(voteRequestDTOMock);

        assertEquals(response.voteClassification().getDescription(), voteMock.voteClassification().getDescription());
    }

    @Test
    void testVoting_whenSessionAndRullingOkButNotAssociate_shouldBeAnException() {
        var voteRequestDTOMock = VoteRequestDTOFactory.sample();
        var rullingMock = RullingFactory.sample();
        var voteMock = VoteFactory.sample();

        rullingMock.addNewVote(voteMock);

        sessionService.setRullingTitle("Teste");

        doReturn(Boolean.TRUE).when(sessionService).getIsCounting();
        doReturn("Teste").when(sessionService).getRullingTitle();
        doReturn(Optional.empty()).when(associateRepository).findByCpf(any());

        assertThrows(AssociateNotFoundException.class, () -> {
            rullingService.voting(voteRequestDTOMock);
        });
    }

    @Test
    void testVoting_whenSessionButNotRulling_shouldBeAnException() {
        var voteRequestDTOMock = VoteRequestDTOFactory.sample();
        var associateMock = AssociateFactory.sample();
        var rullingMock = RullingFactory.sample();
        var voteMock = VoteFactory.sample();

        rullingMock.addNewVote(voteMock);

        sessionService.setRullingTitle("Teste");

        doReturn(Boolean.TRUE).when(sessionService).getIsCounting();
        doReturn("Teste").when(sessionService).getRullingTitle();
        doReturn(Optional.of(associateMock)).when(associateRepository).findByCpf(any());
        doReturn(Optional.empty()).when(rullingRepository).findByTitle(any());

        assertThrows(RullingTitleInvalidException.class, () -> {
            rullingService.voting(voteRequestDTOMock);
        });
    }

    @Test
    void testVoting_whenSessionButNotSession_shouldBeAnException() {
        var voteRequestDTOMock = VoteRequestDTOFactory.sample();
        var associateMock = AssociateFactory.sample();
        var rullingMock = RullingFactory.sample();
        var voteMock = VoteFactory.sample();

        rullingMock.addNewVote(voteMock);

        sessionService.setRullingTitle("Teste");

        doReturn(Boolean.FALSE).when(sessionService).getIsCounting();
        doReturn("Teste").when(sessionService).getRullingTitle();
        doReturn(Optional.of(associateMock)).when(associateRepository).findByCpf(any());
        doReturn(Optional.of(rullingMock)).when(rullingRepository).findByTitle(any());

        assertThrows(InvalidVotingSessionException.class, () -> {
            rullingService.voting(voteRequestDTOMock);
        });
    }

    @Test
    void testVoting_whenVoteAlreadyExists_shouldBeAnException() {
        var voteRequestDTOMock = VoteRequestDTOFactory.sample();
        var associateMock = new Associate("teste", "teste", "548.527.100-66");
        var rullingMock = RullingFactory.sample();
        var voteMock = VoteFactory.sample();

        rullingMock.addNewVote(voteMock);

        sessionService.setRullingTitle("Teste");

        doReturn(Boolean.TRUE).when(sessionService).getIsCounting();
        doReturn("Teste").when(sessionService).getRullingTitle();
        doReturn(Optional.of(associateMock)).when(associateRepository).findByCpf(any());
        doReturn(Optional.of(rullingMock)).when(rullingRepository).findByTitle(any());

        assertThrows(VoteAlreadyExistsException.class, () -> {
            rullingService.voting(voteRequestDTOMock);
        });
    }
}
