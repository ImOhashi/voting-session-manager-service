package com.app.voting_session_manager_service.domain.services.impl;

import com.app.voting_session_manager_service.application.dtos.requests.SessionRequestDTO;
import com.app.voting_session_manager_service.domain.services.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SessionServiceImpl implements SessionService {

    @Value("${app.session.time}")
    private Integer sessionTime;

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final Lock lock = new ReentrantLock();

    private Boolean isCounting = false;
    private String rullingTitle;

    @Async
    @Override
    public void execute(SessionRequestDTO sessionRequestDTO) {
        if (!lock.tryLock() || isCounting) {
            logger.warn("Counter already in execution, try later");
            return;
        }

        try {
            this.rullingTitle = sessionRequestDTO.rullingTitle();
            startSession();
        } finally {
            this.rullingTitle = null;
            closeSession();
        }
    }

    private void startSession() {
        logger.info("Initializing session...");

        this.isCounting = true;

        logger.info("Counter status: {}", isCounting);

        for (int time = sessionTime; time >= 0; time--) {
            try {
                Thread.sleep(1000);
                logger.info("Time: {} seconds", time);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void closeSession() {
        this.isCounting = false;
        lock.unlock();
        logger.info("Close session.");
    }

    public Boolean getIsCounting() {
        return this.isCounting;
    }

    public String getRullingTitle() {
        return this.rullingTitle;
    }

    public void setRullingTitle(String rullingTitle) {
        this.rullingTitle = rullingTitle;
    }

    public void setIsCounting(boolean isCounting) {
        this.isCounting = isCounting;
    }

    public void setSessionTime(int sessionTime) {
        this.sessionTime = sessionTime;
    }
}
