package com.dk.Launcher;

import com.dk.Dtos.ClickEventDTO;
import com.dk.Dtos.UrlMappingDTO;
import com.dk.Model.ClickEvent;
import com.dk.Model.UrlMapping;
import com.dk.Model.User;
import com.dk.Repository.ClickEventRepository;
import com.dk.Repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UrlMappingService {

    private static final String URL_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 8;

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private ClickEventRepository clickEventRepository;

    /**
     * Generates a new short URL and saves the mapping.
     */
    public UrlMappingDTO createShortUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        urlMapping.setClickCount(0);
        UrlMapping saved = urlMappingRepository.save(urlMapping);
        return convertToDto(saved);
    }

    /**
     * Generates a random alphanumeric short URL.
     */
    private String generateShortUrl() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder(SHORT_URL_LENGTH);
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            builder.append(URL_CHARACTERS.charAt(random.nextInt(URL_CHARACTERS.length())));
        }
        return builder.toString();
    }

    /**
     * Converts entity to DTO.
     */
    private UrlMappingDTO convertToDto(UrlMapping entity) {
        UrlMappingDTO dto = new UrlMappingDTO();
        dto.setId(entity.getId());
        dto.setOriginalUrl(entity.getOriginalUrl());
        dto.setShortUrl(entity.getShortUrl());
        dto.setClickCount(entity.getClickCount());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUsername(entity.getUser().getUsername());
        return dto;
    }

    /**
     * Returns all short URLs created by a given user.
     */
    public List<UrlMappingDTO> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Returns daily click statistics for a short URL within a date range.
     */
    public List<ClickEventDTO> getClickEventsByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        UrlMapping mapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (mapping != null) {
            List<ClickEvent> events = clickEventRepository.findByUrlMappingAndClickDateBetween(mapping, start, end);
            return events.stream()
                    .collect(Collectors.groupingBy(e -> e.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream()
                    .map(entry -> {
                        ClickEventDTO dto = new ClickEventDTO();
                        dto.setClickDate(entry.getKey());
                        dto.setCount(entry.getValue());
                        return dto;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Returns total click counts grouped by date for all URLs owned by a user within a date range.
     */
    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDateTime start, LocalDateTime end) {
        List<UrlMapping> mappings = urlMappingRepository.findByUser(user);
        List<ClickEvent> events = clickEventRepository.findByUrlMappingInAndClickDateBetween(mappings, start, end);
        return events.stream()
                .collect(Collectors.groupingBy(e -> e.getClickDate().toLocalDate(), Collectors.counting()));
    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if(urlMapping != null){
            urlMapping.setClickCount(urlMapping.getClickCount()+1);
            urlMappingRepository.save(urlMapping);

            // Record click Event.
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(urlMapping);
            clickEventRepository.save(clickEvent);
        }
        return urlMapping;
    }
}
