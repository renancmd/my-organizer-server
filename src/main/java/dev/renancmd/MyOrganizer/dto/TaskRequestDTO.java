package dev.renancmd.MyOrganizer.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {
    private String name;
    private String description;
    private LocalDateTime date;
}
