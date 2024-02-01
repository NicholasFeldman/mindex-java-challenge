package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportsServiceImplTest {

    private String reportsUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before()
    public void setup() {
        reportsUrl = "http://localhost:" + port + "/employee/{id}/reports";
    }

    // I use hardcoded ids and expected data from the test data for this test for simplicity.
    // Ideally this test would be a bit more versatile
    @Test
    public void testRead() {
        String testEmployeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportsUrl, ReportingStructure.class, testEmployeeId).getBody();
        assertEquals(testEmployeeId, readReportingStructure.getEmployee().getEmployeeId());
        assertEquals(4, readReportingStructure.getNumberOfReports());
    }
}
