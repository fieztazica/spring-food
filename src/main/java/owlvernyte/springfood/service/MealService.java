package owlvernyte.springfood.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import owlvernyte.springfood.entity.Meal;
import owlvernyte.springfood.repository.IMealRepository;

import java.util.List;

@Service
public class MealService {
    @Autowired
    private IMealRepository mealRepository;

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Meal getMealById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    public void upsertMeal(Meal meal) {
        mealRepository.save(meal);
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }

    public Page<Meal> getAllMeals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mealRepository.findAll(pageable);
    }
}
