package com.dk.Repository;

import com.dk.Model.ClickEvent;
import com.dk.Model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {

    List<ClickEvent> findByUrlMappingAndClickDateBetween(
            UrlMapping mapping,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    List<ClickEvent> findByUrlMappingInAndClickDateBetween(
            List<UrlMapping> urlMappings,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
