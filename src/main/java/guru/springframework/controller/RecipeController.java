package guru.springframework.controller;

import guru.springframework.service.RecipeService;
import guru.springframework.service.RecipeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @CrossOrigin
    @GetMapping(path = {"", "/", "index.html", "index"})
    public String recipeIndex(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipe";
    }
}
