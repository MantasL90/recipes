package lt.codeacademy.dishrecipes.commons.exceptions;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {


    public UserException(String messageCode) {
        super(messageCode);

    }
}
