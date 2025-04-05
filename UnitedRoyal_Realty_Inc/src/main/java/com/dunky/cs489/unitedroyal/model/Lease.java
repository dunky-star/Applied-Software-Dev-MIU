package com.dunky.cs489.unitedroyal.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record Lease(
        long leaseNo,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal monthlyRentalRate,
        String tenant,
        String apartmentNo
) {
    // Method to calculate revenue for a lease
    public BigDecimal calculateLeaseRevenue() {
        long months = ChronoUnit.MONTHS.between(startDate, endDate);
        return monthlyRentalRate.multiply(BigDecimal.valueOf(months));
    }
}