package edu.cs489app.library.service;

import edu.cs489app.library.dto.mapper.AddressMapper;
import edu.cs489app.library.dto.mapper.PublisherMapper;
import edu.cs489app.library.dto.request.PublisherRequestDto;
import edu.cs489app.library.dto.response.PublisherResponseDto;
import edu.cs489app.library.model.Publisher;
import edu.cs489app.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService{

    private final PublisherRepository publisherRepository;

    /**
     * @param publisherDto
     * @return
     */
    @Override
    public Optional<PublisherResponseDto> createPublisher(PublisherRequestDto publisherDto) {
        // From publisherRequestDto, construct publisher
        if(publisherDto == null) return Optional.empty();
        Publisher publisher = PublisherMapper.toEntity(publisherDto);
        Publisher saved = publisherRepository.save(publisher);
        return Optional.of(PublisherMapper.toDto(saved));
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Optional<PublisherResponseDto> findPublisherByName(String name) {
        return publisherRepository.findByNameIgnoreCase(name)
                .map(PublisherMapper::toDto);
    }

    /**
     * @return
     */
    @Override
    public List<PublisherResponseDto> findAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(PublisherMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param name
     * @param publisherDto
     * @return
     */
    @Override
    public Optional<PublisherResponseDto> updatePublisher(String name, PublisherRequestDto publisherDto) {
        return publisherRepository.findByNameIgnoreCase(name)
                .map(existing -> {
                    existing.setName(publisherDto.name());
                    existing.setAddress(AddressMapper.toEntity(publisherDto.addressRequestDto()));
                    Publisher updated = publisherRepository.save(existing);
                    return PublisherMapper.toDto(updated);
                });
    }

    /**
     * @param name
     * @return
     */
    @Override
    public Optional<PublisherResponseDto> deletePublisher(String name) {
        return publisherRepository.findByNameIgnoreCase(name)
                .map(existing -> {
                    publisherRepository.deleteByName(name);
                    return PublisherMapper.toDto(existing);
                });
    }


}
