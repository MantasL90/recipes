package lt.codeacademy.dishrecipes.users.entities;

import lombok.Getter;
import lombok.Setter;
import lt.codeacademy.dishrecipes.recipes.entities.Recipe;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private String name;
    private String surname;
    private String emailAddress;

    @OneToMany(mappedBy="user")
    private Set<Recipe> recipes;
    @ManyToOne
    private Role role;

    @Override
    public Set<Role> getAuthorities() {
        return Set.of(role);
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
