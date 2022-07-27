package lt.codeacademy.dishrecipes.recipes.repos;

import lt.codeacademy.dishrecipes.recipes.Recipe;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

@Profile("!mocked-db")
public interface JpaRecipesRepository extends JpaRepository<Recipe, UUID>, RecipesRepository{

    List<Recipe> findByTitleContainingIgnoreCase(String title);
}
