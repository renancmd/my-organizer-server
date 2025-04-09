package dev.renancmd.MyOrganizer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private boolean done;
    private String name;
    private String description;
    private LocalDate date;
}
