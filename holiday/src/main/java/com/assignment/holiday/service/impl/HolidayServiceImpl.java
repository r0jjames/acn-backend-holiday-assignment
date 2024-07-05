package com.assignment.holiday.service.impl;

import com.assignment.holiday.dto.HolidayDto;
import com.assignment.holiday.service.HolidayService;
import com.assignment.holiday.service.client.NagerDateClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

@Service
@AllArgsConstructor
public class HolidayServiceImpl implements HolidayService {
    private final NagerDateClient nagerDateClient;

    @Override
    public List<HolidayDto> getLast3Holidays(String countryCode) {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        List<HolidayDto> holidays = nagerDateClient.getHolidays(currentYear, countryCode);
        return holidays.stream()
                .filter(h -> h.getDate().isBefore(currentDate))
                .sorted(Comparator.comparing(HolidayDto::getDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> countNonWeekendHolidaysForCountries(List<String> countryCodes, int year) {
        // The results are sorted in descending order based on the count.
        return countryCodes.parallelStream()
                .collect(Collectors.toMap(
                        countryCode -> countryCode,
                        countryCode -> countNonWeekendHolidays(countryCode, year)
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public List<HolidayDto> getCommonHolidays(String countryCode1, String countryCode2, int year) {
        List<HolidayDto> holidays1 = nagerDateClient.getHolidays(year, countryCode1);
        List<HolidayDto> holidays2 = nagerDateClient.getHolidays(year, countryCode2);
        return holidays1.stream()
                .filter(h1 -> holidays2.stream().anyMatch(h2 -> h2.getDate().equals(h1.getDate())))
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private Integer countNonWeekendHolidays(String countryCode, int year) {
        List<HolidayDto> holidays = nagerDateClient.getHolidays(year, countryCode);
        return (int) holidays.stream()
                .filter(holiday -> !isWeekend(holiday.getDate()))
                .count();
    }
}
