package lt.codeacademy.dishrecipes.recipes.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.codeacademy.dishrecipes.users.entities.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Recipe")
public class Recipe {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    @Size(min=3, max=50)
    private String title;

    private boolean isPublished;

    @NotBlank
    private String description;

    @NotBlank
    @Size(min = 3, max = 255)
    private String ingredients;

    @NotBlank
    @Size(min = 3, max = 255)
    private String preparation;

    @NotBlank
    @Size(min = 3, max = 255)
    private String preparationTime;

    @NotNull
    @Positive
    private Integer servings;

    @ManyToOne
    @JoinColumn(name="username", nullable=false)
    private User user;

}

