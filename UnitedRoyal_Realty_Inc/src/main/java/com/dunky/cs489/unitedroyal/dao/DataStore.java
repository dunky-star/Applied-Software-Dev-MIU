package com.dunky.cs489.unitedroyal.dao;

import com.dunky.cs489.unitedroyal.model.Apartment;
import com.dunky.cs489.unitedroyal.model.Lease;

import java.time.LocalDate;
import java.util.List;

public class DataStore {
    public static List<Apartment> getApartments() {
        Apartment a1 = new Apartment("B1102", "The Cameron House", 11, 790, 3);
        Apartment a2 = new Apartment("A705", "The Cameron House", 7, 855, 4);
        Apartment a3 = new Apartment("C1210", "Pointe Palace", 12, 1000, 4);
        Apartment a4 = new Apartment("A1371", "Pointe Palace", 13, 1000, 4);

        a1.addLease(new Lease(2927458265L, LocalDate.of(2025,4,2), LocalDate.of(2025,10,2), new BigDecimal("1500.00"), "Anna Smith", a1));
        a2.addLease(new Lease(3128874121L, LocalDate.of(2025,2,1), LocalDate.of(2026,2,1), new BigDecimal("1750.50"), "Michael Philips", a2));
        a2.addLease(new Lease(3128874119L, LocalDate.of(2023,2,1), LocalDate.of(2024,2,1), new BigDecimal("1650.55"), "Michael Philips", a2));
        a4.addLease(new Lease(9189927460L, LocalDate.of(2025,3,1), LocalDate.of(2026,3,1), new BigDecimal("2560.75"), "Alex Campos", a4));

        return Arrays.asList(a1, a2, a3, a4);
}
