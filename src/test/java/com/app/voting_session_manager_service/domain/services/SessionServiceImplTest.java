package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.factories.RullingFactory;
import com.app.voting_session_manager_service.factories.SessionRequestDTOFactory;
import com.app.voting_session_manager_service.resources.repositories.RullingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@TestPropertySource(properties = {
        "app.kafka.topic.result-topic=teste-topic"
})
class SessionServiceImplTest {

    @Autowired
    private SessionService sessionService;

    @MockitoBean
    private KafkaTemplate<String, String> kafkaTemplateMock;

    @MockitoBean
    private RullingRepository rullingRepositoryMock;

    @BeforeEach
    void setUp() {
        sessionService.setSessionTime(5);
    }

    @Test
    void testExecute_whenCounterIsAlreadyRunning_shouldNotStartNewSession() throws InterruptedException {
        var sessionRequestDTOMock = SessionRequestDTOFactory.sample();

        sessionService.setIsCounting(true);
        sessionService.execute(sessionRequestDTOMock);

        assertTrue(sessionService.getIsCounting());
    }

    @Test
    void testExecute_whenCounterIsNotRunning_shouldStartSession() throws InterruptedException {
        var sessionRequestDTOMock = SessionRequestDTOFactory.sample();
        var rullingMock = RullingFactory.sample();

        CompletableFuture<Void> future = new CompletableFuture<>();

        doReturn(Optional.of(rullingMock)).when(rullingRepositoryMock).findByTitle(any());
        doReturn(Optional.of(rullingMock)).when(rullingRepositoryMock).findByTitle(any());
        doReturn(future).when(kafkaTemplateMock).send(any(), any());

        sessionService.setIsCounting(false);

        sessionService.execute(sessionRequestDTOMock);

        Thread.sleep(7000);

        assertFalse(sessionService.getIsCounting());
    }

    @Test
    void testStartSession_shouldCountDownCorrectly() throws InterruptedException {
        var sessionRequestDTOMock = SessionRequestDTOFactory.sample();
        var rullingMock = RullingFactory.sample();

        sessionService.setIsCounting(false);

        doReturn(Optional.of(rullingMock)).when(rullingRepositoryMock).findByTitle(any());

        sessionService.execute(sessionRequestDTOMock);

        Thread.sleep(3500);

        assertFalse(sessionService.getIsCounting());
    }

    @Test
    void testLockShouldBeReleasedAfterSession() throws InterruptedException {
        var sessionRequestDTOMock = SessionRequestDTOFactory.sample();


        sessionService.setIsCounting(false);

        sessionService.execute(sessionRequestDTOMock);

        Thread.sleep(7000);

        assertFalse(sessionService.getIsCounting(), "Counter should be stopped and lock released");
    }
}
