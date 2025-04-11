package dev.renancmd.MyOrganizer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoDTO {
    private String email;
    private String name;
}