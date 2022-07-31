package lt.codeacademy.dishrecipes.recipes.repos;

import lt.codeacademy.dishrecipes.recipes.entities.Recipe;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Profile("!mocked-db")
public interface JpaRecipesRepository extends JpaRepository<Recipe, UUID>{

    @Query("FROM Recipe r WHERE r.isPublished IS true")
    Page<Recipe> findAllPublishedRecipes(Pageable pageable);


//    @Query("FROM Recipe r WHERE r.user_id=:username" )
//    Page<Recipe> findRecipesByUsername(String username, Pageable pageable);


    @Query("FROM Recipe r WHERE r.isPublished IS true AND r.title LIKE %:title%" )
    Page<Recipe> findPublishedRecipeByTitle(String title, Pageable pageable);


//    @Query("FROM Recipe r WHERE r.user.id=:id")
//    Page<Recipe> findUserRecipes(@Param("id") Long id, Pageable pageable);

    Page<Recipe> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
