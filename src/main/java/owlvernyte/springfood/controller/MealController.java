package owlvernyte.springfood.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import owlvernyte.springfood.entity.CartItem;
import owlvernyte.springfood.entity.Meal;
import owlvernyte.springfood.service.CartService;
import owlvernyte.springfood.service.MealService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/meals")
public class MealController {
    @Autowired
    private MealService mealService;

    @Autowired
    private CartService cartService;
    @GetMapping
    public String listMeals(Model model) {
        List<Meal> meals = mealService.getAllMeals();
        model.addAttribute("meals", meals);
        return "meal/index";
    }

    @GetMapping("/add")
    public String addMealForm(Model model) {
        model.addAttribute("meal", new Meal());
        return "meal/add";
    }

    @PostMapping("/add")
    public String addMeal(@ModelAttribute("meal") Meal meal, BindingResult bindingResult, Model model) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();

            model.addAttribute("errors", errors);
            return "meal/add";
        }

        mealService.upsertMeal(meal);
        return "redirect:/meals";
    }


    @GetMapping("/edit/{id}")
    public String editMealForm(@PathVariable("id") Long id, Model model) {
        Meal editMeal = mealService.getMealById(id);

        if (editMeal != null) {
            model.addAttribute("meal", editMeal);
            return "meal/edit";
        } else {
            return "error";
        }
    }

    @PostMapping("/edit")
    public String editMeal(@ModelAttribute("meal") Meal updateMeal) {
        mealService.upsertMeal(updateMeal);
        return "redirect:/meals";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        mealService.deleteMeal(id);
        return "redirect:/meals";
    }
    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("imageUrl") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String uploadDir = "F:/JetBrains/images";
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String filePath = uploadDir + File.separator + fileName;
            File destination = new File(filePath);
            file.transferTo(destination);

            model.addAttribute("images", "Uploaded image: " + fileName);
        }

        return "redirect:/meals";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(HttpSession session,
                            @RequestParam long id,
                            @RequestParam String title,
                            @RequestParam double price,
                            @RequestParam(defaultValue = "1") int quantity)
    {
        var cart = cartService.getCart(session);
        cart.addItems(new CartItem(id, title, price, quantity));
        cartService.updateCart(session, cart);
        return "redirect:/meals";
    }
}
