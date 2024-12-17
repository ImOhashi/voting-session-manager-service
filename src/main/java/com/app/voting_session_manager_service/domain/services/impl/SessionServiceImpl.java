package com.app.voting_session_manager_service.domain.services.impl;

import com.app.voting_session_manager_service.application.dtos.requests.SessionRequestDTO;
import com.app.voting_session_manager_service.domain.entities.Result;
import com.app.voting_session_manager_service.domain.entities.enums.VoteClassification;
import com.app.voting_session_manager_service.domain.exceptions.SessionSurveyException;
import com.app.voting_session_manager_service.domain.services.SessionService;
import com.app.voting_session_manager_service.resources.repositories.RullingRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SessionServiceImpl implements SessionService {

    @Value("${app.session.time}")
    private Integer sessionTime;

    @Value("${app.kafka.topic.result-topic}")
    private String resultTopic;

    private final KafkaTemplate kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final Lock lock = new ReentrantLock();

    private Boolean isCounting = false;
    private String rullingTitle;

    private final RullingRepository rullingRepository;

    public SessionServiceImpl(KafkaTemplate kafkaTemplate, RullingRepository rullingRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.rullingRepository = rullingRepository;
    }

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

        var surveyResult = sessionSurvey();

        if (surveyResult.isEmpty()) {
            throw new SessionSurveyException("Session survey invalid");
        }

        surveyResult.ifPresent(this::sendResultToTopic);

        lock.unlock();
        logger.info("Close session.");
    }

    private Optional<Result> sessionSurvey() {
        return rullingRepository.findByTitle(this.rullingTitle).map(rulling -> {
            AtomicInteger sim = new AtomicInteger(0);
            AtomicInteger nao = new AtomicInteger(0);

            rulling.getVotesList().forEach(vote -> {
                if (vote.voteClassification().getDescription().equals(VoteClassification.SIM.getDescription())) {
                    sim.addAndGet(1);
                } else {
                    nao.addAndGet(1);
                }
            });

            this.rullingTitle = null;

            return new Result(rulling.getTitle(), sim.get(), nao.get());
        });
    }

    private void sendResultToTopic(Result result) {
        try {
            kafkaTemplate.send(resultTopic, result.toJson());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
