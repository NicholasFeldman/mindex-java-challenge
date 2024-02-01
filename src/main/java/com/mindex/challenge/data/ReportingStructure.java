package com.mindex.challenge.data;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Transient;

/**
 * This entity is purely transactional, and is never used to persist/load from the database itself.
 * It's used to represent the reports of an employee, to be serialized and returned to the client.
 * It is created dynamically by a ${@link com.mindex.challenge.service.ReportsService} implementation.
 */
public class ReportingStructure {

    @Transient
    private final Employee employee;

    @Transient
    private final int numberOfReports;

    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    @JsonView(DefaultView.class)
    public Employee getEmployee() {
        return this.employee;
    }

    @JsonView(DefaultView.class)
    public int getNumberOfReports() {
        return this.numberOfReports;
    }

    /**
     * Default view for the ReportingStructure.
     */
    // Since we are composing Employees, I wanted to include a minimal view of that.
    // In most all other cases, this would be dependent on some more heavy API design consideration.
    // However, in this case I wanted to avoid redundancy with the regular employee endpoint,
    // especially considering that a _new_ endpoint was requested, so I must assume that the original endpoint
    // should not be modified or broken in any way.
    public interface DefaultView extends Employee.ReportsView {}
}
