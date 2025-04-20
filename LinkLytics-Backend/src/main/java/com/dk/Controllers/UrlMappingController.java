package com.dk.Controllers;

import com.dk.Dtos.ClickEventDTO;
import com.dk.Dtos.UrlMappingDTO;
import com.dk.Launcher.UrlMappingService;
import com.dk.Launcher.UserService;
import com.dk.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
public class UrlMappingController {

    @Autowired
    private UrlMappingService urlMappingService;

    @Autowired
    private UserService userService;

    // {"originalUrl": "https://example.com"}
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String, String> request,
                                                        Principal principal) {
        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(originalUrl, user);
        return ResponseEntity.ok(urlMappingDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/myurls")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingDTO> urls = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/analytics/{shortUrl}")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam("startDate") String startDate,
                                                               @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);
        List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOS);
    }

//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("/totalClicks")
//    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal,
//                                                                     @RequestParam("startDate") String startDate,
//                                                                     @RequestParam("endDate") String endDate) {
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
//        LocalDate start = LocalDate.parse(startDate, formatter);
//        LocalDate end = LocalDate.parse(endDate, formatter);
//        User user = userService.findByUsername(principal.getName());
//        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUserAndDate(user, start.atStartOfDay(), end.atStartOfDay());
//        return ResponseEntity.ok(totalClicks);
//    }

    /*
    *
    @GetMapping("/total-clicks")
    public String getTotalClicksByDate(@RequestParam String date) {
        try {
            // Clean the date string by trimming any whitespace or newlines
            String cleanedDate = date.trim();

            // Parse the cleaned date
            LocalDate parsedDate = LocalDate.parse(cleanedDate, DateTimeFormatter.ISO_LOCAL_DATE);

            // Here you can use parsedDate to fetch clicks, for now just returning as response
            return "Parsed date: " + parsedDate;

        } catch (DateTimeParseException e) {
            return "Invalid date format. Please use 'yyyy-MM-dd'. Error: " + e.getMessage();
        }
    }*/
}
