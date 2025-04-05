package com.dunky.cs489.unitedroyal.service;

import com.dunky.cs489.unitedroyal.model.Apartment;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ApartmentServiceImpl implements ApartmentService {
    @Override
    public List<Apartment> sortApartments(List<Apartment> apartments) {
        return apartments.stream()
                .sorted(Comparator.comparingDouble(Apartment::size).reversed()
                        .thenComparing(Apartment::apartmentNo))
                .collect(Collectors.toList());
    }
}
