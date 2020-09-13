package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListStorage implements Crudable {
    private List<Meal> meals = new CopyOnWriteArrayList();
    private AtomicInteger counter = new AtomicInteger(0);

    public List<Meal> getMeals() {
        return meals;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    @Override
        public boolean create(Meal meal) {
            meal.setId(counter.incrementAndGet());
            boolean added = meals.add(meal);
            if (added) {

            }else {
                counter.decrementAndGet();
            }
            return added;
    }

    @Override
    public Meal update(int id,Meal newMeal) {
        id=id-1;

        Meal updatedMeal = meals.set(id, newMeal);
        return updatedMeal;
    }

    @Override
    public boolean delete(int id) {
        id=id-1;
        Meal meal = meals.get(id);
        boolean deleted = meals.remove(meal);
        if (deleted){
            counter.decrementAndGet();
            reidentity();
        }

        return deleted;
    }

    public void reidentity(){
        for (int i = 0; i < meals.size(); i++) {
            meals.get(i).setId(i+1);
        }
    }

    @Override
    public Meal getMealById(int id) {
        id=id-1;
        return meals.get(id);
    }

    @Override
    public List<MealTo> getAllMeals() {
        List<MealTo> mealTo = MealsUtil.filteredByCycles(meals, LocalTime.of(00,00),LocalTime.of(23,59),2000);
        for (int i = 0; i < mealTo.size(); i++) {
            mealTo.get(i).setId(i+1);
        }
        Iterator<MealTo> listIterator = mealTo.iterator();
        int id = 1;
        while (listIterator.hasNext()){
            listIterator.next().setId(id);
            id++;
        }
        return mealTo;
    }
}
