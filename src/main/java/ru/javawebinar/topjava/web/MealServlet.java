package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.cert.CollectionCertStoreParameters;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        this.controller = appCtx.getBean(MealRestController.class);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("userid")!=null){
            Integer userId =Integer.parseInt(request.getParameter("userid"));
            SecurityUtil.setAuthUserId(userId);
            response.sendRedirect("meals");
            return;
        }
        if (request.getParameter("filter")!=null) {
            String dateFrom = request.getParameter("dateFrom")==null?"":request.getParameter("dateFrom");
            String dateTo = request.getParameter("dateTo")==null?"":request.getParameter("dateTo");
            String timeFrom = request.getParameter("timeFrom")==null?"":request.getParameter("timeFrom");
            String timeTo = request.getParameter("timeTo")==null?"":request.getParameter("timeTo");
            LocalDate startDate = dateFrom.equals("") ? null : LocalDate.parse(dateFrom);
            LocalDate endDate = dateTo.equals("") ? null : LocalDate.parse(dateTo);
            LocalTime startTime = timeFrom.equals("") ? null : LocalTime.parse(timeFrom);
            LocalTime endTime = timeTo.equals("") ? null : LocalTime.parse(timeTo);
            List<MealTo> mealTos = null;
            boolean dateFilterActive = false;
            if (startDate!=null&&endDate!=null&&!startDate.equals("") && !endDate.equals("")){
                mealTos = controller.getAll()
                        .stream()
                        .filter(meal->DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalDate(),startDate,endDate))
                        .collect(Collectors.toList());
                dateFilterActive = true;
                request.setAttribute("dateFrom",dateFrom);
                request.setAttribute("dateTo",dateTo);
            }
            if (startTime!=null&&endTime!=null&&!startTime.equals("") &&!endTime.equals("")){
                if (!dateFilterActive){
                    mealTos = controller.getAll();
                } mealTos = mealTos.stream()
                        .filter(meal->DateTimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(),startTime,endTime))
                        .collect(Collectors.toList());
                request.setAttribute("timeFrom",timeFrom);
                request.setAttribute("timeTo",timeTo);
                }
            if (mealTos!=null){
                request.setAttribute("meals",mealTos);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                return;
            }
            }


        String id = request.getParameter("id");
        String userId = request.getParameter("userId");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        meal.setUserId(userId.isEmpty()?null:Integer.valueOf(userId));
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()){
            controller.create(meal);
        }else controller.update(meal,Integer.valueOf(id));
        response.sendRedirect("meals");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(null,LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;

            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        controller.getAll().stream().filter(e->e.getUserId()==SecurityUtil.authUserId()).collect(Collectors.toList()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
