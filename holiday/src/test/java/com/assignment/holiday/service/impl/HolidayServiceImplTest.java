package com.assignment.holiday.service.impl;

import com.assignment.holiday.dto.HolidayDto;
import com.assignment.holiday.service.client.NagerDateClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class HolidayServiceImplTest {

    @MockBean
    private NagerDateClient nagerDateClient;

    @Autowired
    private HolidayServiceImpl holidayService;

    private List<HolidayDto> nlHolidays;
    private List<HolidayDto> usHolidays;

    @BeforeEach
    public void setup() {
        nlHolidays = Arrays.asList(
                new HolidayDto(LocalDate.of(2024, 1, 1), "Nieuwjaarsdag"),
                new HolidayDto(LocalDate.of(2024, 3, 29), "Goede Vrijdag"),
                new HolidayDto(LocalDate.of(2024, 3, 31), "Eerste Paasdag"),
                new HolidayDto(LocalDate.of(2024, 4, 1), "Tweede Paasdag"),
                new HolidayDto(LocalDate.of(2024, 4, 27), "Koningsdag"),
                new HolidayDto(LocalDate.of(2024, 5, 5), "Bevrijdingsdag"),
                new HolidayDto(LocalDate.of(2024, 5, 9), "Hemelvaartsdag"),
                new HolidayDto(LocalDate.of(2024, 5, 19), "Eerste Pinksterdag"),
                new HolidayDto(LocalDate.of(2024, 5, 20), "Tweede Pinksterdag"),
                new HolidayDto(LocalDate.of(2024, 12, 25), "Eerste Kerstdag"),
                new HolidayDto(LocalDate.of(2024, 12, 26), "Tweede Kerstdag")
        );

        usHolidays = Arrays.asList(
                new HolidayDto(LocalDate.of(2024, 1, 1), "New Year's Day"),
                new HolidayDto(LocalDate.of(2024, 1, 15), "Martin Luther King, Jr. Day"),
                new HolidayDto(LocalDate.of(2024, 2, 19), "Presidents' Day"),
                new HolidayDto(LocalDate.of(2024, 5, 27), "Memorial Day"),
                new HolidayDto(LocalDate.of(2024, 7, 4), "Independence Day"),
                new HolidayDto(LocalDate.of(2024, 9, 2), "Labor Day"),
                new HolidayDto(LocalDate.of(2024, 10, 14), "Columbus Day"),
                new HolidayDto(LocalDate.of(2024, 11, 11), "Veterans Day"),
                new HolidayDto(LocalDate.of(2024, 11, 28), "Thanksgiving Day"),
                new HolidayDto(LocalDate.of(2024, 12, 25), "Christmas Day")
        );
    }

    @Test
    public void testGetLast3Holidays() {
        String countryCode = "NL";

        when(nagerDateClient.getHolidays(2024, countryCode)).thenReturn(nlHolidays);

        List<HolidayDto> result = holidayService.getLast3Holidays(countryCode);

        assertEquals(3, result.size());
        assertEquals("Tweede Kerstdag", result.get(0).getLocalName());
        assertEquals("Eerste Kerstdag", result.get(1).getLocalName());
        assertEquals("Tweede Pinksterdag", result.get(2).getLocalName());
    }

    @Test
    public void testCountNonWeekendHolidaysForCountries() {
        List<String> countryCodes = Arrays.asList("NL", "US");
        int year = 2024;

        when(nagerDateClient.getHolidays(year, "NL")).thenReturn(nlHolidays);
        when(nagerDateClient.getHolidays(year, "US")).thenReturn(usHolidays);

        Map<String, Integer> result = holidayService.countNonWeekendHolidaysForCountries(countryCodes, year);

        assertEquals(2, result.size());
        assertEquals(7, result.get("NL"));
        assertEquals(10, result.get("US"));
    }

    @Test
    public void testGetCommonHolidays() {
        String countryCode1 = "NL";
        String countryCode2 = "US";
        int year = 2024;

        when(nagerDateClient.getHolidays(year, countryCode1)).thenReturn(nlHolidays);
        when(nagerDateClient.getHolidays(year, countryCode2)).thenReturn(usHolidays);

        List<HolidayDto> result = holidayService.getCommonHolidays(countryCode1, countryCode2, year);

        assertEquals(2, result.size());
        assertEquals("Nieuwjaarsdag", result.get(0).getLocalName()); // New Year's Day
        assertEquals("Eerste Kerstdag", result.get(1).getLocalName());  // Christmas Day
    }
}
