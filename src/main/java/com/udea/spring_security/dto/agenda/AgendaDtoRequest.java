package com.udea.spring_security.dto.agenda;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgendaDtoRequest {

     @JsonProperty("service_number")
     String serviceNumber;

     @JsonProperty("service_name")
     @NotBlank
     String serviceName;

     @JsonProperty("service_date")
     @NotNull
     LocalDate serviceDate;

     @JsonProperty("service_hour")
     @NotNull
     LocalTime serviceHour;

     @JsonProperty("technician_name")
     @NotBlank
     String technicianName;

     @JsonProperty("adviser_name")
     @NotBlank
     String adviserName;

     Boolean status;

     @JsonProperty("real_state")
     String realState;

     String observations;

     @NotNull
     Double price;

    @JsonProperty("service_type_id")
     Long serviceTypeId;

    @JsonProperty("cuistomer_id")
    Long customerId;




    
}
