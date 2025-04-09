package edu.cs489app.library.service;

import edu.cs489app.library.dto.request.PublisherRequestDto;
import edu.cs489app.library.dto.response.PublisherResponseDto;

import java.util.Optional;

public interface PublisherService {
    // Create
    Optional<PublisherResponseDto> createPublisher(PublisherRequestDto publisherDto);

    // Find

    // Find all

    // Update
}
