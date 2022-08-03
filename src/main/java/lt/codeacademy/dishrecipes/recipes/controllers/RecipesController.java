package lt.codeacademy.dishrecipes.recipes.controllers;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.commons.exceptions.RecipeException;
import lt.codeacademy.dishrecipes.recipes.service.RecipesService;
import lt.codeacademy.dishrecipes.recipes.entities.Recipe;

import lt.codeacademy.dishrecipes.users.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/private/recipes")
    public String getAllRecipes(@PageableDefault(size = 6) Pageable pageable, Model model, @AuthenticationPrincipal User user) {

        Page<Recipe> recipes = recipesService.getRecipes(pageable, user);
        model.addAttribute("recipes", recipes);

        return "recipes";
    }

    @GetMapping("/public/publishedRecipes")
    public String getPublishedRecipes(@PageableDefault(size = 6) Pageable pageable, Model model) {

        Page<Recipe> recipes = recipesService.getPublishedRecipes(pageable);
        model.addAttribute("recipes", recipes);

        return "publishedRecipes";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/private/recipes/create")
    public String openRecipeForm(Model model) {

        model.addAttribute("recipe", new Recipe());
        return "recipeForm";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/private/recipes/create")
    public String createRecipe(Recipe recipe, @AuthenticationPrincipal User user, BindingResult errors, RedirectAttributes redirectAttributes) {

        recipesService.createRecipe(recipe, user);
        redirectAttributes.addFlashAttribute("message", "msg.recipe.created");
        redirectAttributes.addFlashAttribute("recipeTitle", recipe.getTitle());

        return "redirect:/private/recipes";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("private/recipes/{id}")
    public String openRecipe(@PathVariable UUID id, Model model) {

        model.addAttribute("recipe", recipesService.getRecipe(id));

        return "recipeForm";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/private/recipes/{id}")
    public String updateRecipe(@PathVariable UUID id, Recipe recipe, RedirectAttributes redirectAttributes) {

        recipesService.updateRecipe(id, recipe);
        redirectAttributes.addFlashAttribute("message", "msg.recipe.updated");
        redirectAttributes.addFlashAttribute("recipeTitle", recipe.getTitle());


        return "redirect:/private/recipes";
    }

    @GetMapping("/public/recipes/{id}/review")
    public String reviewRecipe(@PathVariable UUID id, Model model) {

        model.addAttribute("recipe", recipesService.getRecipe(id));

        return "recipeDetails";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/private/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        String deletedRecipeTitle = recipesService.getRecipe(id).getTitle();

        recipesService.deleteRecipe(id);
        redirectAttributes.addFlashAttribute("message", "msg.recipe.deleted");
        redirectAttributes.addFlashAttribute("recipeTitle", deletedRecipeTitle);


        return "redirect:/private/recipes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/recipes/{id}/publish")
    public String publishRecipe(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        recipesService.publishRecipe(id);
        redirectAttributes.addFlashAttribute("message", "msg.recipe.published");
        redirectAttributes.addFlashAttribute("recipeTitle", recipesService.publishRecipe(id).getTitle());

        return "redirect:/private/recipes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/recipes/{id}/unpublish")
    public String unpublishRecipe(@PathVariable UUID id, RedirectAttributes redirectAttributes) {

        Recipe recipe = recipesService.unPublishRecipe(id);
        redirectAttributes.addFlashAttribute("message", "msg.recipe.unpublished");
        redirectAttributes.addFlashAttribute("recipeTitle", recipesService.publishRecipe(id).getTitle());
        return "redirect:/private/recipes";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/private/recipes/search")
    public String searchPrivate(@RequestParam(required = false) String title, Model model, Pageable pageable, @AuthenticationPrincipal User user) {

        Page<Recipe> resultList =  recipesService.searchPrivate(title, pageable, user);
        model.addAttribute("recipes", resultList);

        return "recipes";
    }

    @GetMapping("/public/publishedRecipes/search")
    public String search(@RequestParam(required = false) String title, Model model, Pageable pageable) {

        Page<Recipe> resultList = recipesService.findAllPublishedRecipesByTitle(title, pageable);
        model.addAttribute("recipes", resultList);

        return "publishedRecipes";
    }

}
