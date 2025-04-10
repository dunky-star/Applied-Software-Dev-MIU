package edu.cs489app.library.dto.mapper;

import edu.cs489app.library.dto.request.AddressRequestDto;
import edu.cs489app.library.dto.response.AddressResponseDto;
import edu.cs489app.library.model.Address;

public class AddressMapper {

    // Map DTO → Entity
    public static Address toEntity(AddressRequestDto dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setUnitNo(dto.unitNo());
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setZip(dto.zip());
        return address;
    }

    // Map Entity → DTO
    public static AddressResponseDto toDto(Address entity) {
        if (entity == null) return null;

        return new AddressResponseDto(
                entity.getId(),
                entity.getUnitNo(),
                entity.getStreet(),
                entity.getCity(),
                entity.getState(),
                entity.getZip()
        );
    }
}
