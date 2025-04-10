package edu.cs489app.library.dto.request;

// NB: Don't maintain Bidirectional relationship in Dto to avoid loop/stack overflow.
public record AddressRequestDto (
        String unitNo,
        String street,
        String city,
        String state,
        Integer zip
){
}
