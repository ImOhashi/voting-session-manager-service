package com.app.voting_session_manager_service.domain.services.impl;

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

    @Async
    @Override
    public void execute() {
        if (!lock.tryLock() || isCounting) {
            logger.warn("Counter already in execution, try later");
            return;
        }

        try {
            startSession();
        } finally {
            closeSession();
        }
    }

    private void startSession() {
        logger.info("Initializing session...");

        isCounting = true;

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
        isCounting = false;
        lock.unlock();
        logger.info("Close session.");
    }

    public Boolean getIsCounting() {
        return this.isCounting;
    }
}
