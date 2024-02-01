package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {

    // Custom mongo query here since employeeId is nested in the employee object
    @Query("{'employee.employeeId': ?0}")
    Compensation findByEmployeeId(String employeeId);
}
