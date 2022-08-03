package lt.codeacademy.dishrecipes.users.controllers;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.commons.exceptions.UserException;
import lt.codeacademy.dishrecipes.users.entities.User;
import lt.codeacademy.dishrecipes.users.service.UserService;
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


@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("private/users")
    public String getAllUsers(@PageableDefault(size = 6) Pageable pageable, Model model) {

        Page<User> users = userService.getAllUsers(pageable);

        model.addAttribute("users", users);

        return "users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/private/users/search")
    public String search(@RequestParam(required = false) String username, Model model, Pageable pageable) {

        Page<User> filteredUsers = userService.findUsersByUsername(username, pageable);

        model.addAttribute("users", filteredUsers);

        return "users";
    }

    @GetMapping("/public/signup")
    public String openSignUpForm(Model model) {

        model.addAttribute("user", new User());

        return "signUpForm";
    }

    @PostMapping("/public/signUpForm/create")
    public String createUser(@Valid User user, BindingResult errors, RedirectAttributes redirectAttributes, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            return "signUpForm";
        }

        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("message","msg.user.created");

        } catch (UserException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "redirect:/login";
    }


    //    ADMIN created user
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/private/user/create")
    public String openUserWithRoleForm(Model model) {

        model.addAttribute("user", new User());

        return "userWithRoleForm";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/user/create")
    public String createUserWithRole(User user, RedirectAttributes redirectAttributes) {

        try {
            String selectedRole = user.getRole().getName();
            userService.createUser(user, selectedRole);
            redirectAttributes.addFlashAttribute("message","msg.user.with.role.created");
            redirectAttributes.addFlashAttribute("selectedUser", user.getUsername());
        } catch (UserException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }
        return "redirect:/private/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("private/user/{username}")
    public String openRecipe(@PathVariable String username, Model model) {

        User userToUpdate = userService.findUserByUsername(username);
        model.addAttribute("user", userToUpdate);

        return "userWithRoleForm";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/user/{username}")
    public String updateRecipe(@PathVariable String username, User user, Model model, RedirectAttributes redirectAttributes) {

        userService.updateUser(username, user);
        redirectAttributes.addFlashAttribute("message", "msg.user.updated");
        redirectAttributes.addFlashAttribute("selectedUser", username);
        return "redirect:/private/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/private/user/{username}/delete")
    public String deleteRecipe(@PathVariable String username, RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user) {

        try {
            userService.deleteUser(username, user);
            redirectAttributes.addFlashAttribute("message", "msg.user.deleted");
            redirectAttributes.addFlashAttribute("selectedUser", username);
        } catch (UserException e) {
            redirectAttributes.addFlashAttribute("failMessage", e.getMessage());
        }

        return "redirect:/private/users";
    }
}