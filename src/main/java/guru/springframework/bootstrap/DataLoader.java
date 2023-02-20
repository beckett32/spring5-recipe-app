package guru.springframework.bootstrap;

import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.*;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final NoteRepository noteRepository;
    private final IngredientRepository ingredientRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, NoteRepository noteRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.noteRepository = noteRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            recipes.add(createPerfectGuacamole());
            recipes.add(createSpicyGrilledChicken());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recipeRepository.saveAll(recipes);
    }

    private Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }

    private Recipe createPerfectGuacamole() throws IOException {
        Recipe recipe = new Recipe();
        recipe.getCategories().add(categoryRepository.findByDescription("American").get());
        recipe.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        recipe.setDescription("The best guacamole keeps it simple: just ripe avocados and a handful of flavorful mix-ins. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");
        recipe.setDirections("Cut the avocados:\n" + "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to " + "Cut and Peel an Avocado.) Place in a bowl.\n" + "\n" + "How to make guacamole - scoring avocado\n" + "Mash the avocado flesh:\n" + "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" + "\n" + "How to make guacamole - smashing avocado with fork\n" + "Add the remaining ingredients to taste:\n" + "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" + "\n" + "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" + "\n" + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" + "\n" + "Serve immediately:\n" + "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" + "\n" + "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" + "\n" + "Refrigerate leftover guacamole up to 3 days.\n" + "\n" + "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        recipe.setDifficulty(Recipe.Difficulty.EASY);


        File file = ResourceUtils.getFile("classpath:templates/static/images/guacamole.jpg");
        InputStream in = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(data);

        recipe.setImage(toObjects(data));
        recipe.setCookTime(10);

        Notes note = new Notes();
        note.setNotes("Be careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, " + "and do not" + " touch your " + "eyes or the area near your eyes for several hours afterwards.");
        note.setRecipe(recipe);
        recipe.setNotes(note);
        recipe.setPrepTime(10);
        recipe.setCookTime(0);
        recipe.setServings("2 to 4");
        recipe.setSource("Perfect Guacamole");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        //recipe = recipeRepository.save(recipe);
        recipe.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), unitOfMeasureRepository.findByDescription("Each").get()));
        recipe.addIngredient(new Ingredient("kosher salt", new BigDecimal(.25), unitOfMeasureRepository.findByDescription("Teaspoon").get()));
        recipe.addIngredient(new Ingredient("fresh lime or lemon juice", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Tablespoon").get()));
        recipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion ", new BigDecimal(2), unitOfMeasureRepository.findByDescription("Tablespoon").get()));
        recipe.addIngredient(new Ingredient("serrano (or jalapeño) chilis, stems and seeds removed, minced", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Each").get()));
        recipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), unitOfMeasureRepository.findByDescription("Tablespoon").get()));
        recipe.addIngredient(new Ingredient("freshly ground black pepper", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Pinch").get()));
        recipe.addIngredient(new Ingredient("ripe tomato", new BigDecimal(.5), unitOfMeasureRepository.findByDescription("Each").get()));
        recipe.addIngredient(new Ingredient("Red radish or jicama slices for garnish (optional)", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Each").get()));


        return recipe;
    }

    private Recipe createSpicyGrilledChicken() throws IOException {
        Recipe recipe = new Recipe();
        recipe.getCategories().add(categoryRepository.findByDescription("American").get());
        recipe.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        recipe.setDescription("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");
        recipe.setDirections("Prepare the grill:\n" + "Prepare either a gas or charcoal grill for medium-high, direct heat.\n" + "\n" + "Make the marinade and coat the chicken:\n" + "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" + "\n" + "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" + "\n" + "Spicy Grilled Chicken Tacos\n" + "Grill the chicken:\n" + "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165°F. Transfer to a plate and rest for 5 minutes.\n" + "\n" + "Thin the sour cream with milk:\n" + "Stir together the sour cream and milk to thin out the sour cream to make it easy to drizzle.\n" + "\n" + "Assemble the tacos:\n" + "Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" + "\n" + "Warm the tortillas:\n" + "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" + "\n" + "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" + "\n" + "NUTRITION FACTS\n" + "(PER SERVING)\n" + "655\n" + "CALORIES\n" + "44g\n" + "FAT\n" + "32g\n" + "CARBS\n" + "41g\n" + "PROTEIN\n");
        recipe.setDifficulty(Recipe.Difficulty.EASY);


        File file = ResourceUtils.getFile("classpath:templates/static/images/spicy_grilled_chicken.jpg");
        InputStream in = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(data);

        recipe.setImage(toObjects(data));

        Notes note = new Notes();
        note.setNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");
        note.setRecipe(recipe);
        recipe.setNotes(note);
        recipe.setPrepTime(20);
        recipe.setCookTime(15);
        recipe.setServings("4 to 6");
        recipe.setSource("Spicy Grilled Chicken ");
        recipe.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");

        // recipe = recipeRepository.save(recipe);

        recipe.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), unitOfMeasureRepository.findByDescription("Tablespoon").get()));
        recipe.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Teaspoon").get()));
        recipe.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Teaspoon").get()));
        recipe.addIngredient(new Ingredient("sugar", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Teaspoon").get()));
        recipe.addIngredient(new Ingredient("kosher salt", new BigDecimal(.5), unitOfMeasureRepository.findByDescription("Teaspoon").get()));
        recipe.addIngredient(new Ingredient("clove garlic, finely chopped", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Each").get()));
        recipe.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), unitOfMeasureRepository.findByDescription("Tablespoon").get()));
        recipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), unitOfMeasureRepository.findByDescription("Tablespoon").get()));
        recipe.addIngredient(new Ingredient("olive oil", new BigDecimal(2), unitOfMeasureRepository.findByDescription("Tablespoon").get()));
        recipe.addIngredient(new Ingredient("skinless, boneless chicken thighs (1 1/4 pounds)", new BigDecimal(4), unitOfMeasureRepository.findByDescription("Each").get()));


        return recipe;
    }
}
