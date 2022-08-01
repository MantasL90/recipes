package lt.codeacademy.dishrecipes.users.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "Roles")
public class Role implements GrantedAuthority {

    private static final String ROLE_PREFIX = "ROLE_";

    @Id
    private String name;

    @Override
    public String getAuthority() {
        return ROLE_PREFIX + name;
    }
}
