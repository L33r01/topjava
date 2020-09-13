package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.Crudable;
import ru.javawebinar.topjava.util.ListStorage;

import java.util.List;

public class MealDao {
    private Crudable data = new ListStorage();

    public void createMeal(Meal meal) {
        data.create(meal);
    }

    public void updateMeal(int id,Meal newMeal) {
        data.update(id,newMeal);
    }

    public void deleteMeal(int id) {
        data.delete(id);
    }

    public Meal getMealById(int id){
        return data.getMealById(id);
    }

    public List<MealTo> getAllMeals(){
        return data.getAllMeals();
    }

}
