package edu.cs489app.library.service;

import edu.cs489app.library.dto.request.PublisherRequestDto;
import edu.cs489app.library.dto.response.PublisherResponseDto;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    // Create
    Optional<PublisherResponseDto> createPublisher(PublisherRequestDto publisherDto);

    // Find
    Optional<PublisherResponseDto> findPublisherByName(String name);

    // Find all
    List<PublisherResponseDto> findAllPublishers();

    // Update
    Optional<PublisherResponseDto> updatePublisher(String name, PublisherRequestDto publisherDto);
}
