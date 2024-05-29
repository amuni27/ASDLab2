package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employees_PensionPlan {
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();


        employees.add(new Employee(1, "Daniel", "Agar", LocalDate.parse("2018-01-17"), 105945.50));
        employees.add(new Employee(2, "Benard", "Shaw", LocalDate.parse("2019-05-03"), 197750.00));
        employees.add(new Employee(3, "Carly", "Agar", LocalDate.parse("2014-05-16"), 842000.75));
        employees.add(new Employee(4, "Wesley", "Schneider", LocalDate.parse("2019-05-02"), 74500.00));

        return employees;
    }

    public static List<PensionPlan> loadPensionPlans() {
        List<PensionPlan> pensionPlans = new ArrayList<>();

        pensionPlans.add(new PensionPlan(1, LocalDate.parse("2023-01-17"), 100.00));
        pensionPlans.add(new PensionPlan(3, LocalDate.parse("2019-11-04"), 1555.50));

        return pensionPlans;
    }
}