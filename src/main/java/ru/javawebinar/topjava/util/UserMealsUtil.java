package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
            List<UserMealWithExcess> result = new ArrayList<>();
            Map<Long, Boolean> epochDays = new HashMap();
            long epochday = 0;
            int totalCalories = 0;
            meals.sort(new Comparator<UserMeal>() {
                @Override
                public int compare(UserMeal o1, UserMeal o2) {
                    return o1.getDateTime().compareTo(ChronoLocalDateTime.from(o2.getDateTime()));
                }
            });
        for (UserMeal um :
                meals) {
            LocalTime lt = um.getDateTime().toLocalTime();
            LocalDate ld = um.getDateTime().toLocalDate();
            epochDays.put(ld.toEpochDay(), false);
            if (epochday == 0) {
                epochday = ld.toEpochDay();
                totalCalories += um.getCalories();
            } else if (epochday == ld.toEpochDay()) {
                totalCalories += um.getCalories();
            } else if (epochday != ld.toEpochDay()) {
                if (totalCalories >= caloriesPerDay) {
                    epochDays.put(epochday, true);
                }
                totalCalories = 0;
                totalCalories += um.getCalories();
                epochday = ld.toEpochDay();
            }

        }
        if (totalCalories >= caloriesPerDay) {
            epochDays.put(epochday, true);
        }

        for (UserMeal um :
                meals) {
            LocalTime lt = um.getDateTime().toLocalTime();
            if (lt.compareTo(startTime) >= 0 && lt.isBefore(endTime)) {
                LocalDate ld = um.getDateTime().toLocalDate();
                result.add(new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(), epochDays.get(ld.toEpochDay())));
            }
        }

        return result;
    }
}
