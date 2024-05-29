package org.example;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = Employees_PensionPlan.loadEmployees();
        List<PensionPlan> pensionPlans = Employees_PensionPlan.loadPensionPlans();

        printAllEmployees(employees, pensionPlans);
        printMonthlyUpcomingEnrollees(employees, pensionPlans);
    }

    private static void printAllEmployees(List<Employee> employees, List<PensionPlan> pensionPlans) {
        List<String> employeeData = employees.stream().map(employee -> {
            String pensionPlanData = "";
            PensionPlan pensionPlan = pensionPlans.stream()
                    .filter(plan -> plan.getPlanReferenceNumber() == employee.getEmployeeId())
                    .findFirst().orElse(null);
            if (pensionPlan != null) {
                pensionPlanData = String.format(", \"pensionPlan\": {\"planReferenceNumber\": %d, \"enrollmentDate\": \"%s\", \"monthlyContribution\": %.2f}",
                        pensionPlan.getPlanReferenceNumber(), pensionPlan.getEnrollmentDate(), pensionPlan.getMonthlyContribution());
            }
            return String.format("{\"employeeId\": %d, \"firstName\": \"%s\", \"lastName\": \"%s\", \"employmentDate\": \"%s\", \"yearlySalary\": %.2f%s}",
                    employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
                    employee.getEmploymentDate(), employee.getYearlySalary(), pensionPlanData);
        }).collect(Collectors.toList());

        System.out.println("[");
        System.out.println(String.join(",\n", employeeData));
        System.out.println("]");
    }

    private static void printMonthlyUpcomingEnrollees(List<Employee> employees, List<PensionPlan> pensionPlans) {
        LocalDate firstDayOfNextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfNextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(
                LocalDate.now().plusMonths(1).lengthOfMonth());

        List<Employee> upcomingEnrollees = employees.stream()
                .filter(employee -> pensionPlans.stream().noneMatch(plan -> plan.getPlanReferenceNumber() == employee.getEmployeeId())) // Not enrolled for a pension
                .filter(employee -> employee.getEmploymentDate().plusYears(5).isBefore(lastDayOfNextMonth.plusDays(1))) // Employed for at least 5 years by the last day of next month
                .filter(employee -> employee.getEmploymentDate().plusYears(5).isAfter(firstDayOfNextMonth.minusDays(1))) // Employed for at least 5 years by the first day of next month
                .sorted((e1, e2) -> e1.getEmploymentDate().compareTo(e2.getEmploymentDate())) // Sort by employment dates
                .collect(Collectors.toList());

        System.out.println("Monthly Upcoming Enrollees Report:");
        System.out.println("[");
        upcomingEnrollees.forEach(employee -> System.out.printf("{\"employeeId\": %d, \"firstName\": \"%s\", \"lastName\": \"%s\", \"employmentDate\": \"%s\", \"yearlySalary\": %.2f},\n",
                employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
                employee.getEmploymentDate(), employee.getYearlySalary()));
        System.out.println("]");
    }

}
