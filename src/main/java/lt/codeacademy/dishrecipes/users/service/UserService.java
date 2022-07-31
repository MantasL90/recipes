package lt.codeacademy.dishrecipes.users.service;

import lt.codeacademy.dishrecipes.users.entities.User;
import lt.codeacademy.dishrecipes.users.repos.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findUserWithRoles(username)
                .orElseThrow();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }
}
