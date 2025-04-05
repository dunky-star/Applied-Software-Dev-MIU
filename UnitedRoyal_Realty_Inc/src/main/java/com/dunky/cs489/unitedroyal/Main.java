package com.dunky.cs489.unitedroyal;

import com.dunky.cs489.unitedroyal.model.Apartment;
import com.dunky.cs489.unitedroyal.model.Lease;
import com.dunky.cs489.unitedroyal.service.ApartmentService;
import com.dunky.cs489.unitedroyal.service.ApartmentServiceImpl;
import com.dunky.cs489.unitedroyal.service.LeaseService;
import com.dunky.cs489.unitedroyal.service.LeaseServiceImpl;
import com.dunky.cs489.unitedroyal.util.JsonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Lease
        Lease lease1 = new Lease(3128874121L, LocalDate.of(2025,2,1), LocalDate.of(2026,2,1), new BigDecimal("1750.50"), "Michael Philips", "A705");
        Lease lease2 = new Lease(2927458265L, LocalDate.of(2025,4,2), LocalDate.of(2025,10,2), new BigDecimal("1500.00"), "Anna Smith", "B1102");
        Lease lease3 = new Lease(9189927460L, LocalDate.of(2025,3,1), LocalDate.of(2026,3,1), new BigDecimal("2560.75"), "Alex Campos", "A1371");
        Lease lease4 = new Lease(3128874119L, LocalDate.of(2023,2,1), LocalDate.of(2024,2,1), new BigDecimal("1650.55"), "Michael Philips", "A705");

        // Apartment
        Apartment a1 = new Apartment("B1102", "The Cameron House", 11, 790, 3, List.of(lease2));
        Apartment a2 = new Apartment("A705", "The Cameron House", 7, 855, 4, List.of(lease1, lease4));
        Apartment a3 = new Apartment("C1210", "Pointe Palace", 12, 1000, 4, List.of());
        Apartment a4 = new Apartment("A1371", "Pointe Palace", 13, 1000, 4, List.of(lease3));

        List<Apartment> apartments = List.of(a1, a2, a3, a4);
        List<Lease> leases = List.of(lease1, lease2, lease3, lease4);


        // Services
        ApartmentService apartmentService = new ApartmentServiceImpl();
        LeaseService leaseService = new LeaseServiceImpl();

        // Output Sorted Apartments as JSON
        List<Apartment> sortedApartments = apartmentService.sortApartments(apartments);
        String jsonOutput = JsonUtil.convertApartmentsToJson(sortedApartments);
        System.out.println("\nSorted Apartments (JSON):\n" + jsonOutput);

        // Output Total Revenue
        BigDecimal totalRevenue = leaseService.calculateTotalRevenue(leases);
        System.out.printf("\nTotal Lease Revenue: $%,.2f\n", totalRevenue);
    }
}