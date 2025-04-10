package edu.cs489app.library.dto.response;

import java.util.List;

public record BookResponseDto (
        Long id,
        String title,
        String isbn,
        String description,
        Integer pageCount,
        String publishedDate,
        PublisherResponseDto publisherResponseDto,
        List<AuthorResponseDto> authorResponseDto
) {
}
