package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public List<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    public Meal get(int id, int authUserId) {
        checkMealAcess(id,authUserId);
        return checkNotFoundWithId(repository.get(id),id);
    }

    public Meal create(Meal meal,int authUserId) {
        checkNew(meal);
        meal.setUserId(authUserId);
        return repository.save(meal);
    }

    public void delete(int id, int authUserId) {
        checkMealAcess(id,authUserId);
       checkNotFoundWithId(repository.delete(id),id);
    }

    public void update(Meal meal, int authUserId) {
        checkMealAcess(meal.getId(),authUserId);
        checkNotFoundWithId(repository.save(meal),meal.getId());
    }
}