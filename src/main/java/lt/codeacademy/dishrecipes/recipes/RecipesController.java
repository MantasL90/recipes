package lt.codeacademy.dishrecipes.recipes;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.recipes.entities.Recipe;
import lt.codeacademy.dishrecipes.recipes.errors.RecipeNotFoundException;
import lt.codeacademy.dishrecipes.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class RecipesController {

    private final RecipesService recipesService;

    @GetMapping("/private/recipes")
    public String getAllRecipes(@PageableDefault(size = 9) Pageable pageable, Model model, @AuthenticationPrincipal User user) {

        Page<Recipe> recipes = recipesService.getRecipes(pageable, user);
        model.addAttribute("recipes", recipes);

        return "recipes";
    }

    @GetMapping("/public/publishedRecipes")
    public String getPublishedRecipes(@PageableDefault(size = 9) Pageable pageable, Model model, @AuthenticationPrincipal User user) {

        System.out.println("GETTING PUBLISHED RECIPES: "+ user);
        Page<Recipe> recipes = recipesService.getRecipes(pageable, user);
        model.addAttribute("recipes", recipes);

        return "publishedRecipes";
    }

    @GetMapping("/private/recipes/create")
    public String openRecipeForm(Model model) {

        model.addAttribute("recipe", new Recipe());
        return "recipeForm";
    }

    @PostMapping("/private/recipes/create")
    public String createRecipe(@Valid Recipe recipe, BindingResult errors, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return "recipeForm";
        }

        recipesService.createRecipe(recipe);
        redirectAttributes.addFlashAttribute("message",
                String.format("Recipe '%s' successfully created!", recipe.getTitle()));

        return "redirect:/private/recipes";
    }

    @GetMapping("private/recipes/{id}")
    public String openRecipe(@PathVariable UUID id, Model model) {

        model.addAttribute("recipe", recipesService.getRecipe(id));

        return "recipeForm";
    }

    @PostMapping("/private/recipes/{id}")
    public String updateRecipe(Recipe recipe, Model model) {

        recipesService.updateRecipe(recipe);

        model.addAttribute("message", String.format("Recipe '%s' successfully updated!", recipe.getTitle()));

        return "redirect:/private/recipes";
    }

    @GetMapping("/public/recipes/{id}/review")
    public String reviewRecipe(@PathVariable UUID id, Model model) {

        model.addAttribute("recipe", recipesService.getRecipe(id));

        return "recipeDetails";
    }

    @PostMapping("/private/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        Recipe recipe = recipesService.deleteRecipe(id);
        redirectAttributes.addAttribute("message", String.format("Recipe '%s' successfully deleted!", recipe.getTitle()));

        return "redirect:/private/recipes";
    }

    @PostMapping("/private/recipes/{id}/publish")
    public String publishRecipe(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        Recipe recipe = recipesService.publishRecipe(id);
        redirectAttributes.addAttribute("message", String.format("Recipe '%s' successfully published!", recipe.getTitle()));

        return "redirect:/private/recipes";
    }

    @PostMapping("/private/recipes/{id}/unpublish")
    public String unpublishRecipe(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        Recipe recipe = recipesService.unPublishRecipe(id);
        redirectAttributes.addAttribute("message", String.format("Recipe '%s' successfully published!", recipe.getTitle()));

        return "redirect:/private/recipes";
    }


    @GetMapping("/public/publishedRecipes/search")
    public String searchPublishRecipes(@RequestParam(required = false) String title, Model model, Pageable pageable) {

        Page<Recipe> resultList = recipesService.findAllPublishedRecipesByTitle(title, pageable);
        model.addAttribute("recipes", resultList);

        return "publishedRecipes";
    }

    @GetMapping("/private/recipes/search")
    public String search(@RequestParam(required = false) String title, Model model, Pageable pageable) {

        Page<Recipe> resultList = recipesService.findAllRecipesByTitle(title, pageable);
        model.addAttribute("recipes", resultList);

        return "recipes";
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public String recipeNotFound(RecipeNotFoundException e, Model model) {

        model.addAttribute("messageCode", e.getMessage());
        model.addAttribute("recipeId", e.getId());

        return "error/recipeNotFoundPage";
    }
}
