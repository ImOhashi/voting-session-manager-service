package com.app.voting_session_manager_service.domain.services;

import com.app.voting_session_manager_service.domain.services.impl.SessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceImplTest {

    private SessionServiceImpl sessionService;

    @BeforeEach
    void setUp() {
        sessionService = new SessionServiceImpl();
        sessionService.setSessionTime(5);
    }

    @Test
    void testExecute_whenCounterIsAlreadyRunning_shouldNotStartNewSession() throws InterruptedException {
        sessionService.setIsCounting(true);
        sessionService.execute();
        assertTrue(sessionService.getIsCounting(), "Counter should already be running");
    }

    @Test
    void testExecute_whenCounterIsNotRunning_shouldStartSession() throws InterruptedException {
        sessionService.setIsCounting(false);

        sessionService.execute();

        Thread.sleep(1500);

        assertFalse(sessionService.getIsCounting(), "Counter should have stopped after session time");
    }

    @Test
    void testStartSession_shouldCountDownCorrectly() throws InterruptedException {
        sessionService.execute();

        Thread.sleep(3500);

        assertFalse(sessionService.getIsCounting(), "Counter should be stopped");
    }

    @Test
    void testLockShouldBeReleasedAfterSession() throws InterruptedException {
        sessionService.setIsCounting(false);

        sessionService.execute();

        Thread.sleep(1500);

        assertFalse(sessionService.getIsCounting(), "Counter should be stopped and lock released");
    }
}
