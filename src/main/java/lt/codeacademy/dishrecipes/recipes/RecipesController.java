package lt.codeacademy.dishrecipes.recipes;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.recipes.errors.RecipeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@Controller
public class RecipesController {

    private final RecipesService recipesService;

    @GetMapping("/public/recipes")
    public String getRecipes(@PageableDefault(size = 3) Pageable pageable, Model model) {

        Page<Recipe> recipes = recipesService.getRecipes(pageable);
        model.addAttribute("recipes", recipes);

        return "recipes";
    }

    @GetMapping("/private/recipes/create")
    public String openRecipeForm(Model model) {

        model.addAttribute("product", new Recipe());
        return "recipeForm";
    }

    @PostMapping("/private/recipes/create")
    public String createProduct(@Valid Recipe recipe, BindingResult errors, RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return "recipeForm";
        }

        recipesService.createRecipe(recipe);

        redirectAttributes.addFlashAttribute("message",
                String.format("Recipe '%s' successfully created!", recipe.getTitle()));

        return "redirect:/recipes";
    }

    @GetMapping("/private/recipes/{id}")
    public String openProduct(@PathVariable UUID id, Model model) {

        model.addAttribute("recipe", recipesService.getRecipe(id));

        return "recipeForm";
    }

    @PostMapping("/private/recipes/{id}")
    public String updateRecipe(Recipe recipe, Model model) {

        recipesService.updateRecipe(recipe);

        model.addAttribute("message", String.format("Recipe '%s' successfully updated!", recipe.getTitle()));

        return getRecipes(null, model);
    }

    @GetMapping("/private/recipes/{id}/recipe")
    public String recipeDetails(@PathVariable UUID id, Model model) {

        model.addAttribute("recipe", recipesService.getRecipe(id));

        return "recipeDetails";
    }

    @PostMapping("/private/products/{id}/delete")
    public String deleteProduct(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        Recipe recipe = recipesService.deleteRecipe(id);
        redirectAttributes.addAttribute("message", String.format("Recipe '%s' successfully deleted!", recipe.getTitle()));

        return "redirect:/recipes";
    }

    @GetMapping("/public/products/search")
    public String search(@RequestParam(required = false) String title, Model model) {

        model.addAttribute("products",
                recipesService.search(title));

        return "recipes";
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public String productNotFound(RecipeNotFoundException e, Model model) {

        model.addAttribute("messageCode", e.getMessage());
        model.addAttribute("recipeId", e.getId());

        return "error/productNotFoundPage";
    }
}
