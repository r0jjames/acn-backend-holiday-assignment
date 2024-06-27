package com.assignment.holiday.controller;

import com.assignment.holiday.dto.HolidayDto;
import com.assignment.holiday.service.HolidayService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/holidays", produces = {MediaType.APPLICATION_JSON_VALUE})
public class HolidayController {
    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping("/{countryCode}/last3")
    public List<HolidayDto> getLast3Holidays(@PathVariable String countryCode) {
        return holidayService.getLast3Holidays(countryCode);
    }

    @GetMapping("/{year}/count")
    public Map<String, Long> countNonWeekendHolidays(@PathVariable int year, @RequestParam List<String> countryCodes) {
        return countryCodes.stream()
                .collect(Collectors.toMap(
                        countryCode -> countryCode,
                        countryCode -> holidayService.countNonWeekendHolidays(countryCode, year)
                ));
    }

    @GetMapping("/{year}/common")
    public List<HolidayDto> getCommonHolidays(@PathVariable int year, @RequestParam String countryCode1, @RequestParam String countryCode2) {
        return holidayService.getCommonHolidays(countryCode1, countryCode2, year);
    }
}
