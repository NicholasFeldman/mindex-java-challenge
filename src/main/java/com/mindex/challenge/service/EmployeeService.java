package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);

    /**
     * Hydrate in-place the direct reports of the given employee.
     * @param employee The employee to hydrate reports for
     * @return The given employee, with direct reports hydrated
     */
    Employee hydrateDirectReports(Employee employee);
}
