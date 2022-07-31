package lt.codeacademy.dishrecipes.commons.validators;

import lt.codeacademy.dishrecipes.users.entities.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "emailAddress.required");

        if (user.getEmailAddress() == null) {
            return;
        }

        if (!user.getEmailAddress().contains("@") &&
                !user.getEmailAddress().contains(".") &&
                !(user.getEmailAddress().indexOf("@") < user.getEmailAddress().indexOf("."))) {
            errors.rejectValue("emailAddress", "emailAddress.invalid",
                    new Object[]{user.getEmailAddress()}, null);
        }
    }
}
