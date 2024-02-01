package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure createReportingStructure(String id) {
        Employee employee = employeeService.read(id);

        employeeService.hydrateDirectReports(employee);

        // Subtract one from the number of reports, to not include the employee themselves
        int numberOfReports = (int) flattenReports(employee).count() - 1;

        return new ReportingStructure(employee, numberOfReports);
    }

    /**
     * Flatten the employee and their direct reports into a single stream.
     * This is used recursively to walk the tree of reports.
     *
     * Note that the initial employee is also included in flattened structure
     *
     * @param employee The employee to flatten
     * @return A flattened stream of the employee, their reports, their reports' reports, and so on.
     */
    private Stream<Employee> flattenReports(Employee employee) {
        if (employee.getDirectReports() == null) {
            return Stream.of(employee);
        }

        return Stream.concat(
                Stream.of(employee),
                employee.getDirectReports().stream().flatMap(this::flattenReports)
        );
    }
}
