package ru.javawebinar.topjava.util;

import org.w3c.dom.ls.LSOutput;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    /*  Реализовать метод `UserMealsUtil.filteredByCycles` через циклы (`forEach`):
                -  должны возвращаться только записи между `startTime` и `endTime`
                -  поле `UserMealWithExcess.excess` должно показывать,
        превышает ли сумма калорий за весь день значение `caloriesPerDay`

        Т.е `UserMealWithExcess` - это запись одной еды, но поле `excess` будет одинаково для всех записей за этот день.
                - Проверьте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
                - Оцените Time complexity алгоритма. Если она больше O(N), например O(N*N) или N*log(N), сделайте O(N). */
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // collecting info about calories on each day
        Map<LocalDate, Integer> daysAndCalories = new HashMap<>();
        meals.forEach(meal -> {
            if (daysAndCalories.containsKey(meal.getDateTime().toLocalDate())) {
                daysAndCalories.computeIfPresent(meal.getDateTime().toLocalDate(), (key, value) -> value + meal.getCalories());
            } else {
                daysAndCalories.putIfAbsent(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        });
        // calculating excesses
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal userMeal : meals) {
            boolean excess = caloriesPerDay < daysAndCalories.get(userMeal.getDateTime().toLocalDate());
            if (TimeUtil.isBetweenInclusive(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExcessList.add(convertMeal(userMeal, excess));
            }
        }
        return userMealWithExcessList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // collecting info about calories on each day
        Map<LocalDate, Integer> daysAndCalories = meals.stream().collect(Collectors.groupingBy(key -> key.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        // calculating excesses
        return meals.stream().filter(e -> TimeUtil.isBetweenInclusive(e.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> convertMeal(meal, caloriesPerDay < daysAndCalories.get(meal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }

    public static UserMealWithExcess convertMeal(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }
}
