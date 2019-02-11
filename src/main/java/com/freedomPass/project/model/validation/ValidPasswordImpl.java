package com.freedomPass.project.model.validation;

import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.UserService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.AlphabeticalSequenceRule;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.NumericalSequenceRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public class ValidPasswordImpl implements ConstraintValidator<ValidPassword, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(getResolver(), Arrays.asList(
                //        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new NumericalSequenceRule(3, false),
                new AlphabeticalSequenceRule(3, false),
                //                new QwertySequenceRule(3, false),
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                String.join(", ", validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }

    private UserProfile getAuthenticatedUser() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) { // Access is done from anynomous user (no login was made)
            if (!SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
                UserProfile userProfile = (UserProfile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                return userService.toUser(userProfile.getId());
            }
        }
        return null;
    }

    private MessageResolver getResolver() {
        UserProfile user = getAuthenticatedUser();
        String userLang = "en";
        if (user != null) {
            userLang = getAuthenticatedUser().getLanguage().getPrefix().toLowerCase();
        }
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(this.getClass().getResource("/passwordValidationmessages." + userLang + ".properties").getFile()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidPasswordImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidPasswordImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        MessageResolver resolver = new PropertiesMessageResolver(props);
        return resolver;
    }
}
