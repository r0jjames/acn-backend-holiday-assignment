package com.assignment.holiday.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(
        name = "Holiday",
        description = "Schema to hold Holiday Information"
)
public class HolidayDto {
   @Schema(description = "Date of the holiday", example = "2024-01-01")
   private LocalDate date;

   @Schema(description = "Local name of the holiday", example = "Nieuwjaarsdag")
   private String localName;

   @Schema(description = "Official name of the holiday", example = "New Year's Day")
   private String name;

   @Schema(description = "Country code of the holiday", example = "NL")
   private String countryCode;

   @Schema(description = "Is the holiday date fixed", example = "false")
   private boolean fixed;

   @Schema(description = "Is the holiday global", example = "true")
   private boolean global;

   @Schema(description = "Countries where the holiday is celebrated", example = "NL")
   private String countries;

   @Schema(description = "Year of launch", example = "null")
   private String launchYear;

   @Schema(description = "Types of the holiday", example = "[\"Public\"]")
   private List<String> types;
}
