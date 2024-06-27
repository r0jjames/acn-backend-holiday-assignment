package com.assignment.holiday.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class HolidayDto {
   private LocalDate date;
   private String localName;
   private String name;
   private String countryCode;
   private boolean fixed;
   private boolean global;
   private String countries;
   private String launchYear;
   private List<String> types;
}
