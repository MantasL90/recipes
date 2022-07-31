package lt.codeacademy.dishrecipes.recipes.repos;

import lt.codeacademy.dishrecipes.recipes.entities.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface RecipesRepository {

    Page<Recipe> findAll(Pageable pageable);

    Recipe save(Recipe recipe);

    Optional<Recipe> findById(UUID id);

    void delete(Recipe recipeToRemove);

    Page<Recipe> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
