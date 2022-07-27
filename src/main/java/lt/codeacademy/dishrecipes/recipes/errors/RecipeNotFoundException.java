package lt.codeacademy.dishrecipes.recipes.errors;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RecipeNotFoundException extends RuntimeException{

    private final UUID id;

    public RecipeNotFoundException(String messageCode, UUID id) {
        super(messageCode);
        this.id = id;
    }
}
