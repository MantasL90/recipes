package lt.codeacademy.dishrecipes.commons.exceptions;

import lombok.Getter;

@Getter
public class RecipeException extends RuntimeException {


    public RecipeException(String messageCode) {
        super(messageCode);

    }
}
