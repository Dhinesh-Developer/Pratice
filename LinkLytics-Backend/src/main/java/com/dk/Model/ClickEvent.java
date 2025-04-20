package com.dk.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime clickDate;

    @ManyToOne
    @JoinColumn(name = "url_mapping_id")
    private UrlMapping urlMapping;

    public ClickEvent() {

    }

    @Override
    public String toString() {
        return "ClickEvent{" +
                "id=" + id +
                ", clickDate=" + clickDate +
                ", urlMapping=" + urlMapping +
                '}';
    }

    public ClickEvent(LocalDateTime clickDate, UrlMapping urlMapping) {
        this.clickDate = clickDate;
        this.urlMapping = urlMapping;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getClickDate() {
        return clickDate;
    }

    public void setClickDate(LocalDateTime clickDate) {
        this.clickDate = clickDate;
    }

    public UrlMapping getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(UrlMapping urlMapping) {
        this.urlMapping = urlMapping;
    }

    public ClickEvent(Long id, LocalDateTime clickDate, UrlMapping urlMapping) {
        this.id = id;
        this.clickDate = clickDate;
        this.urlMapping = urlMapping;
    }
}
