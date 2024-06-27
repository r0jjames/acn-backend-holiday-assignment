package com.assignment.holiday.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

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
}
