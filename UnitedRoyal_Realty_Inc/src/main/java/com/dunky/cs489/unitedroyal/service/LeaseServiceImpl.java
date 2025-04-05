package com.dunky.cs489.unitedroyal.service;

import com.dunky.cs489.unitedroyal.model.Lease;

import java.math.BigDecimal;
import java.util.List;

public final class LeaseServiceImpl implements LeaseService {
    @Override
    public BigDecimal calculateTotalRevenue(List<Lease> leases) {
        return leases.stream()
                .map(Lease::calculateLeaseRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
