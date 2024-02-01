package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    // Provide a sane max depth for this example, to prevent infinite loops
    // while hydrating. If the employee objects were being kept in a cache then
    // it would likely be better to do this during the json rendering.
    @Override
    public Employee hydrateDirectReports(Employee employee) {
        return this.hydrateDirectReports(employee, 100);
    }

    // Since the directReports property is only partially hydrated and does not store
    // the full employee information, allow deep hydration.
    // Because we don't have any caching layer, and for the simplicity of this code challenge,
    // I'll perform the hydration in-place. Otherwise, I would prefer to return a copy of the original object.
    // If we were using a newer version of springboot, I would prefer to use @DocumentReference on the entity itself
    public Employee hydrateDirectReports(Employee employee, int maxDepth) {
        if (employee.getDirectReports() == null) {
            return employee;
        }

         employee.setDirectReports(
                employee.getDirectReports()
                        .stream()
                        .map(e -> read(e.getEmployeeId()))
                        .map(e -> hydrateDirectReports(e, maxDepth - 1))
                        .collect(Collectors.toList())
        );

        return employee;
    }
}
