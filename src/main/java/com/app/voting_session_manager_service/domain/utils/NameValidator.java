package com.app.voting_session_manager_service.domain.utils;

public class NameValidator {
    public static boolean isValidName(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }
}
