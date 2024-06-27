package com.assignment.holiday.service;

import com.assignment.holiday.dto.HolidayDto;

import java.util.List;

public interface HolidayService {
    List<HolidayDto> getLast3Holidays(String countryCode);
    Integer countNonWeekendHolidays(String countryCode, int year);
    List<HolidayDto> getCommonHolidays(String countryCode1, String countryCode2, int year);
}
