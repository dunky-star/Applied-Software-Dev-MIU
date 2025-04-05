package com.dunky.cs489.unitedroyal.service;

import com.dunky.cs489.unitedroyal.model.Lease;

import java.math.BigDecimal;
import java.util.List;

public sealed interface LeaseService permits LeaseServiceImpl {
    BigDecimal calculateTotalRevenue(List<Lease> leases);
}