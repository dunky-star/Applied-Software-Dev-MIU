package edu.cs489app.library.dto.request;

public record BookRequestDto(
        String title,
        String isbn,
        String description,
        PublisherRequestDto publisherRequestDto,
        AuthorRequestDto authorRequestDto
) {
}
