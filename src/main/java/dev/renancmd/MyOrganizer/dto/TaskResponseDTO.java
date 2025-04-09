package dev.renancmd.MyOrganizer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
}
