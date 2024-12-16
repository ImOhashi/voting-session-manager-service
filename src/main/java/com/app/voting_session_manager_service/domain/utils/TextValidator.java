package com.app.voting_session_manager_service.domain.utils;

public class TextValidator {
    public static boolean isValidText(String text) {
        return text != null && !text.trim().isEmpty();
    }
}
