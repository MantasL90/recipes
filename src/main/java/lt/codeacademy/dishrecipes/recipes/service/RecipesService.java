package lt.codeacademy.dishrecipes.recipes.service;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.commons.exceptions.RecipeException;
import lt.codeacademy.dishrecipes.recipes.entities.Recipe;
import lt.codeacademy.dishrecipes.recipes.errors.RecipeNotFoundException;
import lt.codeacademy.dishrecipes.recipes.repos.JpaRecipesRepository;
import lt.codeacademy.dishrecipes.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RecipesService {

    private final JpaRecipesRepository recipesRepository;


    public Page<Recipe> searchPrivate(String title, Pageable pageable, User user) {

        if (user.getRole().getName().equalsIgnoreCase("USER")) {

            return recipesRepository.searchByTitleAndUsername(user.getUsername(), title, pageable);
        }
        return recipesRepository.findByTitleContainingIgnoreCase(title, pageable);


    }

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


    public void createRecipe(Recipe recipe, User user) {

        UUID recipeId = UUID.randomUUID();
        recipe.setId(recipeId);
        recipe.setPublished(false);
        recipe.setUser(user);
        recipesRepository.save(recipe);
    }

    @Transactional
    public void updateRecipe(UUID id, Recipe recipe) {


        Recipe oldRecipe = getRecipe(id);
        oldRecipe.setTitle(recipe.getTitle());
        oldRecipe.setDescription(recipe.getDescription());
        oldRecipe.setIngredients(recipe.getIngredients());
        oldRecipe.setPreparation(recipe.getPreparation());
        oldRecipe.setPreparationTime(recipe.getPreparationTime());
        oldRecipe.setServings(recipe.getServings());
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
                .orElseThrow(() -> new RecipeException("msg.recipe.recipeNotFound"));
    }

    @Transactional
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


    //   TODO create search for published Recipes
    public Page<Recipe> findAllPublishedRecipesByTitle(String title, Pageable pageable) {

        return recipesRepository.findPublishedRecipeByTitle(title, pageable);
    }


}
