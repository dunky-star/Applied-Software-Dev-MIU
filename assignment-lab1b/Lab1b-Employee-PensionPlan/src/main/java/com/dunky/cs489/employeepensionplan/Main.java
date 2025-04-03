package com.dunky.cs489.employeepensionplan;

import com.dunky.cs489.employeepensionplan.model.Employee;
import com.dunky.cs489.employeepensionplan.model.PensionPlan;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = loadEmployees();

        System.out.println("=== All employees (Sorted by Salary DESC, Last Name ASC) ===");
        printAllEmployeesJson(employees);

        System.out.println("\n=== Quarterly Upcoming Enrollees (Sorted by Employment Date DESC) ===");
        printUpcomingEnrolleesJson(employees);
    }

    private static List<Employee> loadEmployees(){
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Daniel", "Laber",
                LocalDate.of(2018, 1, 17), 105_945.00,
                new PensionPlan("EX1089", LocalDate.of(2023, 1, 17), 100)));
        employeeList.add(new Employee(2, "Benard", "Shaw", LocalDate.of(2022, 9, 3), 197_750.00, null));
        employeeList.add(new Employee(3, "Carly", "Agar", LocalDate.of(2014, 5, 16), 842_000.75,
                new PensionPlan("SM2307", LocalDate.of(2019, 11, 4), 1555.50)));
        employeeList.add(new Employee(4, "Wesley", "Schneider", LocalDate.of(2022, 7, 21), 74_500.00, null));
        employeeList.add(new Employee(5, "Anna", "Wiltord", LocalDate.of(2022, 6, 15), 85_750.00, null));
        employeeList.add(new Employee(6, "Yosef", "Tesfalem", LocalDate.of(2022, 10, 31), 100_000.00, null));
        return employeeList;
    }

    private static void printAllEmployeesJson(List<Employee> employees) {
        JSONArray jsonArray = new JSONArray();

        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::yearlySalary).reversed()
                        .thenComparing(Employee::lastName))
                .forEach( emp -> {
                    JSONObject obj = new JSONObject()
                            .put("employeeId", emp.employeeId())
                            .put("firstName", emp.firstName())
                            .put("lastName", emp.lastName())
                            .put("employmentDate", emp.employmentDate())
                            .put("yearlySalary", String.format("%.2f", emp.yearlySalary()));

                    if (emp.pensionPlan() != null) {
                        JSONObject pension = new JSONObject()
                                .put("planReferenceNumber", emp.pensionPlan().planReferenceNumber())
                                .put("enrollmentDate", emp.pensionPlan().enrollmentDate())
                                .put("yearlySalary", String.format("%.2f", emp.pensionPlan().monthlyContribution()));
                        obj.put("pensionPlan", pension);
                    }
                    jsonArray.put(obj);
                });

        System.out.println(jsonArray.toString(2));
    }

    private static void printUpcomingEnrolleesJson(List<Employee> employees) {
        JSONArray jsonArray = new JSONArray();

        LocalDate now = LocalDate.now();
        int nextMonth = ((now.getMonthValue() - 1) / 3 + 1) * 3 + 1;
        int nextQuarter = (nextMonth > 12) ? 1 : nextMonth;
        int year = (nextMonth > 12) ? now.getYear() + 1 : now.getYear();

        LocalDate startOfNextQuarter = LocalDate.of(year, nextQuarter, 1);
        LocalDate endOfNextQuarter = YearMonth.of(year, nextQuarter + 2).atEndOfMonth();

        employees.stream()
                .filter(emp -> emp.pensionPlan() == null)
                .filter(emp -> {
                    LocalDate eligibleDate = emp.employmentDate().plusYears(3);
                    return !eligibleDate.isAfter(endOfNextQuarter) && !eligibleDate.isBefore(startOfNextQuarter);
                })
                .sorted(Comparator.comparing(Employee::employmentDate).reversed())
                .forEach(emp -> {
                    JSONObject obj = new JSONObject()
                            .put("employeeId", emp.employeeId())
                            .put("firstName", emp.firstName())
                            .put("lastName", emp.lastName())
                            .put("employmentDate", emp.employmentDate())
                            .put("yearlySalary", String.format("%.2f", emp.yearlySalary()));
                    jsonArray.put(obj);
                });

        System.out.println(jsonArray.toString(2));
    }

}

