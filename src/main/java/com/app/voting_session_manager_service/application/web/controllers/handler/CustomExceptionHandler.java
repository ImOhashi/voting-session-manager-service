package com.app.voting_session_manager_service.application.web.controllers.handler;

import com.app.voting_session_manager_service.application.web.controllers.handler.exceptions.ApiErrorMessage;
import com.app.voting_session_manager_service.domain.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleRuntimeException(RuntimeException runtimeException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, runtimeException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleAssociateAlreadyExistsException(AssociateAlreadyExistsException associateAlreadyExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, associateAlreadyExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleAssociateInvalidException(AssociateInvalidException associateInvalidException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, associateInvalidException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleAssociateNotFoundException(AssociateNotFoundException associateNotFoundException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, associateNotFoundException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleInvalidVotingSessionException(InvalidVotingSessionException invalidVotingSessionException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, invalidVotingSessionException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleRullingAlreadyExistsException(RullingAlreadyExistsException rullingAlreadyExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, rullingAlreadyExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleRullingTitleInvalidException(RullingTitleInvalidException rullingTitleInvalidException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, rullingTitleInvalidException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleVoteAlreadyExistsException(VoteAlreadyExistsException voteAlreadyExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, voteAlreadyExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }
}
