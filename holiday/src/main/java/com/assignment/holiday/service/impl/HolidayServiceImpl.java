package com.assignment.holiday.service.impl;

import com.assignment.holiday.dto.HolidayDto;
import com.assignment.holiday.service.HolidayService;
import com.assignment.holiday.service.client.NagerDateClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HolidayServiceImpl implements HolidayService {
    private final NagerDateClient nagerDateClient;
    @Override
    public List<HolidayDto> getLast3Holidays(String countryCode) {
        int currentYear = java.time.Year.now().getValue();
        HolidayDto[] holidays = getHolidays(countryCode, currentYear);
        return Arrays.stream(holidays)
                .sorted((h1, h2) -> h2.getDate().compareTo(h1.getDate()))
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public long countNonWeekendHolidays(String countryCode, int year) {
        HolidayDto[] holidays = getHolidays(countryCode, year);
        return Arrays.stream(holidays)
                .filter(holiday -> !isWeekend(holiday.getDate()))
                .count();
    }

    @Override
    public List<HolidayDto> getCommonHolidays(String countryCode1, String countryCode2, int year) {
        HolidayDto[] holidays1 = getHolidays(countryCode1, year);
        HolidayDto[] holidays2 = getHolidays(countryCode2, year);
        return Arrays.stream(holidays1)
                .filter(h1 -> Arrays.stream(holidays2).anyMatch(h2 -> h2.getDate().equals(h1.getDate())))
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }
    private HolidayDto[] getHolidays(String countryCode, int year) {
        return nagerDateClient.getHolidays(year, countryCode);
    }
}
