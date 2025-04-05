package com.dunky.cs489.unitedroyal.service;

import com.dunky.cs489.unitedroyal.model.Apartment;

import java.util.List;

public sealed interface ApartmentService permits ApartmentServiceImpl {
    List<Apartment> sortApartments(List<Apartment> apartments);
}
