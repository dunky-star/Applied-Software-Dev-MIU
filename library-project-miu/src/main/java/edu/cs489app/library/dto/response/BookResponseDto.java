package edu.cs489app.library.dto.response;

public record BookResponseDto (
        Long id,
        String title,
        String isbn,
        String description,
        Integer pageCount,
        String publishedDate,
        PublisherResponseDto publisherResponseDto,
        AuthorResponseDto authorResponseDto
) {
}
