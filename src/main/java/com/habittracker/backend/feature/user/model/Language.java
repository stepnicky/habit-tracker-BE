package com.habittracker.backend.feature.user.model;

import com.habittracker.backend.feature.user.exception.UnsupportedLanguageException;
import lombok.Getter;

@Getter
public enum Language {

    EN("en-gb"),
    PL("pl");

    private final String code;

    Language(String code) {
        this.code = code;
    }

    public static Language fromCode(String code) {
        for (Language language : Language.values()) {
            if (language.getCode().equalsIgnoreCase(code)) {
                return language;
            }
        }
        throw new UnsupportedLanguageException(
            String.format("Unsupported locale: %s", code)
        );
    }
}
