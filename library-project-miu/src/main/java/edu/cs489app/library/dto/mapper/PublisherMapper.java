package edu.cs489app.library.dto.mapper;

import edu.cs489app.library.dto.request.PublisherRequestDto;
import edu.cs489app.library.dto.response.PublisherResponseDto;
import edu.cs489app.library.model.Publisher;

public class PublisherMapper {

    // Map DTO → Entity
    public static Publisher toEntity(PublisherRequestDto dto) {
        if (dto == null) return null;

        Publisher publisher = new Publisher();
        publisher.setName(dto.name());
        publisher.setAddress(AddressMapper.toEntity(dto.addressRequestDto()));
        return publisher;
    }

    // Map Entity → DTO
    public static PublisherResponseDto toDto(Publisher entity) {
        if (entity == null) return null;

        return new PublisherResponseDto(
                entity.getId(),
                entity.getName(),
                AddressMapper.toDto(entity.getAddress())
        );
    }
}
