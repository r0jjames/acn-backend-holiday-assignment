package com.assignment.holiday.controller;

import com.assignment.holiday.dto.HolidayDto;
import com.assignment.holiday.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HolidayControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private HolidayController holidayController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(holidayController).build();
    }

    @Test
    public void testGetLast3Holidays() throws Exception {
        // Mocking service method
        List<HolidayDto> mockHolidays = Arrays.asList(
                new HolidayDto(LocalDate.parse("2023-01-01"), "New Year's Day"),
                new HolidayDto(LocalDate.parse("2023-12-25"), "Christmas Day"),
                new HolidayDto(LocalDate.parse("2023-11-23"), "Thanksgiving Day")
        );
        when(holidayService.getLast3Holidays(anyString())).thenReturn(mockHolidays);

        // Performing the mockMvc request
        mockMvc.perform(get("/api/holidays/US/last3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].localName").value("New Year's Day"))
                .andExpect(jsonPath("$[1].localName").value("Christmas Day"))
                .andExpect(jsonPath("$[2].localName").value("Thanksgiving Day"));
    }

    @Test
    public void testCountNonWeekendHolidays() throws Exception {
        // Mocking service method
        Map<String, Integer> mockHolidayCounts = Map.of("US", 10, "NL", 9);
        when(holidayService.countNonWeekendHolidaysForCountries(anyList(), anyInt())).thenReturn(mockHolidayCounts);

        // Performing the mockMvc request
        mockMvc.perform(get("/api/holidays/2024/count")
                        .param("countryCodes", "US", "NL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.US").value(10))
                .andExpect(jsonPath("$.NL").value(9));
    }

    @Test
    public void testGetCommonHolidays() throws Exception {
        // Mocking service method
        List<HolidayDto> mockCommonHolidays = Arrays.asList(
                new HolidayDto(LocalDate.parse("2024-01-01"), "New Year's Day"),
                new HolidayDto(LocalDate.parse("2024-12-25"), "Christmas Day")
        );
        when(holidayService.getCommonHolidays(anyString(), anyString(), anyInt())).thenReturn(mockCommonHolidays);

        // Performing the mockMvc request
        mockMvc.perform(get("/api/holidays/2024/common")
                        .param("countryCode1", "US")
                        .param("countryCode2", "NL"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].localName").value("New Year's Day"))
                .andExpect(jsonPath("$[1].localName").value("Christmas Day"));
    }
}
