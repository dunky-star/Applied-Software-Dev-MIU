package com.dunky.cs489.employeepensionplan.model;

import java.time.LocalDate;

public record PensionPlan(
        String planReferenceNumber,
        LocalDate enrollmentDate,
        double monthlyContribution

) {
}
