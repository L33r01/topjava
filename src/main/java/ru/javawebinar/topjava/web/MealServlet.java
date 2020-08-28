package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    public static final List<MealTo> meals= new ArrayList();
    public MealServlet(){
        meals.add(new MealTo(LocalDateTime.of(2020,04,15,12,13,24),"myaso",150,true));
        meals.add(new MealTo(LocalDateTime.of(2020,05,15,12,13,24),"pechen",25,false));
        meals.add(new MealTo(LocalDateTime.of(2020,06,15,12,13,24),"morozhenoe",65,false));
        meals.add(new MealTo(LocalDateTime.of(2020,07,15,12,13,24),"yagodi",65,true));
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

//        resp.sendRedirect("meals.jsp");
        String test = "testing";
        req.setAttribute("meals",meals);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
