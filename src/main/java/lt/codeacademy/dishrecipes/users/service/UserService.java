package lt.codeacademy.dishrecipes.users.service;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.commons.exceptions.UserException;
import lt.codeacademy.dishrecipes.recipes.repos.JpaRecipesRepository;
import lt.codeacademy.dishrecipes.users.entities.Role;
import lt.codeacademy.dishrecipes.users.entities.User;
import lt.codeacademy.dishrecipes.users.repos.JpaUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private JpaUserRepository jpaUserRepository;
    private JpaRecipesRepository jpaRecipesRepository;

    private PasswordEncoder passwordEncoder;

    public Page<User> getAllUsers(Pageable pageable) {

        return jpaUserRepository.findAll(pageable);
    }

    public Page<User> findUsersByUsername(String username,Pageable pageable) {

        return jpaUserRepository.findByUsernameContainingIgnoreCase(username,pageable);
    }

    public User findUserByUsername(String username){


        return jpaUserRepository.findByUsernameEquals(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return jpaUserRepository.findUserWithRoles(username)
                .orElseThrow();
    }

    public void createUser(User user) {

        if(findUserByUsername(user.getUsername()) != null) {
            throw new UserException("msg.user.exists");
        }

        String encoded = "{bcrypt}" + passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        user.setRole(new Role("USER"));

        jpaUserRepository.save(user);
    }

    public void createUser(User user, String role) {

        if(findUserByUsername(user.getUsername()) != null)  {
            throw new UserException("msg.user.exists");
        }

        String encoded = "{bcrypt}" + passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        user.setRole(new Role(role));

        jpaUserRepository.save(user);
    }

    @Transactional
    public void updateUser(String username, User user) {

        User oldUser = findUserByUsername(username);

        oldUser.setName(user.getName());
        oldUser.setSurname(user.getSurname());
        oldUser.setEmailAddress(user.getEmailAddress());
        oldUser.setRole(new Role(user.getRole().getName()));
    }

    @Transactional
    public void deleteUser(String username, @AuthenticationPrincipal User user) {

        if(username.equalsIgnoreCase(user.getUsername())) {
            throw new UserException("msg.user.not.deleted");
        }
          if( jpaRecipesRepository.findRecipesByUsername(username) != null) {
            jpaRecipesRepository.deleteAllInBatch(jpaRecipesRepository.findRecipesByUsername(username));
        }
        User userToDelete = findUserByUsername(username);
        jpaUserRepository.delete(userToDelete);
    }
}
