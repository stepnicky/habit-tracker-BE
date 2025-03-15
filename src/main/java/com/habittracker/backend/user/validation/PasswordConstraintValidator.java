package com.habittracker.backend.user.validation;

import java.util.Arrays;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.WhitespaceRule;
import org.springframework.stereotype.Component;

@Component
public class PasswordConstraintValidator extends org.passay.PasswordValidator {

  private final PasswordValidator validator;

  public PasswordConstraintValidator() {
    this.validator = new org.passay.PasswordValidator(Arrays.asList(
        new LengthRule(8, Integer.MAX_VALUE),

        new CharacterRule(EnglishCharacterData.UpperCase, 1),

        new CharacterRule(EnglishCharacterData.LowerCase, 1),

        new CharacterRule(EnglishCharacterData.Digit, 1),

        new CharacterRule(EnglishCharacterData.Special, 1),

        new WhitespaceRule()
    ));
  }

  public boolean isValid(String password) {
    return validator.validate(new PasswordData(password)).isValid();
  }
}
