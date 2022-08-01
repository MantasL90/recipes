package lt.codeacademy.dishrecipes.recipes.service;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.recipes.entities.Recipe;
import lt.codeacademy.dishrecipes.recipes.errors.RecipeNotFoundException;
import lt.codeacademy.dishrecipes.recipes.repos.JpaRecipesRepository;
import lt.codeacademy.dishrecipes.users.entities.Role;
import lt.codeacademy.dishrecipes.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class RecipesService {

    private final JpaRecipesRepository recipesRepository;


    public Page<Recipe> getRecipes(Pageable pageable, User user) {

        if (user != null) {

            switch (user.getRole().getName()) {
                case "USER" -> {
                    return getUserRecipes(pageable, user);
                }
                case "ADMIN" -> {
                    return recipesRepository.findAll(pageable);
                }
                default -> {
                    return recipesRepository.findAllPublishedRecipes(pageable);
                }
            }
        }
        return recipesRepository.findAllPublishedRecipes(pageable);
    }

    public Page<Recipe> getPublishedRecipes(Pageable pageable) {
        return recipesRepository.findAllPublishedRecipes(pageable);
    }


    public void createRecipe(Recipe recipe) {

        UUID recipeId = UUID.randomUUID();
        recipe.setId(recipeId);
        recipe.setPublished(false);

        recipesRepository.save(recipe);
    }

    public Recipe publishRecipe(UUID id) {

        Recipe recipeToPublish = getRecipe(id);
        recipeToPublish.setPublished(true);

        recipesRepository.save(recipeToPublish);

        return recipeToPublish;
    }

    public Recipe unPublishRecipe(UUID id) {

        Recipe recipeToPublish = getRecipe(id);
        recipeToPublish.setPublished(false);

        recipesRepository.save(recipeToPublish);

        return recipeToPublish;
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

    private Page<Recipe> getUserRecipes(Pageable pageable, User user) {

        String username = user.getUsername();
        return recipesRepository.findRecipesByUsername(username, pageable);
    }


    public Page<Recipe> findAllRecipesByTitle(String title, Pageable pageable) {

        return recipesRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Recipe> findAllPublishedRecipesByTitle(String title, Pageable pageable) {

        return recipesRepository.findPublishedRecipeByTitle(title, pageable);
    }

}
