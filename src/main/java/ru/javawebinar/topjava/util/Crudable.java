package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface Crudable {
    public boolean create(Meal meal);

    public Meal update(int id,Meal newMeal);

    public boolean delete(int id);

    public Meal getMealById(int id);

    public List<MealTo> getAllMeals();
    }

