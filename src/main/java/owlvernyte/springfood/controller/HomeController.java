package owlvernyte.springfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import owlvernyte.springfood.entity.Meal;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home(Model model) {
        return "redirect:/meals";
    }
}
