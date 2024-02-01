package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

/**
 * This service can be used to generate a ${ReportingStructure} for a given employee.
 */
public interface ReportsService {

    /**
     * Create a ${@link ReportingStructure} for the employee with the given id.
     * Reports will be fetched recursively,
     * and the total number of direct and indirect (children of children) calculated.
     *
     * @param id The id of the employee to get reports for
     * @return a ReportingStructure containing
     */
    public ReportingStructure createReportingStructure(String id);
}
