package owlvernyte.springfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import owlvernyte.springfood.entity.Meal;
import owlvernyte.springfood.service.MealService;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private MealService mealService;
    @GetMapping
    public String home(Model model) {
        return "home/index";
    }
    @GetMapping("/contact")
    public String contact(Model model) {
        return "redirect:/email";
    }
}
