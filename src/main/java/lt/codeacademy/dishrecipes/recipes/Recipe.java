package lt.codeacademy.dishrecipes.recipes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @Type(type = "uuid-char")
    private UUID id;

    @NotBlank
    @Size(min=3, max=50)
    private String title;

    @NotBlank
    @Size(max=255)
    private String description;

    @NotBlank
    @Size(max=255)
    private String ingredients;

    @NotBlank
    @Size(max=255)
    private String productionProcess;

    @NotNull
    @Positive
    private BigDecimal preparationTime;

    @NotNull
    @Positive
    private BigDecimal numberOfServings;

}
