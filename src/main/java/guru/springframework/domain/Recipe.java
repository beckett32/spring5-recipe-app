package guru.springframework.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
@Entity
@Table(name = "Recipe")
public class Recipe implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = -1L;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private String servings;
    private String source;
    private String url;
    @Column(length = 2000)
    private String directions;

    @ManyToMany(mappedBy = "recipes")
    private Set<Category> categories = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private List<Ingredient> ingredients = new ArrayList<>();
    @Lob
    private Byte[] image;
    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Recipe))
            return false;

        Recipe recipe = (Recipe) o;

        return Objects.equals(id, recipe.id);
    }

    @Transient
    public String getEncodedString() {

        byte[] data = new byte[image.length];
        for (int i = 0; i < data.length; i++)
            data[i] = image[i].byteValue();

        byte[] dataEncoder = Base64.getEncoder().encode(data);
        return new String(dataEncoder);
    }

    @Transient
    public Integer getTotalTime() {
        return this.getPrepTime() + this.getCookTime();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.getIngredients().add(ingredient);
    }

    public enum Difficulty {
        EASY, MODERATE, HARD
    }
}
