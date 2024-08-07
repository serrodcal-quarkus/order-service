package dev.serrodcal.domain.vos;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerEmail {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    private String email;

    public CustomerEmail(String email) {
        checkEmail(email);

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void update(String email) throws IllegalAccessException {
        checkEmail(email);

        this.email = email;
    }

    private static void checkEmail(String email) {
        if (Objects.isNull(email) || email.isBlank())
            throw new IllegalArgumentException("Customer name cannot be null or blank");

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches())
            throw new IllegalArgumentException("Email pattern not valid");
    }

}
