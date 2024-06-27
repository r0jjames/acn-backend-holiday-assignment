package com.assignment.holiday.service;

import com.assignment.holiday.dto.HolidayDto;

import java.util.List;
import java.util.Map;

public interface HolidayService {
    List<HolidayDto> getLast3Holidays(String countryCode);
    Map<String, Integer> countNonWeekendHolidaysForCountries(List<String> countryCodes, int year);
    List<HolidayDto> getCommonHolidays(String countryCode1, String countryCode2, int year);
}
