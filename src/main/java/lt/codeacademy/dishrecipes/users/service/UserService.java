package lt.codeacademy.dishrecipes.users.service;

import lombok.AllArgsConstructor;
import lt.codeacademy.dishrecipes.users.entities.User;
import lt.codeacademy.dishrecipes.users.repos.JpaUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private JpaUserRepository jpaUserRepository;

    private List<User> getAppUsers() {
        return jpaUserRepository.findAll();
    }

    public User findUserByUsername(String username){
        return jpaUserRepository.findByUsernameEquals(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return jpaUserRepository.findUserWithRoles(username)
                .orElseThrow();
    }

//    public void createUser(User user) {
//
//        User newUser= user;
//
//        newUser.setRole(Role.roleValueToString("USER"));
//
//        jpaUserRepository.save(user);
//    }
}
