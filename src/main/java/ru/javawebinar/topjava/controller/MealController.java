package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class MealController extends HttpServlet {
    private MealDao mealDao = new MealDao();
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/listMeal.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action==null){
            action="";
        }
        String forward = "";
        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealDao.getMealById(mealId);

            mealDao.deleteMeal(mealId);
            forward = LIST_MEAL;


            req.setAttribute("meals", mealDao.getAllMeals());

            resp.sendRedirect("/topjava");
            return;

        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = mealDao.getMealById(mealId);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")) {
            forward = LIST_MEAL;
            req.setAttribute("meals", mealDao.getAllMeals());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = req.getRequestDispatcher(forward);
        view.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal meal = new Meal();
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));


        LocalDateTime ldt = LocalDateTime.parse(req.getParameter("dateTime"));
        meal.setDateTime(ldt);
        String mealId = req.getParameter("id");

        if (mealId == null || mealId.isEmpty()) {
            mealDao.createMeal(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealDao.updateMeal(Integer.parseInt(mealId),meal);
        }
        RequestDispatcher view = req.getRequestDispatcher(LIST_MEAL);
        req.setAttribute("meals", mealDao.getAllMeals());
        view.forward(req, resp);
    }
}
