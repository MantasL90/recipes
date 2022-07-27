package lt.codeacademy.dishrecipes.recipes.repos;

import lt.codeacademy.dishrecipes.recipes.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RecipesRepository {

    Page<Recipe> findAll(Pageable pageable);

    Recipe save(Recipe recipe);

    Optional<Recipe> findById(UUID id);

    void delete(Recipe recipeToRemove);

    List<Recipe> findByTitleContainingIgnoreCase(String title);
}
