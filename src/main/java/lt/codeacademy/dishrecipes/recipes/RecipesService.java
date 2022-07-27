package lt.codeacademy.dishrecipes.recipes;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.recipes.errors.RecipeNotFoundException;
import lt.codeacademy.dishrecipes.recipes.repos.RecipesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RecipesService {

    private final RecipesRepository recipesRepository;

    public Page<Recipe> getRecipes(Pageable pageable) {
        return recipesRepository.findAll(pageable);
    }

    public void createRecipe(Recipe recipe) {

        UUID recipeId = UUID.randomUUID();
        recipe.setId(recipeId);

        recipesRepository.save(recipe);
    }

    public Recipe getRecipe(UUID id) {

        return recipesRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("", null));
    }

    public void updateRecipe(Recipe recipe) {

        recipesRepository.save(recipe);
    }

    public Recipe deleteRecipe(UUID id) {

        Recipe recipeToRemove = getRecipe(id);
        recipesRepository.delete(recipeToRemove);

        return recipeToRemove;
    }

    public List<Recipe> search(String title) {

        return recipesRepository.findByTitleContainingIgnoreCase(title);
    }

}
