package com.finnplay.demo.app.entity;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.finnplay.demo.app.utils.AppUserUtils;

@Component
public class AppUserValidator implements Validator {

    private static final int MIN_PASSWORD_LENGTH = 6;

    private static final String EMAIL_REGEX = "^(.+)@(.+)$";

    private static final String DATE_FORMAT = "YYYY-mm-dd";

    @Override
    public boolean supports(Class<?> clazz) {
        return AppUser.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "field.required",
                "First name is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required",
                "Email is mandatory");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required",
                "Password is mandatory");
        AppUser user = (AppUser) target;
        String userPassword = user.getPassword();
        if (userPassword != null
                && userPassword.trim().length() < MIN_PASSWORD_LENGTH) {
            errors.rejectValue("password", "field.min.length",
                    new Object[] { Integer.valueOf(MIN_PASSWORD_LENGTH) },
                    "The password must be at least [" + MIN_PASSWORD_LENGTH + "] characters in length.");
        }
        String email = user.getEmail();
        if (email != null
                && !AppUserUtils.patternMatches(email, EMAIL_REGEX)) {
            errors.rejectValue("email", "field.email.pattern",
                    new Object[] { Integer.valueOf(MIN_PASSWORD_LENGTH) },
                    "Email must be in xx@yy.zz format");
        }
        Date birthDate = user.getBirthDate();
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            java.util.Date isValidDate = sdf.parse(birthDate.toString());
            Date currentDate = new Date(System.currentTimeMillis());
            boolean isFutureDate = birthDate.after(currentDate);
            if (isFutureDate) {
                errors.rejectValue("birthDate", "field.date.pattern",
                        new Object[] { Integer.valueOf(MIN_PASSWORD_LENGTH) },
                        "Birth Date cant be future date");
            }
        } catch (DateTimeParseException | ParseException e) {
            errors.rejectValue("birthDate", "field.date.pattern",
                    new Object[] { Integer.valueOf(MIN_PASSWORD_LENGTH) },
                    "Date must be in YYYY-mm-dd format");
        }

    }

}
