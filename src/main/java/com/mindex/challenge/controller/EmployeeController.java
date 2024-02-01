package com.mindex.challenge.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ReportsService reportsService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    // I decided to add this endpoint here instead of creating a new controller,
    // because it's intrinsically related to the employee and more or less just a derived view.
    // This is most in the spirit of REST, and also logical for the consumer of the API.
    @JsonView(ReportingStructure.DefaultView.class)
    @GetMapping("/employee/{id}/reports")
    public ReportingStructure readReports(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        return reportsService.createReportingStructure(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
}
