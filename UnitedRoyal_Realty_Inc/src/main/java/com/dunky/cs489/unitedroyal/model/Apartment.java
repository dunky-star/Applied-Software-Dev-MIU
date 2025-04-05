package com.dunky.cs489.unitedroyal.model;

import java.util.List;

public record Apartment(
    String apartmentNo,
    String propertyName,
    int floorNo,
    double size,
    int numberOfRooms,
    List<Lease> leases
    ){
}
