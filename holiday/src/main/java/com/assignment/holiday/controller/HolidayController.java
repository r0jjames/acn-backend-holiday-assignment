package com.assignment.holiday.controller;

import com.assignment.holiday.dto.HolidayDto;
import com.assignment.holiday.service.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/holidays", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class HolidayController {
    private final HolidayService holidayService;

    @Operation(summary = "Get the last 3 celebrated holidays for a given country")
    @GetMapping("/{countryCode}/last3")
    public ResponseEntity<List<HolidayDto>> getLast3Holidays(@PathVariable String countryCode) {
        List<HolidayDto> holidays = holidayService.getLast3Holidays(countryCode);
        return ResponseEntity.ok(holidays);
    }

    @Operation(summary = "Get the number of public holidays not falling on weekends for given country codes")
    @GetMapping("/{year}/count")
    public ResponseEntity<Map<String, Integer>> countNonWeekendHolidays(@PathVariable int year, @RequestParam List<String> countryCodes) {
        Map<String, Integer> holidayCounts = countryCodes.stream()
                .collect(Collectors.toMap(
                        countryCode -> countryCode,
                        countryCode -> holidayService.countNonWeekendHolidays(countryCode, year)
                ));
        return ResponseEntity.ok(holidayCounts);
    }
    @Operation(summary = "Get the common holidays for two countries in a given year")
    @GetMapping("/{year}/common")
    public List<HolidayDto> getCommonHolidays(@PathVariable int year, @RequestParam String countryCode1, @RequestParam String countryCode2) {
        return holidayService.getCommonHolidays(countryCode1, countryCode2, year);
    }
}
