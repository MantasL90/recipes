package lt.codeacademy.dishrecipes.users.controllers;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.commons.validators.RegistrationValidator;
import lt.codeacademy.dishrecipes.users.entities.User;
import lt.codeacademy.dishrecipes.users.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RegistrationValidator registrationValidator;

    @InitBinder(value = "user")
    void initStudentValidator(WebDataBinder binder){
        binder.addValidators(registrationValidator);
    }

//    @PostMapping("/public/signUpForm/create")
//    public String createUser(@Valid User user, BindingResult errors, RedirectAttributes redirectAttributes){
//
//        if(errors.hasErrors()){
//            return "signUpForm";
//        }
//
//        userService.createUser(user);
//
//        redirectAttributes.addFlashAttribute("message",
//                String.format("User '%s' succsessfully created!", user.getUsername()));
//
//        return "redirect:/public/publishedRecipes";
//    }
}