package com.assignment.holiday.service.client;

import com.assignment.holiday.dto.HolidayDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "nagerDateClient", url = "https://date.nager.at/api/v3")
public interface NagerDateClient {
    @GetMapping("/PublicHolidays/{year}/{countryCode}")
    List<HolidayDto> getHolidays(@PathVariable("year") int year, @PathVariable("countryCode") String countryCode);
}
