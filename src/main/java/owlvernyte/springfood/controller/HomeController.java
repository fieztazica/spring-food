package owlvernyte.springfood.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import owlvernyte.springfood.entity.Meal;
import owlvernyte.springfood.service.CartService;
import owlvernyte.springfood.service.MealService;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private MealService mealService;

    @GetMapping
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated())
            model.addAttribute("role",
                    authentication.getAuthorities());
        model.addAttribute("meals",mealService.getAllMeals());
        return "home/index";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "redirect:/email";
    }
}
